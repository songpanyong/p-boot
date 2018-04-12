package com.guohuai.tulip.component.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;


@lombok.Data
@Configuration
public class InvestorlabelSDK {
	
	@Value("${mimosa.host:http://106.15.90.69/}")
	private String mimosaHost; //106.15.90.69  127.0.0.1
	
	@Bean
	public InvestorlabelAPI tulipBootSdk() {
		return Feign.builder().encoder(new GsonEncoder()).decoder(new GsonDecoder()).logger(new Logger.JavaLogger().appendToFile("investorlabelAPI.log")).logLevel(Logger.Level.FULL)
				.target(InvestorlabelAPI.class, this.mimosaHost);
	}
	
}
