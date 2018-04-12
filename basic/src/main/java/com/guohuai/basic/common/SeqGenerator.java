package com.guohuai.basic.common;


import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SeqGenerator {
	@Autowired
	RedisTemplate<String, String> redis;
	final static String SEQ_PRIFIX="seq:";
    public static String SEQ_ENV;

    @Value("${seq.env:}")
    public void setSeqEnv(String seqEnv) {
    	SEQ_ENV = seqEnv;
    }
	
	/**
	 * {k}{yyyymmdd}######## 格式序列号(8字符序列), 最终返回不包括大括号.
	 * 
	 * @param k 序列类别码
	 * @return 字符串序列
	 */
	public String next(String k){
		return getSeqNo(redis,k);
	}
	
	/**
	 * 生成序列号
	 * @param redis
	 * @param prefix 前缀
	 * @return
	 */
	public static String getSeqNo(RedisTemplate<String, String> redis, final String prefix) {
		Calendar cal=Calendar.getInstance();
		String key=String.format("%s%s%02d%02d",prefix,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH));
		long v =redis.opsForValue().increment(SEQ_PRIFIX+SEQ_ENV+key, 1);
		return String.format("%s%08d",SEQ_ENV+key,v);
	}
	
	
	public static String getSimpleSeqNo(RedisTemplate<String, String> redis, final String prefix) {
		long v =redis.opsForValue().increment(prefix, 1);
		return String.format("%s%08d",prefix, v);
	}
}
