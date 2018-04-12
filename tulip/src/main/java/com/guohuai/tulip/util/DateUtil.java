package com.guohuai.tulip.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import com.guohuai.basic.component.exception.GHException;


public class DateUtil {

	public static final String datePattern = "yyyy-MM-dd";
	public static final String datetimePattern = "yyyy-MM-dd HH:mm:ss";
	public static final String timePattern = "HH:mm:ss";
	
	public static boolean same(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		return c0.get(Calendar.YEAR) == c1.get(Calendar.YEAR) && c0.get(Calendar.MONTH) == c1.get(Calendar.MONTH) && c0.get(Calendar.DATE) == c1.get(Calendar.DATE);
	}
	/**
	 * java.sql.Date类型比较
	 * @param param0
	 * @param param1
	 * @return true param0>param1
	 */
	public static boolean ge(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 >= l1;
	}

	/**
	 * java.sql.Date类型比较
	 * @param param0
	 * @param param1
	 * @return true param0 >= param1
	 */
	public static boolean gt(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 > l1;
	}
	/**
	 * java.sql.Date类型比较
	 * @param param0
	 * @param param1
	 * @return true param0<param1
	 */
	public static boolean le(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 <= l1;
	}
	/**
	 * java.sql.Date类型比较
	 * @param param0
	 * @param param1
	 * @return true param0 <= param1
	 */
	public static boolean lt(java.sql.Date param0, java.sql.Date param1) {
		Calendar c0 = Calendar.getInstance();
		c0.setTimeInMillis(param0.getTime());
		long l0 = c0.get(Calendar.YEAR) * 10000 + c0.get(Calendar.MONTH) * 100 + c0.get(Calendar.DATE);

		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(param1.getTime());
		long l1 = c1.get(Calendar.YEAR) * 10000 + c1.get(Calendar.MONTH) * 100 + c1.get(Calendar.DATE);

		return l0 < l1;
	}
	
	/**
	 * 格式化Long类型为yyyy-MM-dd日期
	 * @param timestamp
	 * @return
	 */
	public static String formatDate(long timestamp) {
		return format(timestamp, datePattern);
	}
	
	/**
	 * 格式化Long类型为yyyy-MM-dd HH:mm:ss日期
	 * @param timestamp
	 * @return
	 */
	public static String formatDatetime(long timestamp) {
		return format(timestamp, datetimePattern);
	}
	/**
	 * pattern格式的字符串转换为java.util.Date
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String date, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			throw new GHException(e);
		}
	}
	/**
	 * 获取两个java.util.Date日期的间隔天数
	 * @param sdate
	 * @param edate
	 * @return
	 */
	public static int getDaysBetweenTwoDate(Date sdate, Date edate) {
		long days = (edate.getTime() - sdate.getTime()) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(days));
	}

	public static java.sql.Date formatUtilToSql(Date date) {
		String sdate = format(date, datePattern);
		return java.sql.Date.valueOf(sdate);
	}

	/**
	 * 格式化 sql date
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(java.sql.Date sqlDate) {
		Date date = new Date(sqlDate.getTime());
		return new SimpleDateFormat(datePattern).format(date);
	}

	/**
	 * 将字符串转换成默认格式（XXXX-XX-XX）的sql date
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static java.sql.Date parseToSqlDate(String date) {
		try {
			Date sdate = new SimpleDateFormat(datePattern).parse(date);
			return new java.sql.Date(sdate.getTime());
		} catch (ParseException e) {
			throw new GHException(e);
		}
	}

	/**
	 * 获取指定日期的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date lastDate(Date date) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, -1);
		date = parseDate(format(calender.getTime(), datePattern), datePattern);

		return date;
	}

	/**
	 * 天数增加
	 * 
	 * @param date
	 * @param count
	 * @return
	 */
	public static Date addDay(Date date, int count) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, count);

		return calender.getTime();
	}

	public static int compare_sql_date(java.sql.Date src, java.sql.Date src1) {
		Date date = new Date(src1.getTime());
		return compare_date(src, date);
	}

	/**
	 * 比较日期大小，判断是否超越参照日期
	 * 
	 * @param src
	 * @param src
	 * @return boolean; true:DATE1>DATE2;
	 */
	public static int compare_date(java.sql.Date src, Date src1) {

		String date1 = convertDate2String(datePattern, src);
		String date2 = convertDate2String(datePattern, src1);
		DateFormat df = new SimpleDateFormat(datePattern);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 比较日期大小，判断是否大于或等于当前日期
	 * 
	 * @param src
	 * @return boolean; true:DATE1<=DATE2;
	 */
	public static boolean compare_current(java.sql.Date src) {

		String date1 = convertDate2String(datePattern, src);
		String date2 = convertDate2String(datePattern, getCurrDate());
		DateFormat df = new SimpleDateFormat(datePattern);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() < dt2.getTime()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	/**
	 * 比较日期大小，判断是否小于或等于当前日期
	 * 
	 * @param src
	 * @return boolean; true:DATE1<DATE2;
	 */
	public static boolean compare_current_(java.sql.Date src) {

		String date1 = convertDate2String(datePattern, src);
		String date2 = convertDate2String(datePattern, getCurrDate());
		DateFormat df = new SimpleDateFormat(datePattern);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	/**
	 * 比较两个日期是否相等
	 * 
	 * @param src
	 * @param src
	 * @return boolean; true:DATE1>DATE2;
	 */
	public static boolean equal_date(java.sql.Date src, Date src1) {
		if (null == src || null == src1) {
			return false;
		}

		String date1 = convertDate2String(datePattern, src);
		String date2 = convertDate2String(datePattern, src1);
		DateFormat df = new SimpleDateFormat(datePattern);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() == dt2.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return true;
	}

	/**
	 * 转换日期得到指定格式的日期字符串
	 * 
	 * @param formatString
	 *            需要把目标日期格式化什么样子的格式。例如,yyyy-MM-dd HH:mm:ss
	 * @param targetDate
	 *            目标日期
	 * @return
	 */
	public static String convertDate2String(String formatString, Date targetDate) {
		SimpleDateFormat format = null;
		String result = null;
		if (targetDate != null) {
			format = new SimpleDateFormat(formatString);
			result = format.format(targetDate);
		} else {
			return null;
		}
		return result;
	}

	/**
	 * 获取当前月的天数
	 * 
	 * @return
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar calender = Calendar.getInstance(Locale.CHINA);
		calender.setTime(date);
		int days = calender.getActualMaximum(Calendar.DATE);
		return days;
	}

	/**
	 * 获取两个时间的时间间隔
	 * 
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public static int getDaysBetween(Date sdate, Date edate) {
		Calendar beginDate = Calendar.getInstance();
		beginDate.setTime(sdate);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(edate);
		if (beginDate.after(endDate)) {
			Calendar swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int days = endDate.get(Calendar.DAY_OF_YEAR) - beginDate.get(Calendar.DAY_OF_YEAR) + 1;
		int year = endDate.get(Calendar.YEAR);
		if (beginDate.get(Calendar.YEAR) != year) {
			beginDate = (Calendar) beginDate.clone();
			do {
				days += beginDate.getActualMaximum(Calendar.DAY_OF_YEAR);
				beginDate.add(Calendar.YEAR, 1);
			} while (beginDate.get(Calendar.YEAR) != year);
		}
		return days;
	}

	/**
	 * 获取当前日期 格式:XXXX-XX-XX
	 * 
	 * @param date
	 * @return
	 */
	public static Date getCurrDate() {
		String sdate = format(System.currentTimeMillis(), datePattern);

		return parseDate(sdate, datePattern);
	}

	/**
	 * 获取当前日期 格式:XXXX-XX-XX
	 * 
	 * @param date
	 * @return
	 */
	public static Date getCurrDate(Timestamp time) {
		String sdate = format(time.getTime(), datePattern);

		return parseDate(sdate, datePattern);
	}

	/**
	 * 获取当前日期的当前日历
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(Calendar.DATE);
	}

	/**
	 * 获取当前日期的年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYearFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取当前日期的月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthFromDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;

		return month;
	}

	/**
	 * 获取当前日期的月份-1
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);

		return month;
	}

	// 日期转换格式化
	public static long longToDate(long time) {

		try {
			SimpleDateFormat sf = new SimpleDateFormat(datePattern);
			String date = sf.format(new Date(time));
			return sf.parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;
	}

	public static int longToInt(String format, long time) {

		try {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			int date = Integer.valueOf(sf.format(new Date(time)));
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * 返回预设Format的当前日期字符串
	 */
	public static String getToday() {
		Date today = new Date();
		return format(today);
	}

	/**
	 * 获取当前年份
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
	 * 获取当前月份中的第几天
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
	 * 
	 * @param date
	 *            sql日期
	 * @param pattern
	 *            日期模板
	 * @return
	 */
	public static String formatSqlDate(java.sql.Date date, String pattern) {
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
	public static Date parse(String strDate, String pattern) {
		try {
			return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
		SimpleDateFormat format = new SimpleDateFormat(datetimePattern);
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
		SimpleDateFormat format = new SimpleDateFormat(datetimePattern);
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
	 * 
	 * @param d1
	 *            SqlDate
	 * @param d2
	 *            SqlDate
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

	public static java.sql.Date addSQLDays(Timestamp timestamp, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		cal.add(Calendar.DATE, days);
		return new java.sql.Date(cal.getTimeInMillis());
	}

	public static java.sql.Date addSQLWorkDays(java.sql.Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		for (int i = 0; i < days; i++) {
			System.out.println(DateUtil.format(cal.getTime()));
			cal.add(Calendar.DATE, 1);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				i--;
			}
		}
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
	 * 
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

	public static String getCurrentDate() {
		return format(new Date(), "yyyyMMdd");
	}

	public static String convert(String date, String pattern, String product) {
		DateFormat f = new SimpleDateFormat(pattern);
		DateFormat t = new SimpleDateFormat(product);
		try {
			String s = t.format(f.parse(date));
			return s;
		} catch (ParseException e) {
			throw new GHException(e);
		}
	}

	/**
	 * 获取上一日时间
	 * 
	 * @return
	 */
	public static java.sql.Date getBeforeDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		java.sql.Date d = new java.sql.Date(c.getTimeInMillis());
		return d;
	}

	public static java.sql.Date getAfterDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		java.sql.Date d = new java.sql.Date(c.getTimeInMillis());
		return d;
	}

	/**
	 * 获取下一个工作日
	 * 
	 * @return
	 */
	public static String getNextWorkDay() {

		return format(getAfterDate(), "yyyyMMdd");
	}

	/**
	 * 获取下一个自然日
	 * 
	 * @return
	 */
	public static String getNextNaturalDay() {
		return format(getAfterDate(), "yyyyMMdd");
	}

	/**
	 * 获取交易日开始时间
	 * 
	 * @param offsetDate
	 * @return
	 */
	public static Timestamp getTradeStartTime(java.sql.Date offsetDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(offsetDate);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 获取交易日结束时间
	 * 
	 * @param offsetDate
	 * @return
	 */
	public static Timestamp getTradeEndTime(java.sql.Date offsetDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(offsetDate);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return new Timestamp(cal.getTimeInMillis());
	}
	/**
	 * 获取日期结束时间
	 * 23:59:59
	 * @param offsetDate
	 * @return
	 */
	public static Timestamp getEndDateTime(Date offsetDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(offsetDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * 格式化指定日期为yyyyMMdd格式
	 * 
	 * @param date
	 * @return
	 */
	public static String defaultFormat(Date date) {
		return format(date, "yyyyMMdd");
	}
	
	/**
	 * 格式化指定日期为yyyyMMddHH格式
	 * 
	 * @param date
	 * @return
	 */
	public static String defaultDateHourFormat(Date date) {
		return format(date, "yyyyMMddHH");
	}
	
	
	

	public static int daysBetween(Date bdate) {
		try {
			return daysBetween(new Date(), bdate);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 计算两个日期之差
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time1 - time2) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static boolean isEqualDay(Date date1, Date date2) {
		int i = daysBetween(date1, date2);
		if (i == 0) {
			return true;
		}
		return false;
	}

}
