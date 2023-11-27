package com.jonysham.appdesigner.editor.model;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public final class Attribute {
	public static final String TYPE_ID = "id";
	public static final String TYPE_ENUM = "enum";
	public static final String TYPE_FLAG = "flag";
	public static final String TYPE_VIEW = "view";
	public static final String TYPE_SIZE = "size";
	public static final String TYPE_COLOR = "color";
	public static final String TYPE_STRING = "string";
	public static final String TYPE_NUMBER = "number";
	public static final String TYPE_BOOLEAN = "boolean";
	public static final String TYPE_DIMENSION = "dimension";
	public static final String TYPE_DRAWABLE = "drawable";
	
	static final Map<String, String> types = new HashMap<>();
	static {
		types.put(View.Id, TYPE_ID);
		types.put(View.Width, TYPE_SIZE);
		types.put(View.Height, TYPE_SIZE);
		types.put(View.Padding, TYPE_DIMENSION);
		types.put(View.PaddingLeft, TYPE_DIMENSION);
		types.put(View.PaddingTop, TYPE_DIMENSION);
		types.put(View.PaddingRight, TYPE_DIMENSION);
		types.put(View.PaddingBottom, TYPE_DIMENSION);
		types.put(View.Background, TYPE_COLOR + "|" + TYPE_DRAWABLE);
		types.put(View.Foreground, TYPE_COLOR + "|" + TYPE_DRAWABLE);
		types.put(View.BackgroundTint, TYPE_COLOR);
		types.put(View.ForegroundTint, TYPE_COLOR);
		types.put(View.Alpha, TYPE_NUMBER);
		types.put(View.ScaleX, TYPE_NUMBER);
		types.put(View.ScaleY, TYPE_NUMBER);
		types.put(View.Rotation, TYPE_NUMBER);
		types.put(View.RotationX, TYPE_NUMBER);
		types.put(View.RotationY, TYPE_NUMBER);
		types.put(View.RotationZ, TYPE_NUMBER);
		types.put(View.Visibility, TYPE_BOOLEAN);
		
		types.put(LinearLayout.Orientation, TYPE_ENUM);
		types.put(LinearLayout.Gravity, TYPE_FLAG);
		types.put(LinearLayout.Weight, TYPE_NUMBER);
	}
	
	public static final class View {
		public static final String Id = "android:id";
		public static final String Width = "android:layout_width";
		public static final String Height = "android:layout_height";
		public static final String Padding = "android:padding";
		public static final String PaddingLeft = "android:paddingLeft";
		public static final String PaddingTop = "android:paddingTop";
		public static final String PaddingRight = "android:paddingRight";
		public static final String PaddingBottom = "android:paddingBottom";
		public static final String Background = "android:background";
		public static final String BackgroundTint = "android:backgroundTint";
		public static final String Foreground = "android:foreground";
		public static final String ForegroundTint = "android:foregroundTint";
		public static final String Alpha = "android:alpha";
		public static final String ScaleX = "android:scaleX";
		public static final String ScaleY = "android:scaleY";
		public static final String Rotation = "android:rotation";
		public static final String RotationX = "android:rotationX";
		public static final String RotationY = "android:rotationY";
		public static final String RotationZ = "android:rotationZ";
		public static final String Visibility = "android:visibility";
	}
	
	public static final class LinearLayout {
		public static final String Orientation = "android:orientation";
		public static final String Gravity = "android:gravity";
		public static final String Weight = "android:layout_weight";
	}
	
	public static String getType(String attributeName) {
		if (types.get(attributeName) == null) {
			throw new IllegalArgumentException("Not found type for attribute: " + attributeName);
		}
		
		return types.get(attributeName);
	}
	
	public static String[] splitMultiType(String type) {
		return type.split("|");
	}
}