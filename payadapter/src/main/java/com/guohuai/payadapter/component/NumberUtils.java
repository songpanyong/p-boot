/**
 * f-road.com Inc.
 * Copyright (c) 2013 All Rights Reserved.
 */
package com.guohuai.payadapter.component;

import java.math.BigDecimal;

/**
 * @ClassName: StringUtils
 * @Description: 金额处理
 * @author xueyunlong
 * @date 2016年11月14日 下午3:03:46
 *
 */
public class NumberUtils {

	// 截取小数点两位数
	public static String tranfeString(String money) {
		if (money == null || "".equals(money.trim())) {
			return money;
		}

		int i = money.indexOf(".");
		if (i > 0) {
			money = money + "0000";
		}
		if (i > 0) {
			money = money.substring(0, i + 3);
		}

		return money;
	}

	public static boolean isNumber(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern
				.compile("[0-9]*");
		java.util.regex.Matcher match = pattern.matcher(str.trim());
		return match.matches();
	}

	public static boolean isBigDecimal(String str) {
		java.util.regex.Matcher match = null;
		if (isNumber(str) == true) {
			java.util.regex.Pattern pattern = java.util.regex.Pattern
					.compile("[0-9]*");
			match = pattern.matcher(str.trim());
		} else {
			if (str.trim().indexOf(".") == -1) {
				java.util.regex.Pattern pattern = java.util.regex.Pattern
						.compile("^[+-]?[0-9]*");
				match = pattern.matcher(str.trim());
			} else {
				java.util.regex.Pattern pattern = java.util.regex.Pattern
						.compile("^[+-]?[0-9]+(\\.\\d{1,100}){1}");
				match = pattern.matcher(str.trim());
			}
		}
		return match.matches();
	}
	
	/**
	 * 金额小数位数转换
	 * @param amount 金额
	 * @param digit 小数位数
	 * @param isround 是否四舍五入
	 * @return
	 */
	public static String AmountConversion(String amount, int digit, boolean isround) {
		//String to BigDecimal
		BigDecimal bd = new BigDecimal(amount);
		String result = null;
		if(isround){
			result = bd.setScale(digit,BigDecimal.ROUND_HALF_UP).toString();
		}else{
			result = bd.setScale(digit,BigDecimal.ROUND_DOWN).toString();
		}
		return result;
	}

	public static void main(String[] args) {
		//System.out.println(tranfeString("111"));
		System.out.println(compare("5001", "5000"));
	}
	
	/**
	 * 
	 * <pre>
	 * 大于 返回true
	 * </pre>
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int compare(String v1, String v2) {

		BigDecimal b1 = new BigDecimal(v1);

		BigDecimal b2 = new BigDecimal(v2);

		return b1.compareTo(b2);
	}
	


}
