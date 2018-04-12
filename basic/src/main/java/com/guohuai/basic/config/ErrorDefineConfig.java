package com.guohuai.basic.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "error")
@PropertySource("classpath:errordefine.properties")
@Component
public class ErrorDefineConfig {

	public static Map<Integer, String> define = new HashMap<Integer, String>();

	public Map<Integer, String> getDefine() {
		return ErrorDefineConfig.define;
	}

	public void setDefine(Map<Integer, String> define) {
		ErrorDefineConfig.define = define;
	}

}
