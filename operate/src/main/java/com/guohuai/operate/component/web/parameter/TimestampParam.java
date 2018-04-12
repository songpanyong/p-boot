package com.guohuai.operate.component.web.parameter;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Timestamp;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.TYPE })
public @interface TimestampParam {

	public static class TimestampArgumentResolver implements HandlerMethodArgumentResolver {

		@Override
		public boolean supportsParameter(MethodParameter parameter) {
			Annotation[] as = parameter.getParameterAnnotations();
			for (Annotation a : as) {
				System.out.println(a.getClass().getName());
			}
			return parameter.getParameterType() == Timestamp.class && parameter.hasParameterAnnotation(TimestampParam.class);
		}

		@Override
		public Timestamp resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
			String pn = parameter.getParameterName();
			String pv = webRequest.getParameter(pn);
			if (null == pv || pv.trim().equals("")) {
				return null;
			}
			long ts = Long.parseLong(pv.trim());
			return new Timestamp(ts);
		}

	}

}
