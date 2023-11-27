package com.jonysham.appdesigner.editor;

import android.content.Context;

import com.jonysham.appdesigner.editor.widget.BaseWidget;

import java.lang.reflect.Constructor;
import java.util.List;

public class WidgetFactory {
	private Context context;
	private BaseWidget widget;
	
	public WidgetFactory(Context context, String className) {
		this.context = context;
		widget = createWidget(className);
	}
	
	public BaseWidget create() {
		return widget;
	}
	
	private BaseWidget createWidget(String className) {
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