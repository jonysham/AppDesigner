package com.jonysham.appdesigner.editor;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.content.res.AppCompatResources;

import com.jonysham.appdesigner.editor.widget.BaseWidget;
import com.jonysham.appdesigner.editor.model.Widgets;
import com.jonysham.appdesigner.R;
import com.jonysham.appdesigner.editor.widget.LinearLayoutItem;
import com.jonysham.appdesigner.util.AndroidUtil;

public class LayoutEditor extends LinearLayout {
	private Context context;
	private Drawable strokeDrawable;
	private View shadow;
	
	private OnClickListener onClickListener = v -> {
		
	};
	
	private ViewGroup lastHost;
	private int lastIndex;
	
	private OnLongClickListener onLongClickListener = v -> {
		v.startDragAndDrop(null, new DragShadowBuilder(v), v, 0);
		lastHost = (ViewGroup) v.getParent();
		lastIndex = lastHost.indexOfChild(v);
		lastHost.removeView(v);
		return true;
	};
	
	private OnDragListener onDragListener = new OnDragListener() {
		
		@Override
		public boolean onDrag(View v, DragEvent event) {
			ViewGroup host = (ViewGroup) v;
			
			if ((host instanceof LayoutEditor || host instanceof ScrollView) && getChildCountWithoutShadow(host) >= 1) {
                removeView(shadow);
                return false;
            }
			
			switch (event.getAction()) {
				case DragEvent.ACTION_DROP: {
					host.removeView(shadow);
					
					if (host == LayoutEditor.this && host.getChildCount() > 1) {
                        return false;
                    }
					
					Object object = event.getLocalState();
					BaseWidget widget;
					
					if (object instanceof String) {
						widget = new WidgetFactory(context, Widgets.getClazz(object.toString())).create();
						widget.getAsView().setMinimumWidth(AndroidUtil.dp(20));
						widget.getAsView().setMinimumHeight(AndroidUtil.dp(20));
					} else {
						widget = (BaseWidget) object;
					}
					
					if (widget.getAsView().getParent() != null) {
                        ((ViewGroup) widget.getAsView().getParent()).removeView(widget.getAsView());
                    }
					
					addView(host, widget.getAsView(), event);
					
					widget.getAsView().setOnLongClickListener(onLongClickListener);
					widget.getAsView().setOnClickListener(onClickListener);
					
					if (widget.getAsView() instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) widget.getAsView();
						viewGroup.setOnDragListener(onDragListener);
						viewGroup.setPadding(50,50,50,50);
						
                        LayoutTransition layoutTransition = new LayoutTransition();
                        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
                        layoutTransition.disableTransitionType(LayoutTransition.DISAPPEARING);
                        layoutTransition.setDuration(180L);
                        viewGroup.setLayoutTransition(layoutTransition);
                        viewGroup.setAnimationCacheEnabled(true);
                    }
					
					break;
				}
				
				case DragEvent.ACTION_DRAG_ENDED: {
					if (!event.getResult()) {
						if (event.getLocalState() instanceof BaseWidget) {
							lastHost.addView(((BaseWidget) event.getLocalState()).getAsView(), lastIndex);
							return false;
						}
					}
				}
				
				case DragEvent.ACTION_DRAG_EXITED: {
					host.removeView(shadow);
					break;
				}
				
				case DragEvent.ACTION_DRAG_LOCATION: {
					if (host instanceof LayoutEditor && host.getChildCount() > 1) {
                        return false;
                    }
				}
				
				case DragEvent.ACTION_DRAG_ENTERED: {
					if (host instanceof LayoutEditor && host.getChildCount() > 1) {
                        return false;
                    }
					
					if (shadow.getParent() != null) {
                        int indexOfChild = host.indexOfChild(shadow);
                        int index;
						
                        if ((host instanceof LinearLayout) && ((LinearLayout) host).getOrientation() == LinearLayout.VERTICAL) {
                            index = getVerticalIndexForEvent(host, event);
                        } else if ((host instanceof LinearLayout) && ((LinearLayout) host).getOrientation() == LinearLayout.HORIZONTAL) {
                            index = getHorizontalIndexForEvent(host, event);
                        } else {
                            index = indexOfChild;
                        }

                        if (indexOfChild != index) {
                            host.removeView(shadow);
                            addView(host, shadow, event);
                        }
                    } else {
                        host.addView(shadow);
                    }
				}
			}
			
			return true;
		}
	};
	
	public LayoutEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		strokeDrawable = AppCompatResources.getDrawable(context, R.drawable.bg_stroke);
		shadow = new View(context);
		shadow.setLayoutParams(new ViewGroup.LayoutParams(AndroidUtil.dp(36), AndroidUtil.dp(24)));
		shadow.setBackgroundColor(0xFF424242);
		
		setOnDragListener(onDragListener);
	}
	
	private void addView(ViewGroup parent, View child, DragEvent event) {

        int index = parent.getChildCount();
        if (parent instanceof LinearLayout) {
            if (((LinearLayout) parent).getOrientation() == LinearLayout.VERTICAL) {
                index = getVerticalIndexForEvent(parent, event);
            } else {
                index = getHorizontalIndexForEvent(parent, event);
            }
        }
        parent.addView(child, index);

    }
	
	private int getHorizontalIndexForEvent(ViewGroup parent, DragEvent event) {
        int dropX = (int) event.getX();
        int index = 0;

        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);

            if (childView == shadow) {
                continue;
            }

            if (childView.getRight() < dropX) {
                index++;
            }
        }
        return index;
    }
	
	private int getVerticalIndexForEvent(ViewGroup parent, DragEvent event) {
        int dropY = (int) event.getY();
        int index = 0;

        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);

            if (childView == shadow) {
                continue;
            }

            if (childView.getTop() < dropY) {
                index++;
            }
        }

        return index;
    }
	
	public int getChildCountWithoutShadow(ViewGroup view) {
        int count = 0;
        for (int i = 0; i < view.getChildCount(); i++) {
            View child = view.getChildAt(i);
            if (child == shadow) {
                continue;
            }
            count++;
        }

        return count;
    }
}