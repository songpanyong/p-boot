package com.guohuai.payadapter.redeem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class HistoryTranTwoRedisUtil {
	private  final Logger logger = LoggerFactory.getLogger(HistoryTranTwoRedisUtil.class);
	private final static Long GQTIME=600L;//两分钟
	@Autowired
	public  RedisTemplate<String, String> redis;

	/*** 判断是否已经跑过定时 **/
	public boolean checkRedis(final String key,final String value) {
		Object obj =readRedis(key,value);
		if (null == obj || !(obj instanceof String) || obj.toString().trim().equals("")) {
			return false;
		}
		return true;

	}

	/** 对定时写redis 标记 **/
	public Long setRedis(final String key,final String value) {
		logger.info("写定时标记了-------------》"+key+":"+value);
		return this.redis.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				boolean lock=connection.setNX(key.getBytes(), value.getBytes());
				logger.info("写定时标记了成功与否-------------》"+lock);
				if(lock)return 1L;
				return 0L;
			}
		});
	}
	
	/**
	 *  对定时写redis 标记  并加过期时间
	 * @param key
	 * @param value
	 * @param expixeTime
	 * @return
	 */
	public Long setRedisByTime(final String key,final String value) {
		logger.info("写定时标记了带时间操作-------------》"+key+":"+value);
		return this.redis.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				boolean lock=connection.setNX(key.getBytes(), value.getBytes());
				connection.expire(key.getBytes(), GQTIME);
				logger.info("写定时标记了成功与否-------------》"+lock);
				if(lock)return 1L;
				return 0L;
				
			}
		});
	}

	/** 读定时标记 **/
	public Object readRedis(final String key,final String value) {
		logger.info("读定时标记了-------------》"+key+":"+value);
		Object resule = this.redis.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] bytes = connection.getSet(key.getBytes(), value.getBytes());
				if (null != bytes && bytes.length > 0) {
					Object obj = bytes.toString();
					return obj;
				} else {
					return null;
				}
			}
		});
		return resule;
	}


	/**删除定时标记**/
	public void delRedis(final String...keys) {
		logger.info("删定时标记了-------------》"+JSONObject.toJSONString(keys));
		this.redis.execute(new RedisCallback<Void>() {
			@Override
			public Void doInRedis(RedisConnection connection) throws DataAccessException {
				for(int i=0;i<keys.length;i++){
					connection.del(keys[i].getBytes());
				}
				return null;
			}
		});
	}
}
