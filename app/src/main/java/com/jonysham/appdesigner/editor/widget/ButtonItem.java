package com.jonysham.appdesigner.editor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import com.jonysham.appdesigner.R;
import com.jonysham.appdesigner.editor.WidgetBound;

public class ButtonItem extends Button implements BaseWidget {
	private WidgetBound bound;
	
	public ButtonItem(Context context) {
		super(context);
	}
	
	@Override
	public void setBound(WidgetBound bound) {
		this.bound = bound;
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		bound.draw(canvas, this);
	}
	
	@Override
	@NonNull
	public Button getAsView() {
		return this;
	}
}