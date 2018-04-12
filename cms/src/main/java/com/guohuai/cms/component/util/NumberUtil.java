package com.guohuai.cms.component.util;

import java.text.NumberFormat;

public final class NumberUtil {

	private static NumberFormat formater = NumberFormat.getInstance();

	static {
		formater.setGroupingUsed(false);
		formater.setMaximumFractionDigits(32);
		formater.setMaximumIntegerDigits(32);
	}

	public static String valueOf(double d) {
		return formater.format(d);
	}

	public static String valueOf(int i) {
		return formater.format(i);
	}

	public static String valueOf(long l) {
		return formater.format(l);
	}
	
	public static void main(String[] args) {
		System.out.println(Float.valueOf("100.00").longValue());
	}

}
