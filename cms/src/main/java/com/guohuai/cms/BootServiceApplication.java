package com.guohuai.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.guohuai", "net.kaczmarzyk"})
@EnableAutoConfiguration
@SpringBootApplication
@EnableConfigurationProperties
public class BootServiceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(BootServiceApplication.class, args);
	}

}
