package com.guohuai.cms.component.web.parameter.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumerationsValidator implements ConstraintValidator<Enumerations, Object> {

	private Set<String> elements = new HashSet<String>();
	private boolean caseSensitive = true;

	@Override
	public void initialize(Enumerations constraintAnnotation) {
		this.caseSensitive = constraintAnnotation.caseSensitive();
		for (String element : constraintAnnotation.values()) {
			if (!this.caseSensitive) {
				this.elements.add(element.toLowerCase());
			} else {
				this.elements.add(element);
			}
		}
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (null == value) {
			return true;
		}
		if (this.caseSensitive) {
			return this.elements.contains(value.toString());
		} else {
			return this.elements.contains(value.toString().toLowerCase());
		}
	}

}
