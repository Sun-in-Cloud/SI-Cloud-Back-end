package com.shinhan.sunInCloud.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {
	/**
	 * Timestamp -> yyyy-MM-dd hh:mm (String)
	 * @param timestamp
	 * @return
	 */
	public static String convertTimestampToString(Timestamp timestamp) {
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		return pattern.format(date);
	}
	
	/**
	 * yyyy-MM-dd (String) -> Timestamp
	 * @param string
	 * @return
	 */
	public static Timestamp convertStringToDate(String string) {
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp time = null;
		try {
			Date date = pattern.parse(string);
			time = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	/**
	 * Timestamp -> yyyy-MM-dd (String)
	 * @param timestamp
	 * @return
	 */
	public static String convertTimestampToDate(Timestamp timestamp) {
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
		
		return pattern.format(date);
	}
	
	/**
	 * 현재시각 -> 패턴값
	 * @return
	 */
	public static String getPattern(String pattern) {
		Date date = new Date();
		SimpleDateFormat result = new SimpleDateFormat(pattern);
		
		return result.format(date);
	}
	
	/**
	 * 작년 연도 얻기
	 * @return
	 */
	public static int getLastYear() {
		Date date = new Date();
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy");
		
		return Integer.parseInt(pattern.format(date)) - 1;
	}
}
