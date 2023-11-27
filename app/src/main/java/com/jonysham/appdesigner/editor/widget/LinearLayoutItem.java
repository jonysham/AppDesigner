package com.jonysham.appdesigner.editor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;

import androidx.appcompat.content.res.AppCompatResources;
import com.jonysham.appdesigner.R;

public class LinearLayoutItem extends LinearLayout implements BaseWidget {
	private Drawable strokeDrawable;
	
	public LinearLayoutItem(Context context) {
		super(context);
		
		strokeDrawable = AppCompatResources.getDrawable(context, R.drawable.bg_stroke);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		strokeDrawable.setBounds(0, 0, getWidth(), getHeight());
		strokeDrawable.draw(canvas);
	}
	
	@Override
	@NonNull
	public LinearLayout getAsView() {
		return this;
	}
}