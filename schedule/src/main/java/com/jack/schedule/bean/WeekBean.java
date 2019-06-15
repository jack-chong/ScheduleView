package com.jack.schedule.bean;

import android.graphics.RectF;


/**
 * author : jack(黄冲)
 * e-mail : 907755845@qq.com
 * create : 2019/4/23
 * desc   :
 */
public class WeekBean{

    public WeekBean(ScheduleBean schedule) {
        this.schedule = schedule;
    }

    private ScheduleBean schedule;

    private WeekBean beforeBean;
    private WeekBean laterBean;


    private RectF rectF;
    private int rowCount = 1;
    private int rowIndex;
    private CardColor color;
    private int spanSize = 1;


    public String getAddress() {
        return schedule.getAddress();
    }

    public int getStartTime() {
        return schedule.getStartTime();
    }

    public int getEndTime() {
        return schedule.getEndTime();
    }

    public int getStartDay() {
        return schedule.getStartDay();
    }

    public String getTitle() {
        return schedule.getTitle();
    }

    public void setAddress(String address) {
        schedule.setAddress(address);
    }

    public void setStartTime(int startTime) {
        schedule.setStartTime(startTime);
    }

    public void setEndTime(int endTime) {
        schedule.setEndTime(endTime);
    }

    public void setStartDay(int startDay) {
        schedule.setStartDay(startDay);
    }

    public void setTitle(String title) {
        schedule.setTitle(title);
    }

    public void setAllDay(boolean allDay) {
        schedule.setAllDay(allDay);
    }


    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int index) {
        this.rowIndex = index;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int count) {
        this.rowCount = count;
        if (beforeBean != null){
            beforeBean.setRowCount(count);
        }
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public CardColor getColor() {
        return color;
    }

    public void setSpanSize(int size) {
        this.spanSize = size;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public WeekBean getBeforeBean() {
        return beforeBean;
    }

    public void setBeforeBean(WeekBean beforeBean) {
        this.beforeBean = beforeBean;
    }

    public WeekBean getLaterBean() {
        return laterBean;
    }

    public void setLaterBean(WeekBean laterBean) {
        this.laterBean = laterBean;
    }

    public ScheduleBean getSchedule() {
        return schedule;
    }

    public boolean isAllDay() {
        return schedule.isAllDay();
    }
}
