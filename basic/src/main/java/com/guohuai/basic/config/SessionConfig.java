package com.guohuai.basic.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.ConfigureNotifyKeyspaceEventsAction;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.util.StringUtils;

import com.guohuai.basic.component.ext.web.PersistentCookieHttpSessionStrategy;

@EnableRedisHttpSession
public class SessionConfig {
	@Value("${spring.redis.bhost:#{null}}")
	private String bHost;

	@Value("${spring.redis.bport:3306}")
	private int bPort;

	@Value("${spring.redis.bpassword:#{null}}")
	private String bPassword;

	@Value("${redis.session.maxInactiveInterval:1800}")
	private int maxInactiveInterval;

	@Value("${redis.session.namespace:}")
	private String redisNamespace;
	
	@Value("${spring.redis.pool.bmax-active:0}")
	private int bMaxActive;

	@Value("${redis.no_op:no}")
	String noOp;

	@Bean
	public ConfigureRedisAction configureRedisAction() {
		if (noOp.equals("no")) {
			return new ConfigureNotifyKeyspaceEventsAction();
		}
		return ConfigureRedisAction.NO_OP;
	}

	@Primary
	@Bean
	public RedisOperationsSessionRepository sessionRepository(
			@Qualifier("sessionRedisTemplate") RedisOperations<Object, Object> sessionRedisTemplate,
			ApplicationEventPublisher applicationEventPublisher) {
		RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
		sessionRepository.setApplicationEventPublisher(applicationEventPublisher);
		sessionRepository.setDefaultMaxInactiveInterval(maxInactiveInterval);

		if (StringUtils.hasText(redisNamespace)) {
			sessionRepository.setRedisKeyNamespace(redisNamespace);
		}

		sessionRepository.setRedisFlushMode(RedisFlushMode.ON_SAVE);
		return sessionRepository;
	}

	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new PersistentCookieHttpSessionStrategy();
	}

	@Primary
	@Bean
	@Conditional(SeperateRedisSessionCondition.class)
	public RedisTemplate<String, String> redisTemplate() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(bHost);
		factory.setPort(bPort);
		if (!StringUtils.isEmpty(bPassword)) {
			factory.setPassword(bPassword);
		}
		if (this.bMaxActive>0) {
			factory.getPoolConfig().setMaxTotal(this.bMaxActive);
		}
		factory.afterPropertiesSet();
		StringRedisTemplate template = new StringRedisTemplate(factory);
		return template;
	}
}