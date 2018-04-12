package com.guohuai.basic.component.collectinfo;

import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSON;
import com.guohuai.basic.component.ext.web.BaseResp;

import feign.Feign;

@Configuration
public class CollectInfoSdk {
	private static String host="http://demo.guohuaitech.com/";
	private static CollectInfoApi collectInfoApi=Feign.builder().target(CollectInfoApi.class, host);
	
	public static BaseResp collectInfo(String info){
		BaseResp rep = new BaseResp();
		try {
			rep = JSON.parseObject(collectInfoApi.collectInfo(info), BaseResp.class);
		} catch (Exception e) {
			rep.setErrorCode(-1);
		}
		return rep;
	}
}
