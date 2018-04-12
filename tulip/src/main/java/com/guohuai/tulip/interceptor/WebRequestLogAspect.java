package com.guohuai.tulip.interceptor;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.fastjson.JSON;
import com.guohuai.tulip.annotation.SystemControllerLog;
import com.guohuai.tulip.platform.gatewayrequestlog.GatewayRequestLogEntity;
import com.guohuai.tulip.platform.gatewayrequestlog.GatewayRequestLogService;

@Aspect
@Component
public class WebRequestLogAspect {

	private static Logger logger = LoggerFactory.getLogger(WebRequestLogAspect.class);

	private ThreadLocal<GatewayRequestLogEntity> tlocal = new ThreadLocal<GatewayRequestLogEntity>();

	@Autowired
	private GatewayRequestLogService gatewayRequestLogService;

	public static String getControllerDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	@Pointcut("@annotation(com.guohuai.tulip.annotation.SystemControllerLog)")
	public void webRequestLog() {
	}

//	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
//	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
//		// 获取用户请求方法的参数并序列化为JSON格式字符串
//		// ==========记录本地异常日志==========
//		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		HttpServletRequest request = attributes.getRequest();
//		if (request != null) {
//			String method = request.getMethod();
//			String params = "";
//			if ("POST".equals(method)) {
//				Object[] paramsArray = joinPoint.getArgs();
//				params = argsArrayToString(paramsArray);
//			} else {
//				Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
//				params = paramsMap.toString();
//			}
//			System.out.println("出现异常-----------------");
//			logger.error("传入参数:{}异常方法:{}异常代码:{}异常信息:{}参数:{}", params,
//					joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(),
//					e.getClass().getName(), e.getMessage());
//		}
//	}
	@AfterThrowing(pointcut = "webRequestLog()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		
		try {
			// 处理完请求，返回内容
			GatewayRequestLogEntity optLog = tlocal.get();
			optLog.setResponse(e.getMessage());
			long beginTime = optLog.getRequestTime().getTime();
			long responseTime = (System.currentTimeMillis() - beginTime) / 1000;
			optLog.setResponseTime(responseTime);
			optLog.setStatus("fail");
			 //==========记录本地异常日志========== 
			logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget()
					.getClass().getName()
					+ joinPoint.getSignature().getName(), e.getClass().getName(),
					e.getMessage());
			gatewayRequestLogService.saveGatewayRequestLog(optLog);
		} catch (Exception ex) {
			logger.error("***操作请求日志记录失败doAfterReturning()***", ex);
		}
	}

	// @Order(5)
	@Before("webRequestLog()")
	public void doBefore(JoinPoint joinPoint) {
		try {

			long beginTime = System.currentTimeMillis();

			// 接收到请求，记录请求内容
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			String beanName = joinPoint.getSignature().getDeclaringTypeName();
			String methodName = joinPoint.getSignature().getName();
			String uri = request.getRequestURI();
			String remoteAddr = getIpAddr(request);
			String sessionId = request.getSession().getId();
			String user = (String) request.getSession().getAttribute("user");
			String method = request.getMethod();
			String params = "";
			String description = getControllerDescription(joinPoint);
			if ("POST".equals(method)) {
				Object[] paramsArray = joinPoint.getArgs();
				params = argsArrayToString(paramsArray);
			} else {
				Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
				params = paramsMap.toString();
			}

			logger.info("uri=" + uri + "; beanName=" + beanName + "; remoteAddr=" + remoteAddr + "; user=" + user
					+ "; methodName=" + methodName + "; params=" + params);

			GatewayRequestLogEntity optLog = new GatewayRequestLogEntity();
			optLog.setMethod(methodName);
			optLog.setRequest(params != null ? params.toString() : "");
			optLog.setIp(remoteAddr);
			optLog.setAppId("mimosa");
			optLog.setRequestTime(new Timestamp(beginTime));
			optLog.setDescription(description);
			tlocal.set(optLog);

		} catch (Exception e) {
			logger.error("***操作请求日志记录失败doBefore()***", e);
		}
	}

	// @Order(5)
	@AfterReturning(returning = "result", pointcut = "webRequestLog()")
	@Transactional
	public void doAfterReturning(Object result) {
		try {
			// 处理完请求，返回内容
			GatewayRequestLogEntity optLog = tlocal.get();
			optLog.setResponse(result.toString());
			long beginTime = optLog.getRequestTime().getTime();
			long responseTime = (System.currentTimeMillis() - beginTime) / 1000;
			optLog.setResponseTime(responseTime);
			optLog.setStatus("success");

			System.out.println(
					"请求耗时：" + optLog.getResponseTime() + "   **  " + optLog.getRequest() + " ** " + optLog.getMethod());
			System.out.println("RESPONSE : " + result);
			gatewayRequestLogService.saveGatewayRequestLog(optLog);
		} catch (Exception e) {
			logger.error("***操作请求日志记录失败doAfterReturning()***", e);
		}
	}

	/**
	 * 获取登录用户远程主机ip地址
	 * 
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 请求参数拼装
	 * 
	 * @param paramsArray
	 * @return
	 */
	private String argsArrayToString(Object[] paramsArray) {
		String params = "";
		if (paramsArray != null && paramsArray.length > 0) {
			for (int i = 0; i < paramsArray.length; i++) {
				Object jsonObj = JSON.toJSON(paramsArray[i]);
				if (jsonObj == null) {
					params +=  " ";
					continue;
				}
				params += jsonObj.toString() + " ";
			}
		}
		return params.trim();
	}
}