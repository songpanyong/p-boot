package com.guohuai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//Spring Java Config的标识
@Configuration
//Spring 默认扫描ClassPath中的Component，以本类所在地package为起点。
@ComponentScan(basePackages = {"com.guohuai"})
//Spring Boot的AutoConfig
@EnableAutoConfiguration
//@EnableWebMvc
@SpringBootApplication
@EnableConfigurationProperties
//@ServletComponentScan
public class GhTulipBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(GhTulipBootApplication.class, args);
	}
}