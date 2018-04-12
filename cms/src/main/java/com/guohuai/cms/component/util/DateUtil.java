package com.guohuai.cms.component.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.guohuai.cms.component.exception.MoneyException;

/**
 * Date相关工具
 * 
 * @author tidecc
 */
public class DateUtil {

	public static String defaultDatePattern = "yyyy-MM-dd";
	public static final String fullDatePattern = "yyyy-MM-dd HH:mm:ss";
	public static String timePattern = "HH:mm:ss";

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return defaultDatePattern;
	}

	/**
	 * 返回预设Format的当前日期字符串
	 */
	public static String getToday() {
		Date today = new Date();
		return format(today);
	}
	
	/**
	 *获取当前年份 
	 */
	public static String getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.get(Calendar.YEAR));
	}
	
	/**
	 * 获取当前月份
	 */
	public static String getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return String.valueOf(addZero(cal.get(Calendar.MONTH) + 1));
	}
	
	public static String addZero(int args) {
		if (args < 10) {
			return "0" + args;
		}
		return "" + args;
	}
	
	/**
	 *获取当前月份中的第几天
	 */
	public static String getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		return String.valueOf(addZero(cal.get(Calendar.DATE)));
	}
	
	public static String getCurrentDay(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		return String.valueOf(addZero(cal.get(Calendar.DATE)));
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 */
	public static String format(Date date) {
		return date == null ? " " : format(date, getDatePattern());
	}

	public static String format(long timestamp) {
		return format(new Date(timestamp));
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(Date date, String pattern) {
		return date == null ? " " : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 使用参数Format格式化sqlDate成字符串
	 * @param date sql日期
	 * @param pattern 日期模板
	 * @return
	 */
	public static String formatSqlDate(java.sql.Date date, String pattern){
		return date == null ? " " : new SimpleDateFormat(pattern).format(date);
	}
	
	public static String format(long timestamp, String pattern) {
		return format(new Date(timestamp), pattern);
	}

	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parse(String strDate) throws ParseException {
		return StringUtils.isBlank(strDate) ? null : parse(strDate, getDatePattern());
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(String strDate, String pattern) throws ParseException {
		return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
	}

	/**
	 * 在日期上增加数个整月
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 获取指定年月的最后一天的日期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(String year, String month) {
		Calendar cal = Calendar.getInstance();
		// 年
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		// 月，因为Calendar里的月是从0开始，所以要-1
		// cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		// 日，设为一号
		cal.set(Calendar.DATE, 1);
		// 月份加一，得到下个月的一号
		cal.add(Calendar.MONTH, 1);
		// 下一个月减一为本月最后一天
		cal.add(Calendar.DATE, -1);
		return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获得月末是几号
	}

	/**
	 * 获取指定"年, 月, 日"的Date实例
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String year, String month, String day) throws ParseException {
		String result = year + "-" + (month.length() == 1 ? ("0 " + month) : month) + "-" + (day.length() == 1 ? ("0 " + day) : day);
		return parse(result);
	}

	/**
	 * format Timestamp date
	 * 
	 * @param timestamp
	 * @return formatted date
	 */
	public static String formatFullPattern(Date timestamp) {
		Date currentTime = timestamp;
		SimpleDateFormat format = new SimpleDateFormat(fullDatePattern);
		String dateString = format.format(currentTime);
		return dateString;
	}
	
	/**
	 * format Timestamp date
	 * 
	 * @return formatted date
	 */
	public static String formatFullPattern() {
		Date currentTime = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat(fullDatePattern);
		String dateString = format.format(currentTime);
		return dateString;
	}

	/**
	 * get different days between two dates
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getDifferentDays(Date d1, Date d2) {
		long diff = d1.getTime() - d2.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		return days;
	}
	
	/**
	 * get different days between two SqlDates
	 * @param d1 SqlDate
	 * @param d2 SqlDate
	 * @return
	 */
	public static long getDifferentSqlDays(java.sql.Date d1, java.sql.Date d2) {
		long diff = d1.getTime() - d2.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		return days;
	}

	/**
	 * add days on a date
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return c.getTime();
	}
	
	
	public static java.sql.Date addSQLDays(int days) {
		return addSQLDays(new java.sql.Date(System.currentTimeMillis()), days);
	}
	
	public static java.sql.Date addSQLDays(java.sql.Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return new java.sql.Date(cal.getTimeInMillis());
	}
	

	/**
	 * 获取系统当前SQL类型的Timestamp
	 * 
	 * @return 当前时间
	 */
	public static Timestamp getSqlCurrentDate() {
		return new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
	}

	/**
	 * 获取系统当前SQL类型的Date
	 * 
	 * @return 当前时间
	 */
	public static java.sql.Date getSqlDate() {
		return new java.sql.Date(Clock.DEFAULT.getCurrentTimeInMillis());
	}

	/**
	 * 基于时间字符串, 获取Timestamp对象
	 * 
	 * @param dateTimeStr
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp fetchTimestamp(String dateTimeStr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date dateObj = formatter.parse(dateTimeStr);
		String dateTimeFormatted = formatter.format(dateObj);
		return Timestamp.valueOf(dateTimeFormatted);
	}
	
	/**
	 * 基于日期字符串，获取java.sql.Date对象
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static java.sql.Date fetchSqlDate(String strDate) throws ParseException {
		return new java.sql.Date(parse(strDate).getTime());
	}
	
	
	
	
	/**
	 * 基于Timestamp对象, 转换为格式化的字符串
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getDateTimeFormated(Timestamp timestamp) {
	    Date currentTime = timestamp;
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateString = format.format(currentTime);
	    return dateString;
	 }
	
	public static String getCurrentDate(){
		return format(new Date(), "yyyyMMdd");
	}
	
	
	
	
	public static void main(String[] args){
		System.out.println(getCurrentDate());
		
	}
	
	

	public static String convert(String date, String pattern, String product) {
		DateFormat f = new SimpleDateFormat(pattern);
		DateFormat t = new SimpleDateFormat(product);
		try {
			String s = t.format(f.parse(date));
			return s;
		} catch (ParseException e) {
			throw new MoneyException(e);
		}
	}

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
//
//	public static boolean gt(java.sql.Date param0, java.sql.Date param1) {
//		Calendar c0 = Calendar.getInstance();
//		c0.setTimeInMillis(param0.getTime());
//		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);
//
//		Calendar c1 = Calendar.getInstance();
//		c1.setTimeInMillis(param1.getTime());
//		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);
//
//		return l0 > l1;
//	}
	
	public static boolean gt(java.util.Date param1) {
		
		return gt(new java.util.Date(), param1);
	}
	
	/**
	 * 
	 * @param param0
	 * @param param1
	 * @return true param0 >= param1
	 */
	public static boolean gt(java.util.Date param0, java.util.Date param1) {
		if (null == param1) {
			return false;
		}
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		return c0.compareTo(c1) >= 0;
	}

	/**
	 * 获取上一日时间
	 * @return
	 */
	public static java.sql.Date getBeforeDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		java.sql.Date d = new java.sql.Date(c.getTimeInMillis());
		return d;
	}
	/**
	 * 当前日间是否为T日
	 * @return
	 */
	public static boolean isT(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY) <15;
	}
}
