package com.guohuai.basic.component.sms;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

public class UTF8Encoder implements Encoder {

	@Override
	public void encode(Object object, Type bodyType, RequestTemplate template)
			throws EncodeException {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) object;
		StringBuilder buffer = new StringBuilder();
		for(String k : map.keySet()) {
			if (buffer.length() > 0) {
				buffer.append("&");
			}
			buffer.append(k).append("=");
			try {
				String encoded = URLEncoder.encode(map.get(k), "UTF8");
				buffer.append(encoded);
			} catch (UnsupportedEncodingException e) {
				buffer.append(map.get(k));
			}
		}
		
		template.body(buffer.toString());
	}

}
