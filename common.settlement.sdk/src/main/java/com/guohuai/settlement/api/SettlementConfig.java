package com.guohuai.settlement.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.guohuai.account.api.AccountSdk;

import feign.Logger;
import feign.Logger.Level;

@Configuration
public class SettlementConfig{

	@Value("${common.settlement.host:http://127.0.0.1}")
	private String host;
	
	@Value("${logger.feign.level:BASIC}")
	private String loggerLevel;
	
	@Bean
	public SettlementSdk configSettlementSdk() {
		Level level=getLogLevel();
		SettlementSdk sdk = new SettlementSdk(host,level);
		return sdk;
	}
	
	@Bean
	public AccountSdk configAccountSdk() {
		Level level=getLogLevel();
		AccountSdk sdk = new AccountSdk(this.host,level);
		return sdk;
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
