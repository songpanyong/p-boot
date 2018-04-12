package com.guohuai.basic.component.sms.smssdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guohuai.basic.component.sms.GBKEncoder;

import feign.Feign;
import feign.Logger.Level;
import feign.slf4j.Slf4jLogger;

/***
 * 同舟互联短信通道
 * @author gh
 *
 */
@lombok.Data
@Configuration
public class SMSTZHLSdk {
	
	@Value("${sms.tzhl.host:localhost}")
	private String host;
	
	@Bean
	public SMSTZHLApi smsTZHLSdk() {
		return Feign.builder().encoder(new GBKEncoder()).logger(new Slf4jLogger()).logLevel(Level.FULL).target(SMSTZHLApi.class, "http://" + this.host + "/");
	}
}
