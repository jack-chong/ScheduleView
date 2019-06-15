package com.jack.schedule.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.text.TextPaint;


import com.jack.schedule.R;
import com.jack.schedule.bean.HorizontalGridBean;
import com.jack.schedule.utlis.AutoSizeUtils;
import com.jack.schedule.utlis.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : jack(黄冲)
 * e-mail : 907755845@qq.com
 * create : 2019/4/23
 * desc   : 水平的tab
 */
public class HorizontalTabCanvas extends BaseCanvas{


    private static final int MAX_HORIZONTAL_SCALE = 5;

    private List<HorizontalGridBean> mGridList = new ArrayList<>();

    private float mInitWidth;
    private float cWeekTextSize = 28;
    private float cWeekDateSize = 24;
    private float cWeekTop = 5;
    private float cDateTop = 43;
    private float cDateLeft = 110;
    private float cArrowLeft = 160;
    private float cArrowSize = 30;
    private float cNavigationWidth = 56;
    private float cNavigationHeight = 3;
    private float cNavigationTop = 78;

    private int mSelectColorY = Color.WHITE;
    private int mSelectColorN = Color.parseColor("#FFC0C8FE");
    private int mBackColor = Color.parseColor("#FF4D64FF");
    private int mGridColor = Color.parseColor("#B3C0C8FE");
    private TextPaint mWeekPaint;
    private TextPaint mDatePaint;
    private Paint mBackPaint, mGridPaint, mNavigationPaint;
    private int mCurrentYear = TimeUtils.getYears();
    private int mCurrentWeek = TimeUtils.getYearAndWeek();
    private long mWeekMillis = System.currentTimeMillis();

    private Bitmap mBitmap;
    private Canvas mTabCanvas;

    private long mToDay = TimeUtils.getTodayZero();
    private Bitmap mArrowBitmap;


    public HorizontalTabCanvas(WeekView weekView) {
        super(weekView);
        AutoSizeUtils.autoSize(this);
        initPaint();
    }

    public void initData() {
        mBitmap = Bitmap.createBitmap((int)(getWidth() - getTabWidth()), (int)getTabHeight(), Bitmap.Config.ARGB_8888);
        mTabCanvas = new Canvas(mBitmap);

        mInitWidth = (getWidth() - getTabWidth()) / 7;
        mGridList.clear();
        for (int i = 0; i < 7; i++) {
            float width = mInitWidth;
            HorizontalGridBean bean = new HorizontalGridBean(width);
            mGridList.add(bean);
        }
    }


    private void initPaint() {
        mWeekPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mDatePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mNavigationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mWeekPaint.setTextSize(cWeekTextSize);
        mDatePaint.setTextSize(cWeekDateSize);

        mBackPaint.setColor(mBackColor);
        mGridPaint.setColor(mGridColor);
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setStrokeWidth(1);

        mWeekPaint.setTextAlign(Paint.Align.CENTER);
        mDatePaint.setTextAlign(Paint.Align.CENTER);

        mNavigationPaint.setColor(Color.WHITE);
        mNavigationPaint.setStrokeCap(Paint.Cap.ROUND);
        mNavigationPaint.setStrokeWidth(cNavigationHeight);

        mArrowBitmap = BitmapFactory.decodeResource(getWeekView().getResources(), R.drawable.arrow_down_white);
    }

    public void draw(Canvas canvas) {

        mTabCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mTabCanvas.save();

        mTabCanvas.drawRect(0, 0, getWidth(), getTabHeight(), mBackPaint);
        mTabCanvas.translate(getOffsetX(), 0);

        float offsetX = 0;
        long startDay = mWeekMillis;

        for (HorizontalGridBean bean : mGridList) {
            String dateStr = TimeUtils.getDate("MM.dd", startDay);
            String weekStr = TimeUtils.getWeekOfDate2(startDay);
            float x = offsetX + bean.width / 2;


            if (startDay == mToDay){
                mWeekPaint.setColor(mSelectColorY);
                mDatePaint.setColor(mSelectColorY);
                float sx = x - cNavigationWidth / 2;
                float sy = cNavigationTop;
                float ex = x + cNavigationWidth / 2;
                float ey = cNavigationTop;
                mTabCanvas.drawLine(sx , sy, ex, ey, mNavigationPaint);
            }else {
                mWeekPaint.setColor(mSelectColorN);
                mDatePaint.setColor(mSelectColorN);
            }

            mTabCanvas.drawText(weekStr, x, textCoordTop(cWeekTop, mWeekPaint), mWeekPaint);
            mTabCanvas.drawText(dateStr, x, textCoordTop(cDateTop, mDatePaint), mDatePaint);
            offsetX += bean.width;
            startDay += TimeUtils.DAY_MILLIS;
        }
        mTabCanvas.restore();
        mWeekPaint.setColor(mSelectColorY);
        mDatePaint.setColor(mSelectColorY);

        canvas.drawBitmap(mBitmap, getTabWidth(), 0, null);
        canvas.drawRect(0, 0, getTabWidth(), getTabHeight(), mBackPaint);

        if (mCurrentWeek > 0){
            //不是课程周就不显示
            canvas.drawText("第" + mCurrentWeek + "周", cDateLeft, textCoordTop(cWeekTop, mWeekPaint), mWeekPaint);
            canvas.drawText(String.valueOf(mCurrentYear), cDateLeft, textCoordTop(cDateTop, mDatePaint), mDatePaint);
        }else {
            canvas.drawText(String.valueOf(mCurrentYear), cDateLeft, textCoordCenter(getTabHeight() / 2, mDatePaint), mDatePaint);
        }


        Rect src = new Rect(0, 0, mArrowBitmap.getWidth(), mArrowBitmap.getHeight());
        int sx = (int) cArrowLeft;
        int sy = (int) ((getTabHeight() - cArrowSize) / 2);
        int ex = (int) (cArrowLeft + cArrowSize);
        int ey = (int) (getTabHeight() - (getTabHeight() - cArrowSize) / 2);
        Rect dst = new Rect(sx, sy, ex, ey);
        canvas.drawBitmap(mArrowBitmap, src, dst, null);
    }

    public void drawGrid(Canvas canvas) {
        canvas.save();
        canvas.translate(getOffsetX(), 0);
        float offsetX = 0;
        for (HorizontalGridBean bean : mGridList) {
            canvas.drawLine(offsetX, 0, offsetX, getHeight(), mGridPaint);
            offsetX += bean.width;
        }
        canvas.restore();
    }

    public void scale(float scaleX) {
        for (int i = 0; i < mGridList.size(); i++) {
            HorizontalGridBean bean = mGridList.get(i);
            bean.width = bean.width + bean.width * scaleX;

            int rowCount = 1;
            if (getWeekData().size() > i) {
                rowCount = getRowCount(i);
            }
            if (rowCount > MAX_HORIZONTAL_SCALE){
                rowCount = MAX_HORIZONTAL_SCALE;
            }

            if (bean.width < mInitWidth){
                bean.width = mInitWidth;
            }
            if (bean.width > mInitWidth * rowCount){
                bean.width = mInitWidth * rowCount;
            }
        }
    }


    private int getRowCount(int i){
        if (getWeekData().get(i).isEmpty()){
            return 1;
        }
        return getWeekData().get(i).get(0).getRowCount();
    }

    public float getMaxWidth() {
        float width = 0;
        for (HorizontalGridBean bean : mGridList) {
            width += bean.width;
        }
        return width;
    }


    public List<HorizontalGridBean> getGridList() {
        return mGridList;
    }

    public void setWeekTime(long weekTime) {
        mWeekMillis = weekTime;
        mCurrentYear = TimeUtils.getYears(weekTime);
    }
}
