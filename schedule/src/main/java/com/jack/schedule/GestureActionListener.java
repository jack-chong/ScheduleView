package com.jack.schedule;

import android.view.MotionEvent;
import android.widget.OverScroller;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/4/12 09:39
 */

public interface GestureActionListener {

    void down(MotionEvent event);

    void move(MotionEvent event);

    void up(MotionEvent event);

    void pointerDown(MotionEvent event);

    void pointerMove(MotionEvent event);

    void pointerUp(MotionEvent event);

    void onScale(ScaleDetector detector);

    void onClick(MotionEvent event);

    void onDrag(MotionEvent event, float dx, float dy);

    void onFinishRoll();

    void onRoll(OverScroller scroller);

    void onRollCancel();

    public class ScaleDetector{
        public float scale, scaleX, scaleY;
        public float absScale, absScaleX, absScaleY;
        public float centerX, centerY;
    }
}
