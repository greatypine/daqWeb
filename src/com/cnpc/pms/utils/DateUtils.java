package com.cnpc.pms.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日期工具类（如有新需求请自行添加）
 * 
 * Copyright(c) 2014 Yadea Technology Group , http://www.yadea.com.cn
 * 
 * @author IBM
 * @since 2013-4-24
 */
public class DateUtils {

	/** 日期格式化为yyyy-MM-dd格式 */
	private static final String NORMAL_FORMAT = "yyyy-MM-dd";

	/**
	 * 将Util的Date类型转换为String类型
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(NORMAL_FORMAT);
		return format.format(date);
	}

	/**
	 * 将Util的Date类型转换为String类型
	 *
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date, String date_format) {
		SimpleDateFormat format = new SimpleDateFormat(date_format);
		return format.format(date);
	}

	/**
	 * 将Util的String类型转换为Date类型
	 * 
	 * @param str_date
	 *            日期字符串
	 * @return 日期对象
	 */
	public static Date str_to_date(String str_date, String date_format) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(date_format);
		return format.parse(str_date);
	}

	/**
	 * Long类型毫秒转换Date类型
	 * 
	 * @param dateFormat
	 *            转换的日期格式
	 * @param millSec
	 *            毫秒
	 * @return 字符串日期
	 */
	public static String transferLongToDate(String dateFormat, Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}

	/**
	 * 取得 月份的第一天是周几
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static String getFirstDayWeek(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		int i = cal.get(Calendar.DAY_OF_WEEK);
		String[] str = { "", "日", "一", "二", "三", "四", "五", "六" };
		return str[i];
	}

	/**
	 * 判断月有几天 参数YYYY-MM
	 * 
	 * @param month_work
	 * @return
	 */
	public static int getDaysByMonths(String month_work) {
		int year = Integer.parseInt(month_work.split("-")[0]);
		int month = Integer.parseInt(month_work.split("-")[1]);
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 2:
			return isLeapYear(year) ? 29 : 28;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		default:
			return 31;
		}
	}

	public static boolean isLeapYear(int year) {
		if (year % 100 == 0) {
			if (year % 400 == 0) {
				return true;
			}
		} else {
			if (year % 4 == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param date
	 *            当前日期
	 * @return 返回当前日期上月的月初日期
	 */
	public static String getPreMonthFirstDay(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		Date theDate = (Date) calendar.getTime();

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first);
		day_first = str.toString();

		return day_first;
	}

	/**
	 * 比较两日期大小
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.after(dt2)) {
				return 1;
			} else if (dt1.before(dt2)) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	// 获得本周一与当前日期相差的天数
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return -6;
		} else {
			return 2 - dayOfWeek;
		}
	}

	// 获得当前周- 周一的日期
	public static String getCurrentMonday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得当前周- 周日 的日期
	public static String getPreviousSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 取得当前月
	public static String getCurrMonthDate() {
		GregorianCalendar currentDate = new GregorianCalendar();
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		String curMonth = df.format(monday);
		return curMonth;
	}

	/**
	 * 返回当前时间的月初日期
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getCurrMonthFirstDate(String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Date theDate = (Date) calendar.getTime();

		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first);
		day_first = str.toString();

		return day_first;
	}
	/**
	 * 返回当前时间的月末日期
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getCurrMonthLastDate(String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String day_last = df.format(cale.getTime());
        StringBuffer str = new StringBuffer().append(day_last);
        day_last = str.toString();
        
		return day_last;
	}
	public static String getNextMonthDate(String targetDate, String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		try {
			Date date = df.parse(targetDate);// 初始日期
			calendar.setTime(date);// 设置日历时间
			calendar.add(Calendar.MONTH, 1);// 在日历的月份上增加1个月
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(calendar.getTime());
	}

	public static String getNextDate(String targetDate, String dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();
		try {
			Date date = df.parse(targetDate);// 初始日期
			calendar.setTime(date);// 设置日历时间
			calendar.add(Calendar.DATE, 1);// 在日历的月份上增加1天
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.format(calendar.getTime());
	}
	/**
	 * 返回上个月的月初日期
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getLastMonthFirstDate(String dateFormat) {
		SimpleDateFormat format=new SimpleDateFormat(dateFormat);
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(calendar.getTime());
	}
	/**
	 * 返回上个月的月末日期
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getLastMonthLastDate(String dateFormat) {
		SimpleDateFormat sf=new SimpleDateFormat(dateFormat);
		Calendar calendar=Calendar.getInstance();
		int month=calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return sf.format(calendar.getTime());
	}
	// 获得昨天的日期
	public static String lastDate() {
		Calendar calendar = new GregorianCalendar();
		Date date = new Date();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	// 获得当天的日期
	public static String curDate() {
		Calendar calendar = new GregorianCalendar();
		Date date = new Date();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 0);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	// 获得当前日期前几天的日期(不包含当天)
	public static String getBeforeDate(String dateStr, int count) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String maxDateStr = dateStr;
		String minDateStr = "";
		Calendar calc = Calendar.getInstance();
		try {
			calc.setTime(sdf.parse(maxDateStr));
			calc.add(calc.DATE, count);
			Date minDate = calc.getTime();
			minDateStr = sdf.format(minDate);
			return minDateStr;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	// 获得当前月的前一个月
	public static String getBeforeMonth(String dateStr, int count) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String maxDateStr = dateStr;
		String minDateStr = "";
		Calendar calc = Calendar.getInstance();
		try {
			calc.setTime(sdf.parse(maxDateStr));
			calc.add(calc.MONTH, count);
			Date minDate = calc.getTime();
			minDateStr = sdf.format(minDate);
			return minDateStr;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @author sunning 获取当前时间前6周的日期书包含当前时间所在周从周日开始
	 * @return
	 */
	public static Map<Integer, String> getDateBeforesixWeek() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		Date date = c.getTime();
		Date date2 = getDateBeforeOneDate(date);
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 0; i < 6; i++) {
			Date weekBefore = getWeekBefore(i, date2);
			int weekOfYear = getWeekOfYear(weekBefore);
			map.put(weekOfYear, dateFormat.format(weekBefore));
		}
		return map;
	}

	/**
	 * 
	 * @author sunning 获取当前时间前6周的日期书包含当前时间所在周从周日开始
	 * @return
	 */
	public static List<Map<String, Object>> getDateBeforesix() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		Date date = c.getTime();
		Date date2 = getDateBeforeOneDate(date);
		for (int i = 0; i < 6; i++) {
			Date weekBefore = getWeekBefore(i, date2);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("date", dateFormat.format(weekBefore));
			int weekOfYear = getWeekOfYear(weekBefore);
			map.put("week", weekOfYear);
			list.add(map);
		}

		return list;
	}

	/**
	 * @author sunning 获取当前时间所在年的周数
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取当年的周，从周日开始
	 * @return
	 */
	public static List<String> getDateByWeek(){
		int weeks = getWeekOfYear(new Date());
		List<String> list = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		Date date = c.getTime();
		Date date2 = getDateBeforeOneDate(date);
		for (int i = 0; i < weeks; i++) {
			Date weekBefore = getWeekBefore(i, date2);
			list.add(dateFormat.format(weekBefore));
		}

		Collections.sort(list);
		
		return list;  
	}
	/**
	 * 获取当年的周，从周日开始
	 * @return
	 */
	public static List<String> getMemDateByWeek(){
		String today = "2018-05-17";  
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		Date date1 = null;  
		try {  
		    date1 = format.parse(today);  
		} catch (ParseException e) {  
		    e.printStackTrace();  
		}  
		  
		Calendar calendar = Calendar.getInstance();  
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		calendar.setFirstDayOfWeek(Calendar.MONDAY);  
		calendar.setTime(date1);  
		int begin = calendar.get(Calendar.WEEK_OF_YEAR)-1;
		int weeks = getWeekOfYear(new Date());
        if("2018".equals(year)) {
        	weeks = weeks - begin;
        }
		List<String> list = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		Date date = c.getTime();
		Date date2 = getDateBeforeOneDate(date);
		for (int i = 0; i < weeks; i++) {
			Date weekBefore = getWeekBefore(i, date2);
			list.add(dateFormat.format(weekBefore));
		}
		
		Collections.sort(list);
		
		return list;  
	}
	
	public static Date getWeekBefore(int n, Date date) {
		Calendar c = Calendar.getInstance();

		// 过去七天
		c.setTime(date);
		c.add(Calendar.DATE, -7 * n);
		Date d = c.getTime();
		return d;
	}

	/**
	 * 获取指定日期的前一天
	 * 
	 * @author sunning
	 * @param date
	 * @return
	 */
	public static Date getDateBeforeOneDate(Date date) {
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(date);// 把当前时间赋给日历
		calendar.add(Calendar.DATE, -1); // 设置为前一天
		return calendar.getTime(); // 得到前一天的时间
	}
	/**
	 * 获得上个月最大的日(31/30/29/28)
	 * @return
	 */
	public static int getLastMonthMaxDay(){
		// 获取上月最后一天：
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 下面可以设置月份，注：月份设置要减1，所以设置1月就是1-1，设置2月就是2-1，如此类推
		cal.set(Calendar.MONTH, 1 - 1);
		// 调到上个月
		cal.add(Calendar.MONTH, -1);
		// 得到一个月最最后一天日期(31/30/29/28)
		int MaxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return MaxDay;
	}
	public static String getLastMaxDate(){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		return sf.format(calendar.getTime());
	}
	/**
	 * 获得上个月当天日期
	 * @return
	 */
	public static String getLastMonthTodayDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date); // 设置为当前时间
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); // 设置为上一个月
        return sdf.format(cal.getTime());
	}

	/**
	 * 获取当年所有的天
	 * @return
	 */
	public static List<String> getDateByDay(){
		Calendar c = new GregorianCalendar();
		int days = c.get(Calendar.DAY_OF_YEAR);
		List<String> list = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		c.setTime(new Date());
		Date date = c.getTime();
		for (int i = 0; i < days; i++) {
			String dayBefore = getBeforeDate(dateFormat.format(date),-(i));
			list.add(dayBefore);
		}

		Collections.sort(list);

		return list;
	}

	public static void main(String[] args) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Calendar c = new GregorianCalendar();
//		c.setFirstDayOfWeek(Calendar.MONDAY);
//		c.setTime(new Date());
//		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
//		Date date = c.getTime();
//		String format = dateFormat.format(date);
//		System.out.println(format);
//		int weekOfYear = getWeekOfYear(date);
//		System.out.println(weekOfYear);
//		Date date2 = getDateBeforeOneDate(date);
//		System.out.println(dateFormat.format(date2));
//		int weekOfYear2 = getWeekOfYear(date);
//		System.out.println(weekOfYear2);
		System.out.println(getLastMonthLastDate("yyyy-MM-dd"));
		 List<String> list = getDateByDay();
		 System.out.println(list.toString());
	}

}
