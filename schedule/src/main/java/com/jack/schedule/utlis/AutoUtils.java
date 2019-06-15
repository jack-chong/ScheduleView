package com.jack.schedule.utlis;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * time    : 2017-04-14 11:01
 * desc    : 屏幕适配
 * versions: 1.0
 */
public class AutoUtils {

	private static int sScreenWidth;
	private static int sScreenHeight;

	private static float sAutoScaleX;
	private static float sAutoScaleY;

	private static double sTextAutoScale;

	public static void setSize(Context context, boolean hasStatusBar, int UIWidth, int UIHeight){
		if(context == null || UIWidth < 1 || UIHeight < 1)return;

		DisplayMetrics screenSize = WindowUtils.getScreenSize(context);
		int width = screenSize.widthPixels;
		int height = screenSize.heightPixels;

		if (hasStatusBar) {
			height -= WindowUtils.getStatusHeight(context);
		}

		AutoUtils.sScreenWidth = width;
		AutoUtils.sScreenHeight = height;

		sAutoScaleX = sAutoScaleY = sScreenWidth * 1.0f / UIWidth;
//		sAutoScaleY = sScreenHeight * 1.0f / UIHeight;

		AutoUtils.sTextAutoScale = sAutoScaleX;
	}
    public static void auto(Activity act){
    	if(act == null || sScreenWidth < 1 || sScreenHeight < 1)return;
    	View view = act.getWindow().getDecorView();
    	auto(view);
    }

	public static void auto(Dialog dialog){
		if(dialog == null || sScreenWidth < 1 || sScreenHeight < 1)return;
		View view = dialog.getWindow().getDecorView();
		auto(view);
	}

	public static void auto(PopupWindow popupWindow){
		if(popupWindow == null || sScreenWidth < 1 || sScreenHeight < 1)return;
		View view = popupWindow.getContentView();
		auto(view);
	}



	public static void auto(View view){
		auto(view, null);
	}


	public static void auto(View view, OnSizeChangeListener listener){
		if(view == null || sScreenWidth < 1 || sScreenHeight < 1)return;
		if (sAutoScaleX == 1 && sAutoScaleY == 1) return;

		if (listener != null && !listener.onPrepareChange(view, view.getId())){
			return;
		}

		autoTextSize(view);
		autoSize(view);
		autoPadding(view);
		autoMargin(view);
//		autoShape(view, minScale);

		if(view instanceof ViewGroup){
			auto((ViewGroup)view, listener);
		}
	}



	public interface OnSizeChangeListener{
		boolean onPrepareChange(View view, @IdRes int id);
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	private static void autoShape(View view, boolean autoScale) {

		if (view.getBackground() instanceof GradientDrawable) {
			GradientDrawable background = (GradientDrawable) view.getBackground();
			float cornerRadius = background.getCornerRadius();
			try{
				float[] cornerRadii = background.getCornerRadii();
				for (int i = 0; i < cornerRadii.length; i++) cornerRadii[i] = i % 2 == 0 ? getScaleWidth((int) cornerRadii[i]) : getScaleHeight((int) cornerRadii[i]);
				background.setCornerRadii(cornerRadii);
			}catch (Exception e){

			}
			background.setCornerRadius((getScaleWidth((int) cornerRadius) + getScaleHeight((int) cornerRadius)) / 2);
			view.setBackground(background);
		}
	}

	private static void auto(ViewGroup viewGroup, OnSizeChangeListener listener){
    	int count = viewGroup.getChildCount();

		for (int i = 0; i < count; i++) {

			View child = viewGroup.getChildAt(i);

			if(child != null){
				auto(child, listener);
			}
		}
    }

    public static void autoMargin(View view){
        if (!(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams))
            return;

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if(lp == null) return;

        lp.leftMargin = getScaleWidth(lp.leftMargin);
        lp.topMargin = getScaleHeight(lp.topMargin);
        lp.rightMargin = getScaleWidth(lp.rightMargin);
        lp.bottomMargin = getScaleHeight(lp.bottomMargin);

    }

    public static void autoPadding(View view){
        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();

        l = getScaleWidth(l);
        t = getScaleHeight(t);
        r = getScaleWidth(r);
        b = getScaleHeight(b);

        view.setPadding(l, t, r, b);

		if (view instanceof LinearLayout) {
			((LinearLayout) view).setDividerPadding(getScaleWidth(((LinearLayout) view).getDividerPadding()));
		}
    }

	public static void autoSize(View view){
        ViewGroup.LayoutParams lp = view.getLayoutParams();

        if (lp == null) return;

		int width = lp.width;
		int height = lp.height;
		if(lp.width > 0){
        	lp.width = getScaleWidth(lp.width);
        }

        if(lp.height > 0){
        	lp.height = getScaleHeight(lp.height);
        }

		int minWidth = view.getMinimumWidth();
		int minHeight = view.getMinimumHeight();

		if (minWidth != 0)view.setMinimumWidth(getScaleWidth(minWidth));
		if (minHeight != 0)view.setMinimumHeight(getScaleHeight(minHeight));

    }

    public static void autoTextSize(View view){
    	if(view instanceof TextView){
    		double designPixels = ((TextView) view).getTextSize();
    		double displayPixels = sTextAutoScale * designPixels;
    		((TextView) view).setIncludeFontPadding(false);
    		((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) displayPixels);
    	}
    }

	public static float getScaleWidth(float width){
		if(width < 2) return width;
		return (width * sAutoScaleX);
	}

	public static int getScaleWidth(int width){
    	if(width < 2) return width;
        return (int) (width * sAutoScaleX);
    }

    public static int getScaleHeight(int height){
    	if(height < 2) return height;
        return (int) (height * sAutoScaleX);
    }
}
