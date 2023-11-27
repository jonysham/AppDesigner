package com.jonysham.appdesigner.editor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import com.jonysham.appdesigner.R;

public class ButtonItem extends Button implements BaseWidget {
	private Drawable strokeDrawable;
	
	public ButtonItem(Context context) {
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
	public Button getAsView() {
		return this;
	}
}