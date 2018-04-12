package com.guohuai.cms.component.web.parameter.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
