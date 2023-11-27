package com.jonysham.appdesigner.editor;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;

import com.jonysham.appdesigner.editor.widget.BaseWidget;
import com.jonysham.appdesigner.util.AndroidUtil;

public class WidgetBound {
	private Paint paint;
	private int defColor;
	private int focusColor;
	private int defWidth;
	private int focusWidth;
	
	public WidgetBound(int defColor, int focusColor) {
		this.defColor = defColor;
		this.focusColor = focusColor;
		
		defWidth = AndroidUtil.dp(1);
		focusWidth = AndroidUtil.dp(2);
		
		paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setPathEffect(new DashPathEffect(new float[] {AndroidUtil.dp(4), AndroidUtil.dp(4)}, 0));
	}
	
	public void draw(Canvas canvas, BaseWidget widget) {
		int strokeWidth = widget.isFocus() ? focusWidth : defWidth;
		paint.setStrokeWidth(strokeWidth);
		paint.setColor(widget.isFocus() ? focusColor : defColor);
		canvas.drawRect(strokeWidth, strokeWidth, widget.getAsView().getWidth(), widget.getAsView().getHeight(), paint);
	}
}