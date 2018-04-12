package com.guohuai.cms.component.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.guohuai.cms.component.exception.MoneyException;

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
	
	public final static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String validChineseNumChar(String param) {
		if (StringUtil.isEmpty(param)) {
			return StringUtil.EMPTY;
		}
		Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9,，.。]{1,250}$");
		Matcher m = pattern.matcher(param);
		if (!m.matches()) {
			throw new MoneyException("只支持中文、英文和数字（250字以内）！");
		}
		return param;
	}
	
}
