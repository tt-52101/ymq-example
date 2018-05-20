package io.ymq.example;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright © 2010 Higinet Tech.All Rights Reserved
 * 
 * @author xuyanhua xuyh@higinet.com.cn
 * @date 2011-4-26
 * @description 系统工具类
 */
public class SysUtil {
	/**
	 * 根据月份得到相应季度
	 * 
	 * @param month
	 * @return
	 */
	public static int curSeason(int month) {
		int season = 0;
		if (month % 3 != 0) {
			season = month / 3 + 1;
		} else {
			season = month / 3;
		}
		return season;
	}
	
	/**
	 * 以'-'为分隔符的日期字符串转为时间戳
	 * @param date 日期字符串
	 * @return
	 */
	public static Timestamp parseToTimestamp(String date) {
		Timestamp ts = new Timestamp(new Date().getTime());
		try {
			if (date != null && !(date.trim().equals(""))) {
				String format ="";
				int len = date.length();
				if(len <=4 ){
					format = "yyyy";
				}else if(len <=7 && len > 4){
					format = "yyyy-MM";
				}else if(len <= 10 && len > 7){
					format = "yyyy-MM-dd";
				}else{
					format = "yyyy-MM-dd HH:mm:ss";
				}
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				Date d = sdf.parse(date);
				ts = new Timestamp(d.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}
	/**
	 * 以'-'为分隔符的日期字符串转为时间戳
	 * @param date 日期字符串
	 * @return
	 */
	public static Date parseToDate(String date) {
		Date d = new Date(System.currentTimeMillis());
		try {
			if (date != null && !(date.trim().equals(""))) {
				String format ="";
				int len = date.length();
				if(len <=4 ){
					format = "yyyy";
				}else if(len <=7 && len > 4){
					format = "yyyy-MM";
				}else if(len <= 10 && len > 7){
					format = "yyyy-MM-dd";
				}else{
					format = "yyyy-MM-dd HH:mm:ss";
				}
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				d = sdf.parse(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	/**
	 * 下一天
	 * (此处有问题,即在跨月的时候减一天出现问题,如取5月31号下一天是6月1号,6月1号转为数字20110601,-1后为20110600)
	 * @param today
	 * @return
	 */
	@Deprecated
	public static Timestamp nextDay(Timestamp today) {
		Timestamp t = new Timestamp(today.getTime() + 1000 * 60 * 60 * 24);
		return t;
	}
	
	/**
	 * 前一天
	 * 
	 * @param today
	 * @return
	 */
	public static Timestamp beforeDay(Timestamp today) {
		Timestamp t = new Timestamp(today.getTime() - 1000 * 60 * 60 * 24);
		return t;
	}
	
	/**
	 * 获得今天的最后时刻
	 * @param today
	 * @return
	 */
	public static Timestamp getTodayLastTime(Date today){
		return new Timestamp(today.getTime() + 24*60*60*1000 - 1);
	}

	/**
	 * 获取时间date当年的最后时刻
	 * @param date
	 * @return
	 */
	public static Timestamp getTheYearLastTime(Date date){
		String nextYear = (Integer.parseInt(SysUtil.dateConvert(date, "yyyy"))+1)+"";
		Date nextYearDate = SysUtil.parseToTimestamp(SysUtil.dateConvert(nextYear, "yyyy", "yyyy-MM-dd"));
		return new Timestamp(nextYearDate.getTime() - 1);
	}
	/**
	 * 获取时间date当月的最后时刻
	 * @param date
	 * @return
	 */
	public static Timestamp getTheMonthLastTime(Date date){
		String dateStr = SysUtil.dateConvert(date, "yyyy-MM");
		int year = Integer.parseInt(dateStr.substring(0, 4));
		int month = Integer.parseInt(dateStr.substring(5, 7));
		
		if ( month==10  ||  month==11 || month==12){
			month = Integer.parseInt(dateStr.substring(5, 7));
		}else{
			month = Integer.parseInt(dateStr.substring(6, 7));
		}
		
		String nextMonth = "";
		if( month <12){
			nextMonth = year+"-"+(month+1);
		}else{
			nextMonth = (year+1)+"-"+1;
		}
		Date nextMonthDate = SysUtil.parseToTimestamp(nextMonth);
		return new Timestamp(nextMonthDate.getTime() - 1);
	}
	/**
	 * 获取时间date当日的最后时刻
	 * @param date
	 * @return
	 */
	public static Timestamp getTheDayLastTime(Date date){
		Date theDay = SysUtil.parseToTimestamp(SysUtil.dateConvert(date, "yyyy-MM-dd"));
		Date nextDayDate = new Date(theDay.getTime()+1000*60*60*24);
		return new Timestamp(nextDayDate.getTime() - 1);
	}
	
	/**
	 * 把一种格式的日期转为另一种格式
	 * 
	 * @param date
	 * @param pattern1
	 * @param pattern2
	 * @return
	 */
	public static String dateConvert(String date, String pattern1, String pattern2) {
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1);
			SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2);
			return sdf2.format(sdf1.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 把一种格式的日期转为另一种格式
	 * @param date 日期
	 * @param pattern 指定格式
	 * @return
	 */
	public static String dateConvert(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	/**
	 * 根据今天得到昨天
	 * @param today 指定日期
	 * @return
	 */
	@Deprecated
	public static Timestamp yesterday(Date today){
		return new Timestamp(today.getTime()-24*60*60*1000);
	}
	
	/**
	 * 当前时间
	 * @return
	 */
	public static String current(String dataFormatStr){
		if(dataFormatStr == null || "".equals(dataFormatStr)){
			return current();
		}
		return SysUtil.dateConvert(new Date(System.currentTimeMillis()), dataFormatStr);
	}
	/**
	 * 当前时间
	 * @return
	 * 		[yyyy-MM-dd HH:mm:ss]
	 */
	public static String current(){
		return SysUtil.dateConvert(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String randomDate(){
		int year = (int)(Math.random() * 100 % 6 + 2010);//[2010,2015]
		int month = (int)(Math.random() * 1000 % 12 +1);//[1,12]
		int day = (int)(Math.random() * 1000 % 25 +1);//[1,12]
		int hms = (int)(Math.random() * 100000000 % 566400 +1);//[1,566400]
		Date date = new Date(SysUtil.parseToTimestamp(year+"-"+ (month < 10 ? ("0"+month) : month)+"-"+(day < 10 ? ("0"+day) : day)).getTime()+hms);
		return SysUtil.dateConvert(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static void main(String[] args){
		Date now = new Date();
		System.out.println(SysUtil.getTheMonthLastTime(now));
	}
}
