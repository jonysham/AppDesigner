package com.jonysham.appdesigner.editor.model;

public class Widget {
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
	
	private String name;
	
	public Widget(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getClazz() {
		return "com.jonysham.appdesigner.editor.widget." + name + "Item";
	}
}