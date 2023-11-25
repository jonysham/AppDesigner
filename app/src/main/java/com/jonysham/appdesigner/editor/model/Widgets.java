package com.jonysham.appdesigner.editor.model;

public class Widgets {
	
	public static final String LinearLayout = "LinearLayout";
	public static final String RelativeLayout = "RelativeLayout";
	public static final String FrameLayout = "FrameLayout";
	public static final String Button = "Button";
	
	public static String getClassName(String widgetName) {
		return "android.widget." + widgetName;
	}
}