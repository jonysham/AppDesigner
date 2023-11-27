package com.jonysham.appdesigner.editor;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.color.MaterialColors;
import com.google.android.material.snackbar.Snackbar;
import com.jonysham.appdesigner.editor.model.Widget;
import com.jonysham.appdesigner.editor.widget.BaseWidget;
import com.jonysham.appdesigner.util.AndroidUtil;
import com.jonysham.appdesigner.R;

public class LayoutEditor extends LinearLayout {
	private Context context;
	private View shadow;
	
	private OnClickListener onClickListener = v -> {
		
	};
	
	private OnLongClickListener onLongClickListener = v -> {
		v.startDragAndDrop(null, new DragShadowBuilder(v), v, 0);
		((ViewGroup) v.getParent()).removeView(v);
		
		return true;
	};
	
	private OnDragListener onDragListener = new OnDragListener() {
		
		@Override
		public boolean onDrag(View v, DragEvent event) {
			ViewGroup host = (ViewGroup) v;
			
			switch (event.getAction()) {
				case DragEvent.ACTION_DROP: {
					host.removeView(shadow);
					
					Object object = event.getLocalState();
					BaseWidget widget;
					
					if (object instanceof Widget) {
						widget = WidgetFactory.createWidget(((Widget) event.getLocalState()).getClazz(), context);
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
					widget.setBound(new WidgetBound(defaultBoundColor, focusBoundColor));
					
					if (widget.getAsView() instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) widget.getAsView();
						viewGroup.setOnDragListener(onDragListener);
						viewGroup.setPadding(50,50,50,50);
						
                        LayoutTransition layoutTransition = new LayoutTransition();
                        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
                        layoutTransition.disableTransitionType(LayoutTransition.DISAPPEARING);
                        layoutTransition.setDuration(180L);
                        viewGroup.setLayoutTransition(layoutTransition);
                    }
					
					break;
				}
				
				case DragEvent.ACTION_DRAG_ENDED:
				case DragEvent.ACTION_DRAG_EXITED: {
					host.removeView(shadow);
					
					if (host instanceof BaseWidget) {
						((BaseWidget) host).setFocus(false);
					}
					break;
				}
				
				case DragEvent.ACTION_DRAG_LOCATION:
				case DragEvent.ACTION_DRAG_ENTERED: {
					if (host instanceof LayoutEditor && host.getChildCount() > 1) {
                        return false;
                    }
					
					if (host instanceof BaseWidget) {
						((BaseWidget) host).setFocus(true);
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
	
	private int defaultBoundColor;
	private int focusBoundColor;
	
	public LayoutEditor(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		
		shadow = new View(context);
		shadow.setLayoutParams(new ViewGroup.LayoutParams(AndroidUtil.dp(36), AndroidUtil.dp(24)));
		shadow.setBackground(AppCompatResources.getDrawable(context, R.drawable.bg_drag_shadow));
		
		defaultBoundColor = MaterialColors.getColor(this, com.google.android.material.R.attr.colorOnSurface);
		focusBoundColor = MaterialColors.getColor(this, com.google.android.material.R.attr.colorPrimary);
		
		setOnDragListener(onDragListener);
		setWillNotDraw(false);
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
		
		if (!containsView(child, parent)) {
			String parentName = parent instanceof LayoutEditor ? "Main layout" : parent.getClass().getSuperclass().getSimpleName();
			Snackbar.make(this, "You cannot add more than one widget to " + parentName + "!", Snackbar.LENGTH_SHORT)
			.show();
		}
    }
	
	private boolean containsView(View view, ViewGroup group) {
		for (int i=0; i<group.getChildCount(); i++) {
			if (group.getChildAt(i) == view) {
				return true;
			}
		}
		
		return false;
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
	
	@Override
	public void addView(View v) {
		if (getChildCount() == 0) {
			super.addView(v);
		}
	}
	
	@Override
	public void addView(View v, int index) {
		if (getChildCount() == 0) {
			super.addView(v, index);
		}
	}
}