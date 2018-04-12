package com.guohuai.basic.component.sms.smssdk;

import feign.Param;
import feign.RequestLine;
/***
 * 同舟互联短信通道
 * @author gh
 *
 */
public interface SMSTZHLApi {
//	@RequestLine("GET c123/sendsms?uid={uid}&pwd={pwd}&mobile={mobile}&content={content}")
	@RequestLine("POST c123/sendsms")
	public String sendTZHLSMS(@Param("uid") String cdkey, 
			@Param("pwd") String password,
			@Param("mobile") String phone,
			@Param("content") String message);
}
