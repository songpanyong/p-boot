package com.guohuai.basic.component.sms.smssdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guohuai.basic.component.sms.UTF8Encoder;

import feign.Feign;
import feign.Logger.Level;
import feign.slf4j.Slf4jLogger;

@lombok.Data
@Configuration
public class SMSSdk {

	@Value("${sms.yimei.host:localhost}")
	private String host;
	
	@Bean
	public SMSApi smsSdk() {
		return Feign.builder().encoder(new UTF8Encoder()).logger(new Slf4jLogger()).logLevel(Level.FULL).target(SMSApi.class, "http://" + this.host + "/");
	}
}
