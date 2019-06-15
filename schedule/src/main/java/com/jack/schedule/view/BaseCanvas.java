package com.jack.schedule.view;

import android.text.TextPaint;

import com.jack.schedule.bean.WeekBean;

import java.util.List;

/**
 * author : jack(黄冲)
 * e-mail : 907755845@qq.com
 * create : 2019/4/23
 * desc   :
 */
public abstract class BaseCanvas {

    private WeekView mWeekView;

    public BaseCanvas(WeekView weekView) {
        mWeekView = weekView;
    }

    public WeekView getWeekView() {
        return mWeekView;
    }


    public float getWidth(){
        return getWeekView().getMeasuredWidth();
    }

    public float getHeight(){
        return getWeekView().getMeasuredHeight();
    }


    public float getTabWidth() {
        return mWeekView.getTabWidth();
    }

    public float getTabHeight() {
        return mWeekView.getTabHeight();
    }

    public float getOffsetX(){
        return mWeekView.getOffsetX();
    }

    public float getOffsetY(){
        return mWeekView.getOffsetY();
    }

    public List<List<WeekBean>> getWeekData(){
        return mWeekView.getWeekData();
    }

    /**
     * time    : 2019/4/23 12:24
     * desc    : 文字的左上角坐标
     * versions: 1.0
     */
    public static float textCoordTop(float y, TextPaint paint){
        return paint.descent() - paint.ascent() + y;
    }


    /**
     * time    : 2019/4/23 12:24
     * desc    : 文字的中间的坐标
     * versions: 1.0
     */
    public static float textCoordCenter(float y, TextPaint paint){
        return y - (paint.ascent() - paint.baselineShift) / 2;
    }
}
