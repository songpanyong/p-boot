package com.guohuai.basic.component.ext.web;

import java.util.Enumeration;

import javax.persistence.MappedSuperclass;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.config.TerminalConfig;

@MappedSuperclass
public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	public static final String UID = "UID";// 用户ID
	public static final String UNAME = "UNAME";// 用户姓名
	public static final String USERTYPE = "USERTYPE";// 用户类型

	@Autowired
	protected HttpSession session;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	@Autowired
	private TerminalConfig terminalConfig;

	@Autowired
	PersistentCookieHttpSessionStrategy persistentCookieHttpSessionStrategy;

	@Value("${error.print.strategy:logger}")
	private String onError;

	@ExceptionHandler
	public @ResponseBody ResponseEntity<Response> onError(HttpServletRequest request, Exception exception) {

		this.errorLogHandle(exception);

		Response response = new Response().error(exception);
		Enumeration<String> enums = this.request.getParameterNames();
		while (enums.hasMoreElements()) {
			String name = enums.nextElement();
			response.with(name, this.request.getParameter(name));
		}

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	private void errorLogHandle(Exception e) {
		if (this.onError.equals("logger")) {
			if (e instanceof GHException) {
				logger.info(e.getMessage());
			} else {
				logger.info(e.getMessage(), e);
			}
		} else {
			e.printStackTrace();
		}
	}

	/**
	 * 用户是否已经登录
	 * 
	 * @return 登录id
	 */
	protected String isLogin() {
		if (session.getAttribute(UID) != null) {
			String userOid = session.getAttribute(UID).toString();
			logger.debug("isLogin useroid {}, and sessionid is {}.", userOid, session.getId());
			if (terminalConfig.isUserLocked(userOid)) {
				throw new GHException(15002, "当前用户已锁定");
			}
			return userOid;
		} else {
			return null;
		}
	}

	protected void setLoginUser(String userOid, String... terminal) {
		if (terminal != null && terminal.length > 0) {
			persistentCookieHttpSessionStrategy.onNewSessionTwo(session.getId(), request, response, terminal[0]);
		}
		session.setAttribute(UID, userOid);
		terminalConfig.handleMultiLogin(session.getId(), userOid, terminal);
		logger.info("useroid {} login, and sessionid is {}.", userOid, session.getId());
	}

	protected void setLoginUser(String userOid, String userName, String... terminal) {
		if (terminal != null && terminal.length > 0) {
			persistentCookieHttpSessionStrategy.onNewSessionTwo(session.getId(), request, response, terminal[0]);
		}
		session.setAttribute(UID, userOid);
		session.setAttribute(UNAME, userName);
		terminalConfig.handleMultiLogin(session.getId(), userOid, terminal);
		logger.info("useroid {},userName {} login, and sessionid is {}.", userOid, userName, session.getId());
	}

	protected void setLoginUserAndType(String userOid, String userType) {
		session.setAttribute(UID, userOid);
		session.setAttribute(USERTYPE, userType);
		terminalConfig.handleMultiLogin(session.getId(), userOid);
		logger.info("useroid {}, userType {}, and sessionid is {}.", userOid, userType, session.getId());
	}
	
	protected void setLoginUserAndType(String userOid, String userType, String[] terminal) {
		if (terminal != null && terminal.length > 0) {
			persistentCookieHttpSessionStrategy.onNewSessionTwo(session.getId(), request, response, terminal[0]);
		}
		session.setAttribute(UID, userOid);
		session.setAttribute(USERTYPE, userType);
		terminalConfig.handleMultiLogin(session.getId(), userOid, terminal);
		logger.info("useroid {}, userType {}, and sessionid is {}.", userOid, userType, session.getId());
	}
	
	protected String getLoginUser() {
		if (session.getAttribute(UID) == null) {
			throw new GHException(10002, "当前用户未登录或会话已超时");
		}
		String userOid = session.getAttribute(UID).toString();
		if (terminalConfig.isUserLocked(userOid)) {
			throw new GHException(15002, "当前用户已锁定");
		}
		logger.debug("get login useroid {}, and sessionid is {}.", userOid, session.getId());
		return userOid;
	}

	protected String getLoginUserName() {
		if (session.getAttribute(UNAME) == null) {
			throw new GHException(10002, "当前用户未登录或会话已超时");
		}
		String userName = session.getAttribute(UNAME).toString();
		logger.debug("get login userName {},and sessionid is {}.", userName, session.getId());
		return userName;
	}

	protected String getLoginUserType() {
		if (session.getAttribute(UID) == null) {
			throw new GHException(10002, "当前用户未登录或会话已超时");
		}
		if (session.getAttribute(USERTYPE) == null) {
			return null;
		}
		String userType = session.getAttribute(USERTYPE).toString();
		logger.debug("get login userType {},and sessionid is {}.", userType, session.getId());
		return userType;
	}

	protected void setLogoutUser() {
		String userOid = "";
		if (session.getAttribute(UID) != null) {
			userOid = (String) session.getAttribute(UID);
			session.removeAttribute(UID);
			session.removeAttribute(UNAME);
			this.terminalConfig.removeLoginUser(userOid);
		}
		logger.info("useroid {} logout, and sessionid is {}.", userOid, session.getId());
		session.invalidate();
	}
}
