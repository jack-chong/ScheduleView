package com.jack.schedule.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.text.TextPaint;


import com.jack.schedule.bean.VerticalGridBean;
import com.jack.schedule.utlis.AutoSizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author : jack(黄冲)
 * e-mail : 907755845@qq.com
 * create : 2019/4/23
 * desc   : 垂直的tab
 */
public class VerticalTabCanvas extends BaseCanvas{

    private static final int MAX_VERTICAL_SCALE = 3;
    private List<VerticalGridBean> mGridList = new ArrayList<>();

    private float mInitHeight;
    private float cTextSize = 22;
    private int mTextColor = Color.BLACK;

    private float cTextLeft = 102;

    private float cDialLeft = 170;
    private float cDialWidth = 10;

    private String[] mTimeArr = {"全天", "01:00", "03:00", "05:00", "07:00", "09:00", "11:00", "13:00", "15:00", "17:00", "19:00", "21:00", "23:00"};
    private Paint mDialPaint, mGridPaint, mBackPaint;
    private TextPaint mTimePaint;

    private int mGridColor = Color.parseColor("#B3C0C8FE");
    private int mBackColor = Color.WHITE;
    private int mDialColor = Color.parseColor("#FF9EBDFF");

    private Bitmap mBitmap;
    private Canvas mTabCanvas;

    public VerticalTabCanvas(WeekView weekView) {
        super(weekView);
        AutoSizeUtils.autoSize(this);
        initPaint();
    }

    public void initData() {
        mBitmap = Bitmap.createBitmap((int)(getTabWidth()), (int)(getHeight() - getTabHeight()), Bitmap.Config.ARGB_8888);
        mTabCanvas = new Canvas(mBitmap);
        mGridList.clear();
        mInitHeight = (getHeight() - getWeekView().getTabHeight()) / mTimeArr.length;
        for (int i = 0; i < mTimeArr.length; i++) {
            mGridList.add(new VerticalGridBean(mInitHeight, mTimeArr[i]));
        }
    }


    private void initPaint() {
        mTimePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTimePaint.setTextSize(cTextSize);
        mTimePaint.setColor(mTextColor);

        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setColor(mDialColor);
        mDialPaint.setStyle(Paint.Style.STROKE);
        mDialPaint.setStrokeWidth(2);

        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setColor(mGridColor);
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setStrokeWidth(1);

        mBackPaint = new Paint();
        mBackPaint.setColor(mBackColor);


    }

    public void draw(Canvas canvas) {

        mTabCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mTabCanvas.save();
        mTabCanvas.drawColor(mBackColor);
        mTabCanvas.translate(0, getOffsetY());

        float offsetY = mGridList.get(0).height / 2;

        for (VerticalGridBean bean : mGridList) {
            mTimePaint.setColor(bean.timeStr.equals("全天") ? Color.parseColor("#4d64ff") : mTextColor);
            mTabCanvas.drawText(bean.timeStr, cTextLeft, textCoordCenter(offsetY, mTimePaint), mTimePaint);
            mTabCanvas.drawLine(cDialLeft, offsetY, cDialLeft + cDialWidth, offsetY, mDialPaint);
            offsetY += bean.height;
        }

        mTabCanvas.restore();
        canvas.drawBitmap(mBitmap, 0, getTabHeight(), null);
        canvas.drawLine(getTabWidth(), getTabHeight(), getTabWidth(), getHeight(), mDialPaint);
    }

    public void drawGrid(Canvas canvas) {
        canvas.save();
        canvas.translate(0, getOffsetY());
        float offsetY = 0;
        for (VerticalGridBean bean : mGridList) {
            canvas.drawLine(0, offsetY, getWidth(), offsetY, mGridPaint);
            offsetY += bean.height;
        }
        canvas.restore();
    }

    public void scale(float scaleY) {
        for (VerticalGridBean bean : mGridList) {
            bean.height = bean.height + bean.height * scaleY;

            if (bean.height < mInitHeight){
                bean.height = mInitHeight;
            }
            if (bean.height > mInitHeight * MAX_VERTICAL_SCALE){
                bean.height = mInitHeight * MAX_VERTICAL_SCALE;
            }
        }
    }


    public float getMaxHeight() {
        float maxVal = 0;
        for (VerticalGridBean verticalGridBean : mGridList) {
            maxVal += verticalGridBean.height;
        }
        return maxVal;
    }


    public List<VerticalGridBean> getGridList() {
        return mGridList;
    }

    public float getDialHeight() {
        float height = 0;
        for (int i = 1; i < mGridList.size(); i++) {
            height += mGridList.get(i).height;
        }
        return height;
    }

    public float getDialCap() {
        return mGridList.get(0).height;
    }

}
