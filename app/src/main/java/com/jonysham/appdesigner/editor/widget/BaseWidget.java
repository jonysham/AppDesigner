package com.jonysham.appdesigner.editor.widget;

import com.jonysham.appdesigner.editor.WidgetBound;
import java.util.ArrayList;
import java.util.List;

import android.view.View;

import androidx.annotation.NonNull;

public interface BaseWidget {
	
	void setBound(WidgetBound bound);
	
	default void setFocus(boolean focus) {}
	
	default boolean isFocus() {
		return false;
	}
	
	@NonNull
	View getAsView();
}