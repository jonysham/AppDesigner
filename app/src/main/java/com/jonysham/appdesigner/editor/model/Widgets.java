package com.jonysham.appdesigner.editor.model;

public final class Widgets {
	public static final String LINEAR_LAYOUT = "LinearLayout";
	public static final String ABSOLUTE_LAYOUT = "AbsoluteLayout";
	public static final String RELATIVE_LAYOUT = "RelativeLayout";
	public static final String FRAME_LAYOUT = "FrameLayout";
	public static final String RADIO_GROUP = "RadioGroup";
	
	public static final String IMAGE_VIEW = "ImageView";
	public static final String BUTTON = "Button";
	public static final String IMAGE_BUTTON = "ImageButton";
	public static final String CHECK_BOX = "CheckBox";
	public static final String RADIO_BUTTON = "RadioButton";
	public static final String SWITCH = "Switch";
	public static final String PROGRESS_BAR = "ProgressBar";
	public static final String SEEK_BAR = "SeekBar";
	public static final String TEXT_VIEW = "TextView";
	public static final String EDIT_TEXT = "EditText";
	
	public static String getClazz(String widgetName) {
		return "com.jonysham.appdesigner.editor.widget." + widgetName + "Item";
	}
}