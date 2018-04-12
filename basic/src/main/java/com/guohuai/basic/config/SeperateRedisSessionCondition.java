package com.guohuai.basic.config;
import org.springframework.context.annotation.Condition;   
import org.springframework.context.annotation.ConditionContext;   
import org.springframework.core.type.AnnotatedTypeMetadata;   
   
public class SeperateRedisSessionCondition implements Condition{  
   
  @Override   
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {  
	  String redisBuzzHost=context.getEnvironment().getProperty("spring.redis.bhost");
    return redisBuzzHost!=null && !"".equals(redisBuzzHost);  
  }  
}  