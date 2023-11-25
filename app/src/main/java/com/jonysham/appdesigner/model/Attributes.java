package com.jonysham.appdesigner.model;

import java.util.HashMap;
import java.util.Map;

public class Attributes {

	public static final int TYPE_SIZE = 0;
	public static final int TYPE_DIMENSION = 1;
	public static final int TYPE_NUMBER = 2;
	public static final int TYPE_COLOR = 3;
	public static final int TYPE_ENUM = 4;
	public static final int TYPE_FLAG = 5;
	public static final int TYPE_BOOLEAN = 6;
	
	public static final Map<String, Integer> types = new HashMap<>();
	static {
		types.put(View.Width, TYPE_SIZE);
		types.put(View.Height, TYPE_SIZE);
		types.put(View.Padding, TYPE_DIMENSION);
		types.put(View.PaddingLeft, TYPE_DIMENSION);
		types.put(View.PaddingTop, TYPE_DIMENSION);
		types.put(View.PaddingRight, TYPE_DIMENSION);
		types.put(View.PaddingBottom, TYPE_DIMENSION);
		types.put(View.Background, TYPE_COLOR);
		types.put(View.Foreground, TYPE_COLOR);
		types.put(View.Elevation, TYPE_DIMENSION);
		types.put(View.Alpha, TYPE_NUMBER);
		types.put(View.ScaleX, TYPE_NUMBER);
		types.put(View.ScaleY, TYPE_NUMBER);
		types.put(View.Rotation, TYPE_NUMBER);
		types.put(View.RotationX, TYPE_NUMBER);
		types.put(View.RotationY, TYPE_NUMBER);
		types.put(View.RotationZ, TYPE_NUMBER);
		types.put(View.Visibility, TYPE_ENUM);
	}
	
    public static final class View {
        public static final String Width = "android:layout_width";
        public static final String Height ="android:layout_height";
        public static final String Padding = "android:padding";
        public static final String PaddingLeft = "android:paddingLeft";
        public static final String PaddingTop = "android:paddingTop";
        public static final String PaddingRight = "android:paddingRight";
        public static final String PaddingBottom = "android:paddingBottom";
        public static final String Background = "android:background";
        public static final String Foreground = "android:foreground";
		public static final String Elevation = "android:elevation";
		public static final String Alpha = "android:alpha";
		public static final String ScaleX = "android:scaleX";
		public static final String ScaleY = "android:scaleY";
		public static final String Rotation = "android:rotation";
		public static final String RotationX = "android:rotationX";
		public static final String RotationY = "android:rotationY";
		public static final String RotationZ = "android:rotationZ";
		public static final String Visibility = "android:visibility";
    }
}
