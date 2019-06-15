package com.jack.schedule.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;


import com.jack.schedule.bean.HorizontalGridBean;
import com.jack.schedule.bean.VerticalGridBean;
import com.jack.schedule.bean.WeekBean;
import com.jack.schedule.utlis.AutoSizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : jack(黄冲)
 * e-mail : 907755845@qq.com
 * create : 2019/4/21
 * desc   : 周视图
 */
public class WeekView extends BaseEdgeEffect {

    private float cTabWidth = 190;
    private float cTabHeight = 86;

    private HorizontalTabCanvas mHorizontalTabCanvas;
    private VerticalTabCanvas mVerticalTabCanvas;
    private ContentCanvas mContentCanvas;
    private OnClickWeekListener mOnClickWeekListener;
    private OnCardClickListener mOnCardClickListener;

    private List<List<WeekBean>> mWeekData = new ArrayList<>();


    public WeekView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        AutoSizeUtils.autoSize(this);
        mHorizontalTabCanvas = new HorizontalTabCanvas(this);
        mVerticalTabCanvas = new VerticalTabCanvas(this);
        mContentCanvas = new ContentCanvas(this);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w * h > 0){
            init();
        }
    }

    private void init() {
        mHorizontalTabCanvas.initData();
        mVerticalTabCanvas.initData();
        mContentCanvas.initData();
        setMaxVal(getMaxWidth(), getMaxHeight());
    }

    @Override
    protected void onDrawEdgeEffectLeft(Canvas canvas) {
        super.onDrawEdgeEffectLeft(canvas);
        canvas.translate(-getTabHeight(), getTabWidth());
    }

    @Override
    protected void onDrawEdgeEffectRigth(Canvas canvas) {
        super.onDrawEdgeEffectRigth(canvas);
        canvas.translate(getTabHeight(), 0);
    }

    @Override
    public void onScale(ScaleDetector detector) {
        mHorizontalTabCanvas.scale(detector.scaleX);
        mVerticalTabCanvas.scale(detector.scaleY);
        mContentCanvas.scale(detector.scaleX, detector.scaleY);
        setMaxVal(getMaxWidth(), getMaxHeight());
    }


    @Override
    protected void onDrawContent(Canvas canvas) {
        drawBitmap(canvas);
    }


    private void drawBitmap(Canvas canvas) {

        if (getContentBitmap() == null){
            return;
        }

        drawWeek();

        drawHorizontalGrid();

        drawVerticalGrid();

        canvas.drawBitmap(getContentBitmap(), getTabWidth(), getTabHeight(), null);

        drawHorizontalTab(canvas);

        drawVerticalTab(canvas);
    }

    private void drawWeek() {
        mContentCanvas.draw();
    }

    private void drawHorizontalGrid() {
        mHorizontalTabCanvas.drawGrid(getContentCanvas());
    }

    private void drawVerticalGrid() {
        mVerticalTabCanvas.drawGrid(getContentCanvas());
    }


    private void drawHorizontalTab(Canvas canvas) {
        mHorizontalTabCanvas.draw(canvas);
    }

    private void drawVerticalTab(Canvas canvas) {
        mVerticalTabCanvas.draw(canvas);
    }


    public List<HorizontalGridBean> getHorizontalGridList() {
        return mHorizontalTabCanvas.getGridList();
    }

    public List<VerticalGridBean> getVerticalGridList() {
        return mVerticalTabCanvas.getGridList();
    }


    public float getTabWidth() {
        return cTabWidth;
    }

    public float getTabHeight() {
        return cTabHeight;
    }

    public int getMaxWidth(){
        float maxWidth = mHorizontalTabCanvas.getMaxWidth();
        return (int) (maxWidth - getContentBitmap().getWidth());
    }

    public int getMaxHeight(){
        float maxHeight = mVerticalTabCanvas.getMaxHeight();
        return (int) (maxHeight - getContentBitmap().getHeight());
    }

    public float getOffsetX(){
        return mOffsetX;
    }

    public float getOffsetY(){
        return mOffsetY;
    }

    public void setWeekData(List<List<WeekBean>> weekData) {
        mWeekData.clear();
        mWeekData.addAll(weekData);
        invalidate();
    }

    public Bitmap getContentBitmap(){
        return mContentCanvas.getBitmap();
    }

    public Canvas getContentCanvas(){
        return mContentCanvas.getCanvas();
    }

    public float getDialHeight(){
        return mVerticalTabCanvas.getDialHeight();
    }

    public float getDialCap(){
        return mVerticalTabCanvas.getDialCap();
    }

    public List<List<WeekBean>> getWeekData(){
        return mWeekData;
    }

    @Override
    public void onClick(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (x < getTabWidth() && y < getTabHeight() && mOnClickWeekListener != null){
            mOnClickWeekListener.onClick(this, 0);
            rotateArrow(true);
            return;
        }
        if (mOnCardClickListener == null){
            return;
        }
        x = x - getTabWidth() - getOffsetX();
        y = y - getTabHeight() - getOffsetY();

        for (List<WeekBean> weekDatum : mWeekData) {
            for (WeekBean weekBean : weekDatum) {
                if (weekBean.getRectF().contains(x, y)) {
                    mOnCardClickListener.onClick(this, weekBean);
                    return;
                }
            }
        }
    }

    public void rotateArrow(boolean rotate){

    }

    public void setOnClickWeekListener(OnClickWeekListener onClickWeekListener) {
        mOnClickWeekListener = onClickWeekListener;
    }

    public void setOnCardClickListener(OnCardClickListener onCardClickListener) {
        mOnCardClickListener = onCardClickListener;
    }

    public void setWeekTime(long weekTime) {
        mHorizontalTabCanvas.setWeekTime(weekTime);
        invalidate();
    }

    public interface OnClickWeekListener{
        void onClick(WeekView weekView, long time);
    }
    public interface OnCardClickListener{
        void onClick(WeekView weekView, WeekBean bean);
    }

}
