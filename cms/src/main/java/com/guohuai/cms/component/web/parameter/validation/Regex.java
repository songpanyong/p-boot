package com.guohuai.cms.component.web.parameter.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 正则表达式注解
 * @author wangyan
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = RegexValidator.class)
public @interface Regex {
	
	/**
	 * 正则表达式
	 * @return
	 */
	RegexRule regexRule() default RegexRule.NUMBER;
	
	boolean caseSensitive() default true;
	
	String message() default "{com.tianan.validation.regex}";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}
