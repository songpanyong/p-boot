package com.guohuai.cms.component.userUtil;

import java.nio.charset.Charset;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;

public class UserinfoRedisUtil {
	private static final Charset UTF8CHARSET=Charset.forName("utf8");
	public static final String USER_INFO_REDIS_KEY = "c:g:u:ai:";
	
	public static RedisUserinfo get(RedisTemplate<String, String> redis,final String key){
		return redis.execute(new RedisCallback<RedisUserinfo>() {
			@Override
			public RedisUserinfo doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte [] data =connection.get((USER_INFO_REDIS_KEY+key).getBytes(UTF8CHARSET));
				if (data == null) {
					return null;
				}
				return JSONObject.parseObject(new String(data, UTF8CHARSET),RedisUserinfo.class);
			}
		});
	}
}
