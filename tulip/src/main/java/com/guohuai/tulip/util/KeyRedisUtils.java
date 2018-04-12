package com.guohuai.tulip.util;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class KeyRedisUtils {
	private static final Charset UTF8CHARSET = Charset.forName("utf8");
	
	public static final Map<String, List<String>> initLoadEventList = new HashMap<String, List<String>>();
	
	public static final ConcurrentHashMap<String, List<String>> loadEventRuleList = new ConcurrentHashMap<>();
	
	public static final String INIT_EVENT_RULES = "c:g:u:ai:ev:list:";
	
	public static final String EVENT_RULE_CUMULATIVE = "c:g:u:ai:ev:rule:cumula:";

	public static Boolean expire(RedisTemplate<String, String> redis, final String key, final int expire) {
		return redis.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.expire(key.getBytes(UTF8CHARSET), expire);
			}

		});
	}
	
	
	public static Boolean hset(RedisTemplate<String, String> redis, final String key, final String field, final String value) {
		return redis.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.hSet((key).getBytes(UTF8CHARSET), field.getBytes(UTF8CHARSET), value.getBytes(UTF8CHARSET));
			}

		});
	}
	
	public static String hget(RedisTemplate<String, String> redis, final String key, final String field) {
		return redis.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				 byte [] data = connection.hGet((key).getBytes(UTF8CHARSET), field.getBytes(UTF8CHARSET));
				 if(null != data){
					 return new String(data, UTF8CHARSET);					 
				 } else {
					 return null;
				 }
			}

		});
	}
}
