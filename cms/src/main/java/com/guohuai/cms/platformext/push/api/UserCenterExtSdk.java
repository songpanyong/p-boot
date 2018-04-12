package com.guohuai.cms.platformext.push.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

@lombok.Data
@Configuration
public class UserCenterExtSdk {
	@Value("${mimosa.host:http://localhost}")
	private String host;
	
	@Bean
	public UserCenterExtApi opeExtSdk() {
		return Feign.builder().encoder(new GsonEncoder()).decoder(new GsonDecoder()).logger(new Logger.JavaLogger().appendToFile("http.log")).logLevel(Logger.Level.FULL)
				.target(UserCenterExtApi.class, this.host);
	}
}
