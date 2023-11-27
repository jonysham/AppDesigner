package com.jonysham.appdesigner.editor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;

import androidx.appcompat.content.res.AppCompatResources;
import com.jonysham.appdesigner.R;
import com.jonysham.appdesigner.editor.WidgetBound;

public class LinearLayoutItem extends LinearLayout implements BaseWidget {
	private WidgetBound bound;
	private boolean isFocus;
	
	public LinearLayoutItem(Context context) {
		super(context);
	}
	
	@Override
	public void setBound(WidgetBound bound) {
		this.bound = bound;
	}
	
	@Override
	public void setFocus(boolean focus) {
		isFocus = focus;
	}
	
	@Override
	public boolean isFocus() {
		return isFocus;
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		bound.draw(canvas, this);
	}
	
	@Override
	@NonNull
	public LinearLayout getAsView() {
		return this;
	}
}