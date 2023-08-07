package com.shinhan.sunInCloud.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {
	public static String convertTimestampToString(Timestamp timestamp) {
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		return pattern.format(date);
	}
	
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
	
	public static String convertTimestampToDate(Timestamp timestamp) {
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
		
		return pattern.format(date);
	}
}
