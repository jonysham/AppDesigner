package com.jonysham.appdesigner.editor;

import android.content.Context;

import com.jonysham.appdesigner.editor.widget.BaseWidget;

import java.lang.reflect.Constructor;

public class WidgetFactory {
	public static BaseWidget createWidget(String className, Context context) {
		try {
			Class clazz = Class.forName(className);
			Constructor con = clazz.getConstructor(Context.class);
			return (BaseWidget) con.newInstance(context);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		throw new IllegalArgumentException("Cannot find widget class: " + className);
	}
}