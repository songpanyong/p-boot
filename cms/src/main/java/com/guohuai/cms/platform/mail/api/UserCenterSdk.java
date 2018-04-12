package com.guohuai.cms.platform.mail.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.Logger;
import feign.Logger.Level;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;

@lombok.Data
@Configuration
public class UserCenterSdk {

	@Value("${mimosa.host:http://localhost}")
	private String host;
	
	@Value("${logger.feign.level:BASIC}")
	private String loggerLevel;
	
	@Bean
	public UserCenterApi opeSdk() {
		Level level = getLogLevel();
		
		return Feign.builder().encoder(new GsonEncoder()).decoder(new GsonDecoder())
				.logger(new Slf4jLogger()).logLevel(level)
				.target(UserCenterApi.class, this.host);
	}
	
	private Level getLogLevel(){
		Level level =  Logger.Level.BASIC;
		
		switch (loggerLevel.toUpperCase()) {
			case "NONE":
				level = Logger.Level.NONE;
				break;
			case "HEADERS":
				level = Logger.Level.HEADERS;
				break;
			case "FULL":
				level = Logger.Level.FULL;
				break;
			default:
				break;
		}
		
		return level;
	}
}
