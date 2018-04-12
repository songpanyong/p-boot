package com.guohuai.operate.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import feign.Logger;
import feign.Logger.Level;

@Component
public class OperateSdkConfig {

	@Value("${operate.host:http://cms.demo.guohuaigroup.com/}")
	private String host;
	
	@Value("${logger.feign.level:BASIC}")
	private String loggerLevel;

	@Bean
	public TradeCalendarSdk configTradeCalendarSdk() {
		Level level = getLogLevel();
		TradeCalendarSdk sdk = new TradeCalendarSdk(this.host, level);
		return sdk;
	}

	@Bean
	public OperateSdk configOperateSdk() {
		Level level = getLogLevel();
		OperateSdk sdk = new OperateSdk(this.host, level);
		return sdk;
	}

	@Bean
	@Autowired
	public AdminSdk configAdminSdk(HttpSession session) {
		Level level = getLogLevel();
		AdminSdk sdk = new AdminSdk(session, this.host, level);
		return sdk;
	}

	@Bean
	@Autowired
	public AdminAmpSdk configAdminAmpSdk(HttpSession session) {
		Level level = getLogLevel();
		AdminAmpSdk sdk = new AdminAmpSdk(session, this.host, level);
		return sdk;
	}
	@Bean
	@Autowired
	public AdminLogSdk configAdminLogSdk(HttpSession session){
		Level level = getLogLevel();
		AdminLogSdk sdk=new AdminLogSdk(session, this.host, level);
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
