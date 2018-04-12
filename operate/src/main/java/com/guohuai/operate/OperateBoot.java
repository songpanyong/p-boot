package com.guohuai.operate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = {"com.guohuai" })
@EnableAutoConfiguration
@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class OperateBoot {

	public static void main(String[] args) {

		for (String arg : args) {
			System.out.println("ARG:" + arg);
		}

		SpringApplication.run(OperateBoot.class, args);
	}

}
