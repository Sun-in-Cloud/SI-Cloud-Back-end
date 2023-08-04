package com.shinhan.sunInCloud.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {
	public static String convertTimestampToString(Timestamp timestamp) {
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		return pattern.format(date);
	}
}
