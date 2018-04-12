package com.guohuai.cms.component.util;

import java.nio.charset.Charset;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {

	private static final Charset utf8 = Charset.forName("utf8");

	public static String hget(RedisTemplate<String, String> redis, final String hkey, final String key) {
		return redis.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] bytes = connection.hGet(hkey.getBytes(utf8), key.getBytes(utf8));
				if (null != bytes) {
					return new String(bytes, utf8);
				}
				return null;
			}
		});
	}

	public static boolean hset(RedisTemplate<String, String> redis, final String hkey, final String key, final String value) {
		return redis.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				boolean b = connection.hSet(hkey.getBytes(utf8), key.getBytes(utf8), value.getBytes(utf8));
				return b;
			}
		});
	}

	public static long hdel(RedisTemplate<String, String> redis, final String hkey, final String key) {
		return redis.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long l = connection.hDel(hkey.getBytes(utf8), key.getBytes(utf8));
				return l;
			}
		});
	}

}
