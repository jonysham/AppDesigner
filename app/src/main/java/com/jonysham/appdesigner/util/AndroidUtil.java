package com.jonysham.appdesigner.util;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

public class AndroidUtil {
	
	public static String getDate(long millis) {
		return new SimpleDateFormat("yyyy-mm-dd-HH-mm-ss").format(millis);
	}
	
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-mm-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
	}
}