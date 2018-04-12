package com.guohuai.basic.component.ext.web.parameter.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.guohuai.basic.component.ext.web.parameter.validation.Regex.RegexValidator;

/**
 * 正则表达式注解
 * 
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
	 * 
	 * @return
	 */
	RegexRule regexRule() default RegexRule.NUMBER;

	boolean caseSensitive() default true;

	String message() default "{com.guohuai.validation.regex}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public static enum RegexRule {
		/**
		 * email正则表达式规则
		 */
		EMAIL("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"),

		/*
		 * 数字正则表达式规则
		 */
		NUMBER("^[0-9]*$"),

		/**
		 * 英文字母正则表达式规则
		 */
		ENGLISH("^[a-zA-Z]*$"),

		/**
		 * 中文正则表达式规则
		 */
		CHINESE("^[\u4e00-\u9fa5]*$"),

		/**
		 * 中文，英文字母和数字及_正则表达式规则
		 */
		CHINESE_ENGLISH_NUMBER_("^[\u4e00-\u9fa5_a-zA-Z0-9]+$"),

		/**
		 * 中文，英文字母和数字正则表达式规则
		 */
		CHINESE_ENGLISH_NUMBER("^[\u4e00-\u9fa5_a-zA-Z0-9]+$"),

		/**
		 * url链接地址
		 */
		HTTP_START("^http://.+$"),

		/**
		 * 只有时分秒的时间格式
		 */
		TIME("^((\\d)|(1\\d)|(2[0-3])):[0-5]{1}[0-9]{1}:[0-5]{1}[0-9]{1}$");

		public String value;

		RegexRule(String value) {
			this.value = value;
		}
	}

	public class RegexValidator implements ConstraintValidator<Regex, Object> {

		String regularExpressions = null;
		private boolean caseSensitive = true;

		@Override
		public void initialize(Regex constraintAnnotation) {
			this.regularExpressions = constraintAnnotation.regexRule().value;
			this.caseSensitive = constraintAnnotation.caseSensitive();
		}

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			if (null == value) {
				return true;
			}
			Pattern p = Pattern.compile(this.regularExpressions);
			if (this.caseSensitive) {
				Matcher m = p.matcher(value.toString());
				return m.matches();
			} else {
				Matcher m = p.matcher(value.toString().toLowerCase());
				return m.matches();
			}
		}

	}

}
