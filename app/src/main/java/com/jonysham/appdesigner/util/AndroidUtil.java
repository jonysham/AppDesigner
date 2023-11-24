package com.jonysham.appdesigner.util;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;

public class AndroidUtil {
	
	public static String getDate(long millis) {
		return new SimpleDateFormat("yyyy-mm-dd-HH-mm-ss").format(millis);
	}
	
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-mm-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
	}
	
	public static void openUrl(String url, Context context) {
		try {
            Intent open = new Intent(Intent.ACTION_VIEW);
            open.setData(Uri.parse(url));
            open.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(open);
        } catch (Throwable th) {
            th.printStackTrace();
        }
	}
}