package com.guohuai.basic.component.sms.smssdk;

import feign.Param;
import feign.RequestLine;

public interface SMSApi {

	@RequestLine("POST /sdkproxy/sendsms.action")
	public String sendSMS(@Param("cdkey") String cdkey, 
			@Param("password") String password,
			@Param("phone") String phone,
			@Param("message") String message,
			@Param("addserial") String addserial);
}
