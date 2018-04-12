package com.guohuai.tulip.component.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;

@lombok.Data
@Configuration
public class SendCmsSDK {

	@Value("${cms.host:localhost}")
	private String host;
	
	@Bean
	public SendCmsAPI sendCmsBootSdk() {
		return Feign.builder().target(SendCmsAPI.class, this.host);
	}
}
