package com.guohuai.tulip.platform.coupon.userCoupon;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;

public class UserCouponRedisUtil {
	
	public static final Charset UTF8CHARSET=Charset.forName("utf8");
	
	public static final String USER_COUPON_REDIS_KEY = "c:g:u:ai:cp:";
	
	public static final String USER_NEW_COUPON_REDIS_KEY = "c:g:u:ai:cp:newcp:";
	
	public static final String USER_NEW_COUPON_LOGIN_REDIS_KEY = "c:g:u:ai:cp:logincp:";
	
	public static final String COUPON_REDPACKET_REDIS_KEY = "c:g:u:ai:rand:list:";
	
	public static final String COUPON_REDPACKET_INDEX_REDIS_KEY = "c:g:u:ai:rand:index:";
	
	private static final Logger logger = LoggerFactory.getLogger(UserCouponRedisUtil.class);
	
	public static String setStr(RedisTemplate<String, String> redis, final String key, final String value){
		return redis.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.info("user.coupon.redis={},val={}", key, value);
				connection.set((USER_COUPON_REDIS_KEY + key).getBytes(UTF8CHARSET), value.getBytes(UTF8CHARSET));
				return value;
			}
		});
	}
	
	public static UserCouponRedisInfo lPush(RedisTemplate<String, String> redis, final String key, final UserCouponRedisInfo value){
		final String json = JSONObject.toJSONString(value);
		return redis.execute(new RedisCallback<UserCouponRedisInfo>() {

			@Override
			public UserCouponRedisInfo doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.info("user.coupon.redis.lPush={},val={}", key, json);
				connection.lPush(key.getBytes(UTF8CHARSET), json.getBytes(UTF8CHARSET));
				return value;
			}
		});
	}
	
	public static UserCouponRedisInfo set(RedisTemplate<String, String> redis, final String key, final UserCouponRedisInfo value){
		final String json = JSONObject.toJSONString(value);
		return redis.execute(new RedisCallback<UserCouponRedisInfo>() {

			@Override
			public UserCouponRedisInfo doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.info("user.coupon.redis={},val={}", key + "_" + value.getCouponOid(), json);
				connection.set((USER_COUPON_REDIS_KEY + key + "_" + value.getCouponOid()).getBytes(UTF8CHARSET), json.getBytes(UTF8CHARSET));
				return value;
			}
		});
	}
	
	public static List<UserCouponRedisInfo> lRange(RedisTemplate<String, String> redis, String key){
		return redis.execute(new RedisCallback<List<UserCouponRedisInfo>>() {
			@Override
			public List<UserCouponRedisInfo> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte [] nkey = key.getBytes(UTF8CHARSET);
//				Long size = connection.lLen(nkey);
				List<byte[]> list = connection.lRange(nkey, 0, -1);
				List<UserCouponRedisInfo> rlist = new ArrayList<UserCouponRedisInfo>();
				for (byte[] data : list) {
					if(null != data){
						rlist.add(JSONObject.parseObject(new String(data, UTF8CHARSET), UserCouponRedisInfo.class));
					}
				}
				return rlist;
			}
		});
	}
	
	public static String getStr(RedisTemplate<String, String> redis, final String key){
		return redis.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte [] data = connection.get((USER_COUPON_REDIS_KEY + key).getBytes(UTF8CHARSET));
				if (data == null) {
					return null;
				}
				return new String(data, UTF8CHARSET);
//				return JSONObject.parseObject(new String(data, UTF8CHARSET), UserCouponRedisInfo.class);
			}
		});
	}
	
	public static UserCouponRedisInfo get(RedisTemplate<String, String> redis, final String key){
		return redis.execute(new RedisCallback<UserCouponRedisInfo>() {
			@Override
			public UserCouponRedisInfo doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte [] data = connection.get((USER_COUPON_REDIS_KEY + key).getBytes(UTF8CHARSET));
				if (data == null) {
					return null;
				}
				return JSONObject.parseObject(new String(data, UTF8CHARSET), UserCouponRedisInfo.class);
			}
		});
	}
	
	public static List<UserCouponRedisInfo> getByList(RedisTemplate<String, String> redis, String key, List<String> couponOids){
		return redis.execute(new RedisCallback<List<UserCouponRedisInfo>>() {
			@Override
			public List<UserCouponRedisInfo> doInRedis(RedisConnection connection)
					throws DataAccessException {
				List<UserCouponRedisInfo> list = new ArrayList<UserCouponRedisInfo>();
				for (String coid : couponOids) {
					byte [] data = connection.get((USER_COUPON_REDIS_KEY + key + "_" + coid).getBytes(UTF8CHARSET));
					if(null != data){
						list.add(JSONObject.parseObject(new String(data, UTF8CHARSET), UserCouponRedisInfo.class));
					}
				}
				
				return list;
			}
		});
	}
	
	public static Long delcoup(RedisTemplate<String, String> redis, final String key){
		return redis.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				logger.info("user.coupon.redis.del , key={}", key);
				return connection.del(key.getBytes(UTF8CHARSET));
			}
		});
	}
	
	public static Long del(RedisTemplate<String, String> redis, final String key){
		return redis.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.del((USER_COUPON_REDIS_KEY + key).getBytes(UTF8CHARSET));
			}
		});
	}
}
