package com.guohuai.basic.common;

public class EmailUtil {

	public static final String build(String name, String email) {
		if (null == email || email.trim().equals("")) {
			return "";
		} else {
			if (null == name || name.trim().equals("")) {
				return email.trim();
			} else {
				return String.format("%s<%s>", name.trim(), email.trim());
			}
		}
	}

}
