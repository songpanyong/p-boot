package com.guohuai.basic.config;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Component;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.exception.GHException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class TerminalConfig {
	public static final String MULTI_LOGIN_REDIS_PREFIX = "m:u:ml:";// 用户多终端登录
	public static final String MULTI_USERLOCK_REDIS = "m:u:ul";// 用户锁定状态
	public static final String CURRENT_LOGIN_USERS = "m:c:u";// 当前登录用户，用作统计

	// multi:允许多终端登录，single:单终端登录
	@Value("${mimosa.session.login:}")
	private String multiLogin;

	// 哪些终端的登录会话的cookie保存在当前会话中
	public static String loginScope;

	@Autowired
	private RedisOperationsSessionRepository redisOperation;

	@Autowired
	RedisTemplate<String, String> redis;

	/**
	 * 登录终端列表 app:app终端 pc:pc终端 wx:微信终端
	 */
	public static Set<String> terminals = new HashSet<String>();

	@Value("${mmp.ternimals:app,pc,wx}")
	public void setTerminals(String v) {
		for (String t : v.split(",")) {
			terminals.add(t);
		}
	}

	@Value("${mimosa.login.sessionscope:pc}")
	public void setLoginScope(String v) {
		loginScope = v;
	}

	/**
	 * 处理多终端登录
	 * 
	 * @param currentSessionId
	 * @param userOid
	 * @param terminal
	 */
	public void handleMultiLogin(final String currentSessionId, final String userOid, final String... terminals) {
		this.addLoginUser(userOid);
		if (StringUtil.isEmpty(multiLogin)) {
			return;
		}
		String tmp = "";
		if (terminals != null && terminals.length > 0) {
			tmp = terminals[0];
		}
		if (!TerminalConfig.terminals.contains(tmp)) {
			throw new GHException(15001, "非法的登录终端");
		}
		if (this.isUserLocked(userOid)) {
			throw new GHException(15002, "当前用户已锁定");
		}
		
		final String term = tmp;
		if (multiLogin.equals("single")) {
			redis.execute(new RedisCallback<Boolean>() {

				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
					String hkey = MULTI_LOGIN_REDIS_PREFIX + userOid;
					Map<String, String> map = stringRedisConn.hGetAll(hkey);
					for (String terminal : map.keySet()) {
						String sessionId = map.get(terminal);
						if (!currentSessionId.equals(sessionId)) {
							redisOperation.delete(sessionId);
						}
					}
					stringRedisConn.del(hkey);
					stringRedisConn.hSet(hkey, term, currentSessionId);
					return true;
				}
			});
		} else if (this.multiLogin.equals("multi")) {
			redis.execute(new RedisCallback<Boolean>() {

				@Override
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
					String hkey = MULTI_LOGIN_REDIS_PREFIX + userOid;
					String currentTerminalOldSessionId = stringRedisConn.hGet(hkey, term);
					if (!currentSessionId.equals(currentTerminalOldSessionId)) {
						redisOperation.delete(currentTerminalOldSessionId);
					}
					stringRedisConn.hSet(hkey, term, currentSessionId);
					return true;
				}
			});
		}
	}

	/**
	 * 登出其他终端
	 * 
	 * @param currentSessionId
	 * @param userOid
	 * @param terminal
	 */
	public void logoutOthers(final String currentSessionId, final String userOid, final String... terminals) {
		if (multiLogin.equals("off")) {
			return;
		}
		String tmp = "";
		if (terminals != null && terminals.length > 0) {
			tmp = terminals[0];
		}
		if (!TerminalConfig.terminals.contains(tmp)) {
			throw new GHException(15001, "非法的登录终端");
		}
		final String term = tmp;
		redis.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				String hkey = MULTI_LOGIN_REDIS_PREFIX + userOid;
				Map<String, String> map = stringRedisConn.hGetAll(hkey);
				for (String terminal : map.keySet()) {
					String sessionId = map.get(terminal);
					if (!currentSessionId.equals(sessionId)) {
						redisOperation.delete(sessionId);
					}
				}
				stringRedisConn.del(hkey);
				stringRedisConn.hSet(hkey, term, currentSessionId);
				return true;
			}
		});
	}

	/**
	 * 锁定用户
	 * 
	 * @param userOid
	 */
	public void lockUser(final String userOid) {
		redis.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				stringRedisConn.sAdd(MULTI_USERLOCK_REDIS, userOid);
				return true;
			}
		});
	}

	/**
	 * 解锁用户
	 * 
	 * @param userOid
	 */
	public void unlockUser(final String userOid) {
		redis.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				stringRedisConn.sRem(MULTI_USERLOCK_REDIS, userOid);
				return true;
			}
		});
	}

	/**
	 * 用户是否已经锁定
	 * 
	 * @param userOid
	 *            return true|false
	 */
	public Boolean isUserLocked(final String userOid) {
		return redis.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				return stringRedisConn.sIsMember(MULTI_USERLOCK_REDIS, userOid);
			}
		});
	}
	
	/**
	 * 添加登录用户
	 * @param userOid
	 * @return
	 */
	public Boolean addLoginUser(final String userOid) {
		if (StringUtils.isEmpty(userOid)) {
			return false;
		}
		return redis.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				log.info("userOid={} addLoginUser",userOid);
				return stringRedisConn.hSet(CURRENT_LOGIN_USERS, userOid, System.currentTimeMillis()+"");
			}
		});
	}
	
	/**
	 * 移除登录用户
	 * @param userOid
	 * @return
	 */
	public Boolean removeLoginUser(final String userOid) {
		if (StringUtils.isEmpty(userOid)) {
			return false;
		}
		return redis.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
				log.info("userOid={} removeLoginUser",userOid);
				return stringRedisConn.hDel(CURRENT_LOGIN_USERS, userOid)>0;
			}
		});
	}
}
