package com.guohuai.operate.component.util;

import java.util.UUID;

public class StringUtil {

	public final static String EMPTY = "";

	public final static boolean isEmpty(String s) {
		return s == null || s.trim().equals(EMPTY);
	}

	public final static String vague(String s, int begin, int end) {
		if (isEmpty(s)) {
			return s;
		}

		if (s.length() <= begin + end) {
			return s;
		}
		char[] chars = s.toCharArray();
		for (int i = begin; i < chars.length - end; i++) {
			chars[i] = '*';
		}

		return new String(chars);

	}

	public final static String uuid() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
	
	public final static boolean in(String param, String... factor) {
		if (null != factor && factor.length != 0) {
			for (String f : factor) {
				if (param.equals(f)) {
					return true;
				}
			}
		}
		return false;
	}

}
