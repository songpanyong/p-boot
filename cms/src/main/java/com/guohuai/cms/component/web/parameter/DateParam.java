package com.guohuai.cms.component.web.parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.TYPE })
public @interface DateParam {

	String value() default "yyyy-MM-dd HH:mm:ss";

}
