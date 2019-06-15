package com.jack.schedule.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;


import com.jack.schedule.bean.HorizontalGridBean;
import com.jack.schedule.bean.WeekBean;
import com.jack.schedule.utlis.AutoSizeUtils;
import com.jack.schedule.utlis.TimeUtils;

import java.util.List;

/**
 * author : jack(黄冲)
 * e-mail : 907755845@qq.com
 * create : 2019/4/23
 * desc   :
 */
public class ContentCanvas extends BaseCanvas {

    private float cRound = 6;
    private float cCardHeight = 4;
    private float cStartTop = 116;
    private float cTitleTextSize = 24;
    private float cAddressTextSize = 18;
    private float cTextHorizontalPadding = 10;
    private float cTextVerticalPadding = 3;
    private float cRectPadding = 5;
    private float cTextMinHeight = 50;
    private int mAllDayColor = Color.parseColor("#41CCCCCC");
    private int mStartTime = TimeUtils.HOUT_SECOND * 0;
    private int mEndTime = TimeUtils.HOUT_SECOND * 24;
    private int mTimeLen = mEndTime - mStartTime;


    private Paint mAllDayPaint;
    private TextPaint mTitlePaint, mAddressPaint;
    private Paint mBackPaint;
    private Canvas mChartCanvas;
    private Bitmap mBitmap;

    public ContentCanvas(WeekView weekView) {
        super(weekView);
        AutoSizeUtils.autoSize(this);
        initPaint();
    }

    private void initPaint() {
        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTitlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTitlePaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTitlePaint.setColor(Color.parseColor("#FF333333"));
        mTitlePaint.setTextSize(cTitleTextSize);


        mAddressPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mAddressPaint.setColor(Color.parseColor("#FF333333"));
        mAddressPaint.setTextSize(cAddressTextSize);

        mAllDayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAllDayPaint.setColor(mAllDayColor);
    }


    public void initData() {
        if (mBitmap != null) return;
        mBitmap = Bitmap.createBitmap((int)(getWidth() - getTabWidth()), (int)(getHeight() - getTabHeight()), Bitmap.Config.ARGB_8888);
        mChartCanvas = new Canvas(mBitmap);
    }


    public void draw() {
        mChartCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        drawAllDayBackground();

        mChartCanvas.save();
        mChartCanvas.translate(getOffsetX(), getOffsetY());

        float offsetX = 0;

        List<HorizontalGridBean> gridList = getWeekView().getHorizontalGridList();
        for (int i = 0; i < getWeekData().size(); i++) {
            List<WeekBean> list = getWeekData().get(i);
            for (int j = 0; j < list.size(); j++) {
                drawContent(offsetX, gridList.get(i), list.get(j));
            }
            offsetX += gridList.get(i).width;
        }
        mChartCanvas.restore();
    }

    public void drawAllDayBackground() {
        mChartCanvas.save();
        mChartCanvas.translate(0, getOffsetY());
        mChartCanvas.drawRect(0, 0, getWidth(), getWeekView().getVerticalGridList().get(0).height, mAllDayPaint);
        mChartCanvas.restore();
    }


    private void drawContent(float offsetX, HorizontalGridBean gridBean, WeekBean bean) {


        RectF rectF;
        if (bean.getRectF() == null) {
            rectF = createRectF();
            bean.setRectF(rectF);
        }
        rectF = bean.getRectF();
        fillData(offsetX, gridBean, rectF, bean);

        if (bean.getSpanSize() == 0) return;


        float left = rectF.left + cTextHorizontalPadding;
        float top =  rectF.top + cTextVerticalPadding;

        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setColor(bean.getColor().normalColor);
        mBackPaint.setAntiAlias(true);
        mChartCanvas.drawRoundRect(rectF, cRound, cRound, mBackPaint);

        mBackPaint.setPathEffect(new CornerPathEffect(cRound));
        Path path = new Path();
        path.moveTo(rectF.centerX(), rectF.bottom);
        path.lineTo(rectF.right - cRound / 2, rectF.bottom);
        path.lineTo(rectF.right, rectF.bottom - cRound * 1.5f);
        path.lineTo(rectF.right - cRound, rectF.bottom - cCardHeight);
        path.lineTo(rectF.left + cRound, rectF.bottom - cCardHeight);
        path.lineTo(rectF.left, rectF.bottom - cRound * 1.5f);
        path.lineTo(rectF.left + cRound / 2, rectF.bottom);
        path.close();

        mBackPaint.setColor(bean.getColor().bottomColor);
        mChartCanvas.drawPath(path, mBackPaint);


        int width = (int) (rectF.width() - cTextHorizontalPadding * 2);
        int height = (int) rectF.height();

        if (width * height <= 0){
            return;
        }

        float titleSpace = mTitlePaint.descent() - mTitlePaint.ascent();
        float addressSpace = mTitlePaint.descent() - mTitlePaint.ascent();
        String title = bean.getTitle();
        float addressTop = cTextHorizontalPadding;

        if (!TextUtils.isEmpty(title)){
            mChartCanvas.save();
            mChartCanvas.translate(left, top);
            mChartCanvas.clipRect(0, 0, rectF.width() - cTextHorizontalPadding * 2, rectF.height() - cCardHeight - cTextHorizontalPadding);
            StaticLayout titleLayout = new StaticLayout(title, mTitlePaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 1, true);
            titleLayout.draw(mChartCanvas);
            int lineCount = titleLayout.getLineCount();
            addressTop = titleSpace * lineCount;
            mChartCanvas.restore();
        }


        if (!TextUtils.isEmpty(bean.getAddress()) && addressSpace < rectF.height() - addressTop){
            mChartCanvas.save();
            mChartCanvas.translate(left, top + addressTop);
            mChartCanvas.clipRect(0, 0, rectF.width() - cTextHorizontalPadding * 2, rectF.bottom - (top + addressTop));
            StaticLayout addressLayout = new StaticLayout(bean.getAddress(), mAddressPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 1, true);
            addressLayout.draw(mChartCanvas);
            mChartCanvas.restore();
        }

    }

    private RectF createRectF(){
        return new RectF();
    }

    private void fillData(float offsetX, HorizontalGridBean gridBean, RectF rectF, WeekBean bean) {

        int startTime = bean.getStartTime() - bean.getStartDay();
        int endTime = bean.getEndTime() - bean.getStartDay();

        float width = gridBean.width / bean.getRowCount();
        float sx = width * bean.getRowIndex() + offsetX + cRectPadding;
        float ex = sx + width * bean.getSpanSize() - cRectPadding;
        float sy = findCoordByTime(startTime - mStartTime) + cRectPadding;
        float ey = findCoordByTime(endTime - mStartTime) - cRectPadding;

        if (bean.isAllDay()) {
            sy = cRectPadding;
            ey = getWeekView().getDialCap() - cRectPadding;
        }
        rectF.set(sx, sy, ex, ey);
    }

    private float findCoordByTime(int time){

        return (time > TimeUtils.DAY_SECOND ? TimeUtils.DAY_SECOND : time) * 1.0f / mTimeLen * getWeekView().getDialHeight() + getWeekView().getDialCap();
    }

    public void scale(float scaleX, float scaleY){
        for (int i = 0; i < getWeekData().size(); i++) {
            List<WeekBean> list = getWeekData().get(i);
            for (int j = 0; j < list.size(); j++) {
                RectF rectF = list.get(j).getRectF();
                rectF.left = rectF.left + rectF.left * scaleX;
                rectF.right = rectF.right + rectF.right * scaleX;
                rectF.top = rectF.top + rectF.top * scaleY;
                rectF.bottom = rectF.bottom + rectF.bottom * scaleY;
            }
        }
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public Canvas getCanvas() {
        return mChartCanvas;
    }

}
