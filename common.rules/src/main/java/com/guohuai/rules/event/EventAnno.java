package com.guohuai.rules.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventAnno {
	
	@AliasFor("value")
	String desc() default "";
	
	@AliasFor("desc")
	String value() default "";

	/**
	 * 枚举值.
	 * @return
	 */
	String enums() default "";
}
