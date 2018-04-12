package com.guohuai.basic.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String datePattern = "yyyy-MM-dd";
	public static String datetimePattern = "yyyy-MM-dd HH:mm:ss";
	public static String timePattern = "HH:mm:ss";

	public static boolean eq(java.util.Date param0, java.util.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		return c0.get(Calendar.YEAR) == c1.get(Calendar.YEAR) && c0.get(Calendar.MONTH) == c1.get(Calendar.MONTH) && c0.get(Calendar.DATE) == c1.get(Calendar.DATE);
	}

	public static boolean eq(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		return c0.get(Calendar.YEAR) == c1.get(Calendar.YEAR) && c0.get(Calendar.MONTH) == c1.get(Calendar.MONTH) && c0.get(Calendar.DATE) == c1.get(Calendar.DATE);
	}

	public static boolean ge(java.util.Date param0, java.util.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 >= l1;
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

	public static boolean gt(java.util.Date param0, java.util.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 > l1;
	}

	public static boolean gt(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 > l1;
	}

	public static boolean le(java.util.Date param0, java.util.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 <= l1;
	}

	public static boolean le(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 <= l1;
	}

	public static boolean lt(java.util.Date param0, java.util.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 < l1;
	}

	public static boolean lt(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 < l1;
	}

	public static int compare(java.util.Date param0, java.util.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 > l1 ? 1 : (l0 < l1 ? -1 : 0);
	}

	public static int compare(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 > l1 ? 1 : (l0 < l1 ? -1 : 0);
	}

	public static String formatDate(long timestamp) {
		return format(timestamp, datePattern);
	}

	public static String formatDatetime(long timestamp) {
		return format(timestamp, datetimePattern);
	}

	public static String format(long timestamp, String pattern) {
		return format(new Date(timestamp), pattern);
	}

	public static String format(Date date, String pattern) {
		return date == null ? null : new SimpleDateFormat(pattern).format(date);
	}

	public static Date parseDate(String date, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date lastDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static Date nextDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	public static Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	public static java.sql.Date addDays(java.sql.Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public static Date addMonths(Date date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	public static java.sql.Date addMonths(java.sql.Date date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public static Date addWorkdays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		for (int i = 0; i < days;) {
			calendar.add(Calendar.DATE, 1);
			if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				i++;
			}
		}

		return calendar.getTime();
	}

	public static java.sql.Date addWorkdays(java.sql.Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		for (int i = 0; i < days;) {
			calendar.add(Calendar.DATE, 1);
			if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				i++;
			}
		}

		return new java.sql.Date(calendar.getTimeInMillis());
	}

	public static java.util.Date beginTimeInMillis() {
		return beginTimeInMillis(new Date());
	}

	public static java.util.Date endTimeInMillis() {
		return endTimeInMillis(new Date());
	}

	public static java.util.Date beginTimeInMillis(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static java.util.Date endTimeInMillis(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	public static int intervalDays(java.util.Date start, java.util.Date end) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(start);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(end);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);

		long x = (c2.getTimeInMillis() - c1.getTimeInMillis());

		return (int) (x / 1000 / 24 / 3600);
	}
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.set(2017, 2, 31);
		System.out.println(formatDate(c.getTimeInMillis()));
		c.add(Calendar.MONTH, -1);
		System.out.println(formatDate(c.getTimeInMillis()));
	}

	public static int monthDays(int year, int month) {
		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, year);
		c1.set(Calendar.MONTH, month);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);

		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.YEAR, year);
		c2.set(Calendar.MONTH, month + 1);
		c2.set(Calendar.DAY_OF_MONTH, 1);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);

		return intervalDays(c1.getTime(), c2.getTime());
	}

}
