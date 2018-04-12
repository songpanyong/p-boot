package com.guohuai.operate.component.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static String datePattern = "yyyy-MM-dd";
	private static String datetimePattern = "yyyy-MM-dd HH:mm:ss";

	public static boolean same(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		return c0.get(Calendar.YEAR) == c1.get(Calendar.YEAR) && c0.get(Calendar.MONTH) == c1.get(Calendar.MONTH) && c0.get(Calendar.DATE) == c1.get(Calendar.DATE);
	}

	public static boolean ge(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 >= l1;
	}

	public static String formatDate(long timestamp) {
		return format(timestamp, datePattern);
	}

	public static String formatDatetime(long timestamp) {
		return format(timestamp, datetimePattern);
	}

	public static String format(long timestamp, String pattern) {
		return new SimpleDateFormat(pattern).format(new Date(timestamp));
	}

	/**
	 * 获取2个时间之间的天数
	 * @param edate
	 * @param sdate
	 * @return
	 */
	public static int getDaysBetweenTwoDate(Timestamp edate, Timestamp sdate) {
		long days = (edate.getTime() - sdate.getTime()) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(days));
	}
}
