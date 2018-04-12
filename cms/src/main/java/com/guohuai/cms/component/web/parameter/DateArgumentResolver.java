package com.guohuai.cms.component.web.parameter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class DateArgumentResolver implements HandlerMethodArgumentResolver {

	private Map<String, DateFormat> formater = new HashMap<String, DateFormat>();

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType() == Date.class && parameter.hasParameterAnnotation(DateParam.class);
	}

	@Override
	public Date resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		DateParam dp = parameter.getParameterAnnotation(DateParam.class);
		String p = dp.value();
		if (!this.formater.containsKey(p.trim())) {
			this.formater.put(p.trim(), new SimpleDateFormat(p.trim()));
		}
		String pn = parameter.getParameterName();
		String pv = webRequest.getParameter(pn);
		if (null == pv || pv.trim().equals("")) {
			return null;
		}
		DateFormat df = this.formater.get(p);
		return df.parse(pv);
	}

}
