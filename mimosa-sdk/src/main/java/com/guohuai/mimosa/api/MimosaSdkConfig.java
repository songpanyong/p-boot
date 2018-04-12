package com.guohuai.mimosa.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import feign.Logger;
import feign.Logger.Level;
import lombok.Data;

/**
 * mimosa sdk 参数配置
 * 
 * @author wanglei
 *
 */
@Data
@Component
public class MimosaSdkConfig {

	/** mimosasdk的服务端地址 */
	@Value("${mimosa.host:http://127.0.0.1/}")
	private String host;
	
	@Value("${logger.feign.level:BASIC}")
	private String loggerLevel;

	/**
	 * HTTP请求数据JSON格式
	 * 
	 * <pre>
	 * mimosasdk服务接口只接收json格式数据
	 * </pre>
	 */
	public static final String CONTENT_TYPE_JSON = "Content-Type: application/json";

	@Bean
	public MimosaSdk configMimosaSdk() {
		Level level = getLogLevel();
		
		return new MimosaSdk(this.host, level);
	}

	public Level getLogLevel(){
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
