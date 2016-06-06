package gov.jslt.taxevent.comm;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author yangxi
 * 
 */
public class DateUtil {
	public static final int TYPE_YEAR = 1;

	public static final int TYPE_MONTH = 2;

	public static final int TYPE_DAY = 3;

	public static final int TYPE_HOUR = 4;

	public static final int TYPE_MINUTE = 5;

	/**
	 * 定义常见的时间格式
	 */
	private static String[] dateFormat = { "yyyy-MM-dd HH:mm:ss",
			"yyyy/MM/dd HH:mm:ss", "yyyy年MM月dd日HH时mm分ss秒", "yyyy-MM-dd",
			"yyyy/MM/dd", "yy-MM-dd", "yy/MM/dd", "yyyy年MM月dd日", "HH:mm:ss",
			"yyyyMMddHHmmss", "yyyyMMdd", "yyyy.MM.dd", "yy.MM.dd" };
	/**
	 * 返回当前的日期时间的字符表示 默认使用 yyyy-MM-dd HH:mm:ss 格式
	 * 
	 * @return String 当前时间("yyyy-MM-dd HH:mm:ss" 格式)
	 */
	public static String getNowString() {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}

	/**
	 * 返回当前的日期时间的字符表示
	 * 
	 * @param format
	 *            日期格式
	 * @return String 当前时间
	 */
	public static String getNowString(String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}

	/**
	 * 返回当前的日期时间的 java.sql.Date 表示
	 * 
	 * @return java.sql.Date 当前时间
	 */
	public static java.sql.Date getNowSqlDate() {
		Date currentTime = new Date();
		return new java.sql.Date(currentTime.getTime());
	}

	/**
	 * 日期型变量由 String 转换成 java.util.Date
	 * 
	 * @param date
	 *            String 型变量("yyyy-MM-dd" 格式)
	 * @return java.util.Date 型变量
	 */
	public static Date parseStringToUtil(String date) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date dtResult = null;
		try {
			dtResult = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use
			// File | Settings | File Templates.
		}
		return dtResult;
	}

	/**
	 * 日期型变量由 String 转换成 java.util.Date
	 * 
	 * @param date
	 *            String 型变量
	 * @param format
	 *            日期格式
	 * @return java.util.Date 型变量
	 */
	public static Date parseStringToUtil(String date, String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);
		Date dtResult = null;
		try {
			dtResult = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use
			// File | Settings | File Templates.
		}
		return dtResult;
	}

	/**
	 * 日期型变量由 java.sql.Date 转换成 java.util.Date
	 * 
	 * @param date
	 *            java.util.Date 型变量
	 * @return java.sql.Date 型变量
	 */
	public static java.sql.Date parseUtilToSql(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/***************************************************************************
	 * 获取当前年份字符串
	 */
	public static String getYear() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = format.format(date);
		return year;
	}

	/***************************************************************************
	 * 获取当前月份字符串
	 */
	public static String getMonth() {
		SimpleDateFormat format = new SimpleDateFormat("MM");
		Date date = new Date();
		String month = format.format(date);
		return month;
	}

	/**
	 * 日期型变量由 java.util.Date 转换成 java.sql.Date
	 * 
	 * @param date
	 *            java.sql.Date 型变量
	 * @return java.util.Date 型变量
	 */
	public static Date parseSqlToUtil(java.sql.Date date) {
		return new Date(date.getTime());
	}

	/**
	 * 日期型变量由 java.util.Date 转换成 String 默认使用 yyyy-MM-dd HH:mm:ss 格式
	 * 
	 * @param date
	 *            java.util.Date 型变量
	 * @return String 型日期("yyyy-MM-dd HH:mm:ss" 格式)
	 */
	public static String parseUtilToString(Date date) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	/**
	 * 日期型变量由 java.util.Date 转换成 String
	 * 
	 * @param date
	 *            java.util.Date 型变量
	 * @param format
	 *            日期格式
	 * @return String 型日期
	 */
	public static String parseUtilToString(Date date, String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);
		return formatter.format(date);
	}

	/**
	 * 获取当月最大天数
	 * 
	 * @author yangxi
	 * @param date
	 *            java.util.Date 型变量
	 * @return String 天数
	 */
	public static String getMaxDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 日期型变量由 java.sql.Date 转换成 String 默认使用 yyyy-MM-dd HH:mm:ss 格式
	 * 
	 * @param date
	 *            java.sql.Date 型变量
	 * @return String 型日期("yyyy-MM-dd HH:mm:ss" 格式)
	 */
	public static String parseSqlToString(java.sql.Date date) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	/**
	 * 日期型变量由 java.sql.Date 转换成 String
	 * 
	 * @param date
	 *            java.sql.Date 型变量
	 * @param format
	 *            日期格式
	 * @return String 型日期
	 */
	public static String parseSqlToString(java.sql.Date date, String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);
		return formatter.format(date);
	}

	/**
	 * 数据库日期转为字符串类型 默认使用 yyyy-MM-dd HH:mm:ss 格式
	 * 
	 * @return String("yyyy-MM-dd HH:mm:ss" 格式)
	 */
	public static String timestampTOString(java.sql.Timestamp timestamp) {
		String rettime = "";
		if (timestamp != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			rettime = formatter.format(timestamp);
		}

		return rettime;
	}

	/**
	 * 数据库日期转为字符串类型
	 * 
	 * @param format
	 *            日期格式
	 * @return String("yyyy-MM-dd HH:mm:ss" 格式)
	 */
	public static String timestampTOString(java.sql.Timestamp timestamp,
			String format) {
		String rettime = "";
		if (timestamp != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					format);
			return formatter.format(timestamp);
		}
		return rettime;
	}

	/**
	 * 将日期格式从 java.util.Calendar 转到 java.sql.Timestamp 格式
	 * 
	 * @param date
	 *            java.util.Calendar 格式表示的日期
	 * @return java.sql.Timestamp 格式表示的日期
	 */
	public static java.sql.Timestamp convUtilCalendarToSqlTimestamp(
			java.util.Calendar date) {
		if (date == null)
			return null;
		else
			return new java.sql.Timestamp(date.getTimeInMillis());
	}

	/**
	 * 将日期格式从 java.util.Timestamp 转到 java.util.Calendar 格式
	 * 
	 * @param date
	 *            java.sql.Timestamp 格式表示的日期
	 * @return java.util.Calendar 格式表示的日期
	 */
	public static java.util.Calendar convSqlTimestampToUtilCalendar(
			java.sql.Timestamp date) {
		if (date == null)
			return null;
		else {
			java.util.GregorianCalendar gc = new java.util.GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return gc;
		}
	}

	/**
	 * 解析一个字符串，形成一个Calendar对象，适应各种不同的日期表示法
	 * 
	 * @param dateStr
	 *            期望解析的字符串，注意，不能传null进去，否则出错
	 * @return 返回解析后的Calendar对象 <br>
	 *         <br>
	 *         可输入的日期字串格式如下： <br>
	 *         "yyyy-MM-dd HH:mm:ss", <br>
	 *         "yyyy/MM/dd HH:mm:ss", <br>
	 *         "yyyy年MM月dd日HH时mm分ss秒", <br>
	 *         "yyyy-MM-dd", <br>
	 *         "yyyy/MM/dd", <br>
	 *         "yy-MM-dd", <br>
	 *         "yy/MM/dd", <br>
	 *         "yyyy年MM月dd日", <br>
	 *         "HH:mm:ss", <br>
	 *         "yyyyMMddHHmmss", <br>
	 *         "yyyyMMdd", <br>
	 *         "yyyy.MM.dd", <br>
	 *         "yy.MM.dd"
	 */
	public static Calendar parseDate(String dateStr) {
		if (dateStr == null || dateStr.trim().length() == 0)
			return null;

		Date result = parseDate(dateStr, 0);
		Calendar cal = Calendar.getInstance();
		cal.setTime(result);

		return cal;
	}

	/**
	 * 内部方法，根据某个索引中的日期格式解析日期
	 * 
	 * @param dateStr
	 *            期望解析的字符串
	 * @param index
	 *            日期格式的索引
	 * @return 返回解析结果
	 */
	public static Date parseDate(String dateStr, int index) {
		DateFormat df = null;
		try {
			df = new SimpleDateFormat(dateFormat[index]);

			return df.parse(dateStr);
		} catch (ParseException pe) {
			return parseDate(dateStr, index + 1);
		} catch (ArrayIndexOutOfBoundsException aioe) {
			return null;
		}
	}

	/**
	 * 字符串类型转为数据库日期类型
	 * 
	 * @return String("yyyy-MM-dd HH:mm:ss" 格式)
	 */
	public static java.sql.Timestamp strTOTimestamp(String str) {
		java.sql.Timestamp sdate = null;
		if (str != null) {
			try {
				sdate = convUtilCalendarToSqlTimestamp(parseDate(str));
			} catch (Exception e) {
			}
		}
		return sdate;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            String 型日期变量
	 * @param format
	 *            日期格式
	 * @return String 型日期
	 */
	public static String parseSimpleToChn(String date, String format) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				format);
		return formatter.format(parseStringToUtil(date));
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            String 型日期变量
	 * @return String 型日期
	 */
	public static String parseChnToSimple(String date) {
		date = date.replaceAll("年", "-");
		date = date.replaceAll("月", "-");
		date = date.replaceAll("日", " ");
		date = date.replaceAll("时", ":");
		date = date.replaceAll("分", ":");
		date = date.replaceAll("秒", "");
		return date.trim();
	}

	/**
	 * 通过字符串型的日期取得年份
	 * 
	 * @param date
	 *            字符串型日期
	 * @return 年份
	 */
	public static int getYear(String date) {
		int intIndex1 = date.indexOf("-");
		String strYear = date.substring(0, intIndex1);
		return Integer.parseInt(strYear, 10);
	}

	/**
	 * 通过字符串型的日期取得月份
	 * 
	 * @param date
	 *            字符串型日期
	 * @return 月份
	 */
	public static int getMonth(String date) {
		int intIndex1 = date.indexOf("-");
		int intIndex2 = date.indexOf("-", intIndex1 + 1);
		String strMonth = date.substring(intIndex1 + 1, intIndex2);
		return Integer.parseInt(strMonth, 10);
	}

	/**
	 * 通过字符串型的日期取得日期
	 * 
	 * @param date
	 *            字符串型日期
	 * @return 日期
	 */
	public static int getDay(String date) {
		int intIndex2 = date.lastIndexOf("-");
		String strDay = date.substring(intIndex2 + 1);
		return Integer.parseInt(strDay, 10);
	}

	/**
	 * 通过字符串型的日期取得季度
	 * 
	 * @param date
	 *            字符串型日期
	 * @return 季度
	 */
	public static int getQuarter(String date) {
		String strModel = "0111222333444";
		int intMonth = getMonth(date);
		return Integer.parseInt(String.valueOf(strModel.charAt(intMonth)), 10);
	}

	/**
	 * 计算两日期之间差值
	 * 
	 * @param type
	 *            比较类型
	 * @param date1
	 *            开始日期
	 * @param date2
	 *            截止
	 * @return 差值
	 */
	public static long getDateOffset(int type, Date date1, Date date2) {
		long lngDate1 = date1.getTime();
		long lngDate2 = date2.getTime();

		long MinMilli = 1000 * 60;
		long HrMilli = MinMilli * 60;
		long DyMilli = HrMilli * 24;

		long lngTemp, lngResult;
		switch (type) {
		case DateUtil.TYPE_MINUTE:
			lngTemp = lngDate2 - lngDate1;
			lngResult = lngTemp / MinMilli;
			break;
		case DateUtil.TYPE_HOUR:
			lngTemp = lngDate2 - lngDate1;
			lngResult = lngTemp / HrMilli;
			break;
		case DateUtil.TYPE_DAY:
			lngTemp = lngDate2 - lngDate1;
			lngResult = lngTemp / DyMilli;
			break;
		case DateUtil.TYPE_MONTH:
			lngTemp = getYear(parseUtilToString(date2))
					- getYear(parseUtilToString(date1));
			lngResult = lngTemp * 12;
			break;
		case DateUtil.TYPE_YEAR:
			lngResult = getYear(parseUtilToString(date2))
					- getYear(parseUtilToString(date1));
			break;
		default:
			lngResult = 0;
		}
		return lngResult;
	}

	/**
	 * 日期增减函数
	 * 
	 * @param type
	 *            比较类型
	 * @param date
	 *            原日期
	 * @param offset
	 *            差值
	 * @return 结果日期
	 */
	public static Date setDateOffset(int type, Date date, long offset) {
		long lngDate = date.getTime();

		long MinMilli = 1000 * 60;
		long HrMilli = MinMilli * 60;
		long DyMilli = HrMilli * 24;

		long lngTemp, lngResult;
		switch (type) {
		case DateUtil.TYPE_MINUTE:
			lngTemp = offset * MinMilli;
			lngResult = lngDate + lngTemp;
			break;
		case DateUtil.TYPE_HOUR:
			lngTemp = offset * HrMilli;
			lngResult = lngDate + lngTemp;
			break;
		case DateUtil.TYPE_DAY:
			lngTemp = offset * DyMilli;
			lngResult = lngDate + lngTemp;
			break;
		case DateUtil.TYPE_MONTH:
			lngResult = lngDate;
			break;
		case DateUtil.TYPE_YEAR:
			lngResult = lngDate;
			break;
		default:
			lngResult = lngDate;
		}
		return new Date(lngResult);
	}

	/**
	 * 取指定日期为星期几
	 * 
	 * @return 星期几
	 */
	public static long getWeek() {
		Date now = new Date();
		long lngDateTime = now.getTime();

		long MinMilli = 1000 * 60;
		long HrMilli = MinMilli * 60;
		long DyMilli = HrMilli * 24;

		// 取与1970-1-1日相差几天，1970-1-1日为星期四
		long lngDayFrom = lngDateTime / DyMilli;
		return (lngDayFrom % 7) + 4;
	}

	/**
	 * 取指定日期为星期几
	 * 
	 * @param date
	 *            指定日期
	 * @return 星期几
	 */
	public static long getWeek(Date date) {
		long lngDateTime = date.getTime();

		long MinMilli = 1000 * 60;
		long HrMilli = MinMilli * 60;
		long DyMilli = HrMilli * 24;

		// 取与1970-1-1日相差几天，1970-1-1日为星期四
		long lngDayFrom = lngDateTime / DyMilli;
		return (lngDayFrom % 7) + 4;
	}

	/**
	 * 根据起止时间和时间粒度获取两者之间的时间数组
	 * 
	 * @param beginday
	 *            开始日期
	 * @param endday
	 *            结束日期
	 * @param datetype
	 *            时间粒度：year,month,day
	 * @return 星期几
	 */
	public static ArrayList getDateOffList(String beginday, String endday,
			String datetype) {
		ArrayList dateoff = new ArrayList();
		long off = 0;
		if ("year".equals(datetype)) {
			off = DateUtil.getDateOffset(DateUtil.TYPE_YEAR, DateUtil
					.parseStringToUtil(beginday, "yyyy-MM-dd"), DateUtil
					.parseStringToUtil(endday, "yyyy-MM-dd"));
			for (long i = 0; i <= off; i++) {
				dateoff.add(String.valueOf(DateUtil.getYear(beginday) + i));
			}
		}
		if ("month".equals(datetype)) {
			off = DateUtil.getDateOffset(DateUtil.TYPE_MONTH, DateUtil
					.parseStringToUtil(beginday, "yyyy-MM-dd"), DateUtil
					.parseStringToUtil(endday, "yyyy-MM-dd"))
					+ (DateUtil.getMonth(endday) - DateUtil.getMonth(beginday));
			int year = DateUtil.getYear(beginday);
			int month = DateUtil.getMonth(beginday);
			dateoff.add(String.valueOf(year) + getDoubleDate(month));
			for (int i = 0; i < off; i++) {
				month = month + 1;
				if (month > 12) {
					year = year + 1;
					month = 1;
				}
				dateoff.add(String.valueOf(year) + getDoubleDate(month));
			}
		}
		if ("day".equals(datetype)) {
			off = DateUtil.getDateOffset(DateUtil.TYPE_DAY, DateUtil
					.parseStringToUtil(beginday, "yyyy-MM-dd"), DateUtil
					.parseStringToUtil(endday, "yyyy-MM-dd"));
			for (long i = 0; i <= off; i++) {
				dateoff.add(DateUtil.parseUtilToString(DateUtil.setDateOffset(
						DateUtil.TYPE_DAY, DateUtil.parseStringToUtil(beginday,
								"yyyy-MM-dd"), i), "yyyy-MM-dd"));
			}
		}
		return dateoff;
	}

	/**
	 * 月或日补零，如3补为03
	 */
	public static String getDoubleDate(int date) {
		String ret = "00";
		date = date + 100;
		ret = String.valueOf(date);
		ret = ret.substring(1, 3);
		return ret;
	}

	/**
	 * 获取昨天日期
	 */
	public static String getyestoday() {
		String retTime = DateUtil.parseUtilToString(DateUtil.setDateOffset(
				DateUtil.TYPE_DAY, DateUtil.parseStringToUtil(DateUtil
						.getNowString(), "yyyy-MM-dd"), -1), "yyyy-MM-dd");
		return retTime;
	}

	/**
	 * 获取昨天中文日期
	 */
	public static String getyestodaych() {
		String retTime = DateUtil.parseUtilToString(DateUtil.setDateOffset(
				DateUtil.TYPE_DAY, DateUtil.parseStringToUtil(DateUtil
						.getNowString(), "yyyy-MM-dd"), -1), "yyyy年MM月dd日");
		return retTime;
	}

	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	public static String getYearMonth(String indate) {
		String retDate = "";
		try {
			String[] datastrs = new String[] {};
			datastrs = indate.split("-");
			String monthstr = datastrs[1];
			retDate = datastrs[0] + getDoubleDate(Integer.parseInt(monthstr));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return retDate;
	}

	public static int compare_date(String DATE1, String DATE2) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				// System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				// System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static Calendar parseFormatDate(String dateStr, int index) {
		if ((dateStr == null) || (dateStr.trim().length() == 0)) {
			return null;
		}
		Date result = parseDate(dateStr, index);
		Calendar cal = Calendar.getInstance();
		cal.setTime(result);

		return cal;
	}

	public static String convSqlTimestampToString(Timestamp date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(date.getTime());

		return toDateStrByFormatIndex(gc, 3);
	}

	public static String toDateStrByFormatIndex(Calendar date, int formatIndex) {
		if (date == null)
			return null;
		return new SimpleDateFormat(dateFormat[formatIndex]).format(date
				.getTime());
	}

	/**
	 * 校验日期是否有效("yyyy-MM-dd")
	 * 
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 返回本月第一天 YYYY-MM-DD
	 * 
	 */
	public static String queryByDyt() {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		// c.add(Calendar.MONTH, 0);
		// c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 返回本月最后一天 YYYY-MM-DD
	 * 
	 */
	public static String queryByZhyt() {
		Calendar c = Calendar.getInstance();
		// c.set(Calendar.DAY_OF_MONTH,
		// c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.setTime(new Date());
		// c.roll(Calendar.DAY_OF_MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}
}
