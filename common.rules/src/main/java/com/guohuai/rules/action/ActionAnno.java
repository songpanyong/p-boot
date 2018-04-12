package com.guohuai.rules.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionAnno {
	String id() default "";
	
	@AliasFor("value")
	String desc() default "";
	
	@AliasFor("desc")
	String value() default "";

	ActionParam[] params() default {};
}
