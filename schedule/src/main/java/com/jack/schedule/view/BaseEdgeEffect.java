package com.jack.schedule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EdgeEffect;
import android.widget.OverScroller;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/4/8 09:47
 */

public abstract class BaseEdgeEffect extends BaseScrollerView {

    protected EdgeEffect mEdgeEffecLeft, mEdgeEffecRight;


    public BaseEdgeEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mEdgeEffecLeft = new EdgeEffect(context);
        mEdgeEffecRight = new EdgeEffect(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mEdgeEffecLeft.setColor(0xAAAAAAAA);
            mEdgeEffecRight.setColor(0xAAAAAAAA);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawEdgeEffect(canvas);
    }

    @Override
    public void onRoll(OverScroller scroller) {
        if (mMaxValX != 0 && mOffsetX == 0 && scroller.getCurrVelocity() > 1000) {
            mEdgeEffecLeft.onAbsorb((int) scroller.getCurrVelocity());
        }else if (mMaxValX != 0 && mOffsetX == -mMaxValX && scroller.getCurrVelocity() > 1000){
            mEdgeEffecRight.onAbsorb((int) scroller.getCurrVelocity());
        }
    }

    @Override
    public void onDrag(MotionEvent event, float dx, float dy) {
        super.onDrag(event, dx, dy);
        if (mMaxValX != 0 && dx + mOffsetX >= 0){
            mEdgeEffecLeft.onPull(dx /getWidth());
            mEdgeEffecRight.finish();
        }else if (mMaxValX != 0 && dx + mOffsetX <= -mMaxValX){
            mEdgeEffecRight.onPull(dx / getWidth());
            mEdgeEffecLeft.finish();
        }
    }

    @Override
    public void up(MotionEvent event) {
        mEdgeEffecLeft.onRelease();
        mEdgeEffecRight.onRelease();
        super.up(event);
    }

    private void onDrawEdgeEffect(Canvas canvas){
        canvas.save();
        onDrawEdgeEffectLeft(canvas);
        mEdgeEffecLeft.setSize(getHeight(), getWidth());
        boolean leftNotFinsh = mEdgeEffecLeft.draw(canvas);
        canvas.restore();

        canvas.save();
        onDrawEdgeEffectRigth(canvas);
        mEdgeEffecRight.setSize(getHeight(), getWidth());
        boolean rightNotFinsh = mEdgeEffecRight.draw(canvas);
        canvas.restore();

        if (leftNotFinsh || rightNotFinsh){
            postInvalidateOnAnimation();
        }
    }

    protected void onDrawEdgeEffectLeft(Canvas canvas){
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);
    }
    protected void onDrawEdgeEffectRigth(Canvas canvas){
        canvas.rotate(90);
        canvas.translate(0, -getWidth());
    }
}
