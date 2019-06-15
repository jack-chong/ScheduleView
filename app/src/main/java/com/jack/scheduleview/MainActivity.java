package com.jack.scheduleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jack.schedule.bean.CardColor;
import com.jack.schedule.bean.ScheduleBean;
import com.jack.schedule.utlis.ListUtils;
import com.jack.schedule.utlis.TimeUtils;
import com.jack.schedule.bean.WeekBean;
import com.jack.schedule.view.WeekView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWeekView = findViewById(R.id.weekView);

        initData();
    }

    private void initData(){
        CardColor[] mColors = {new CardColor("#B3C9EFE0", "#FFAFE7D1"), new CardColor("#B3ADCEF0", "#FF7BB2F2"),
                new CardColor("#B3BBBFFF", "#FF8A9AFE"), new CardColor("#B3EAEDE7", "#FFD6DEEC")};

        List<WeekBean> list = new ArrayList<>();
        List<List<WeekBean>> lists = new ArrayList<>();
        {
            WeekBean weekBean = new WeekBean(new ScheduleBean());
            weekBean.setAddress("JD");
            weekBean.setTitle("8");
            weekBean.setStartTime((int) (TimeUtils.getTodayZero() / 1000) + TimeUtils.HOUT_SECOND * 8);
            weekBean.setStartDay((int) (TimeUtils.getTodayZero() / 1000));
            weekBean.setEndTime(weekBean.getStartTime() + TimeUtils.HOUT_SECOND * 3);
            weekBean.setColor(mColors[new Random().nextInt(mColors.length)]);
            list.add(weekBean);
        }
        {
            WeekBean weekBean = new WeekBean(new ScheduleBean());
            weekBean.setAddress("JD");
            weekBean.setTitle("8.5");
            weekBean.setStartTime((int) ((int) (TimeUtils.getTodayZero() / 1000) + TimeUtils.HOUT_SECOND * 8.5));
            weekBean.setStartDay((int) (TimeUtils.getTodayZero() / 1000));
            weekBean.setEndTime((int) (weekBean.getStartTime() + TimeUtils.HOUT_SECOND * 0.5f));
            weekBean.setColor(mColors[new Random().nextInt(mColors.length)]);
            list.add(weekBean);
        }
        {
            WeekBean weekBean = new WeekBean(new ScheduleBean());
            weekBean.setAddress("JD");
            weekBean.setTitle("10");
            weekBean.setStartTime((int) (TimeUtils.getTodayZero() / 1000) + TimeUtils.HOUT_SECOND * 10);
            weekBean.setStartDay((int) (TimeUtils.getTodayZero() / 1000));
            weekBean.setEndTime(weekBean.getStartTime() + TimeUtils.HOUT_SECOND * 3);
            weekBean.setColor(mColors[new Random().nextInt(mColors.length)]);
            list.add(weekBean);
        }
        {
            WeekBean weekBean = new WeekBean(new ScheduleBean());
            weekBean.setAddress("JD");
            weekBean.setTitle("10.5");
            weekBean.setStartTime((int) ((int) (TimeUtils.getTodayZero() / 1000) + TimeUtils.HOUT_SECOND * 10.5));
            weekBean.setStartDay((int) (TimeUtils.getTodayZero() / 1000));
            weekBean.setEndTime(weekBean.getStartTime() + TimeUtils.HOUT_SECOND * 1);
            weekBean.setColor(mColors[new Random().nextInt(mColors.length)]);
            list.add(weekBean);
        }
        {
            WeekBean weekBean = new WeekBean(new ScheduleBean());
            weekBean.setAddress("JD");
            weekBean.setTitle("11.5");
            weekBean.setStartTime((int) ((int) (TimeUtils.getTodayZero() / 1000) + TimeUtils.HOUT_SECOND * 11.5));
            weekBean.setStartDay((int) (TimeUtils.getTodayZero() / 1000));
            weekBean.setEndTime(weekBean.getStartTime() + TimeUtils.HOUT_SECOND * 2);
            weekBean.setColor(mColors[new Random().nextInt(mColors.length)]);
            list.add(weekBean);
        }

        List<WeekBean> list2 = new ArrayList<>();

        {
            WeekBean weekBean = new WeekBean(new ScheduleBean());
            weekBean.setAddress("交融交流会");
            weekBean.setTitle("教六 A6011");
            weekBean.setStartTime((int) ((int) (TimeUtils.getTodayZero() / 1000) + TimeUtils.HOUT_SECOND * 36.5));
            weekBean.setStartDay((int) (TimeUtils.getTodayZero() / 1000) + TimeUtils.DAY_SECOND);
            weekBean.setEndTime(weekBean.getStartTime() + TimeUtils.HOUT_SECOND * 5);
            weekBean.setColor(mColors[new Random().nextInt(mColors.length)]);
            list2.add(weekBean);
        }

        {
            WeekBean weekBean = new WeekBean(new ScheduleBean());
            weekBean.setAddress("交融交流会123456");
            weekBean.setTitle("教六 北京市科创十一街京东大厦A桌");
            weekBean.setStartTime((int) ((int) (TimeUtils.getTodayZero() / 1000) + TimeUtils.HOUT_SECOND * 37.5));
            weekBean.setStartDay((int) (TimeUtils.getTodayZero() / 1000) + TimeUtils.DAY_SECOND);
            weekBean.setEndTime(weekBean.getStartTime() + TimeUtils.HOUT_SECOND * 7);
            weekBean.setColor(mColors[new Random().nextInt(mColors.length)]);
            list2.add(weekBean);
        }


        ListUtils.sort(list, "getStartTime", true);
        ListUtils.sort(list2, "getStartTime", true);


        for (int i = 0; i < list.size(); i++) {
            find(i, list);
        }
        for (int i = 0; i < list2.size(); i++) {
            find(i, list2);
        }



        lists.add(list);
        lists.add(list2);
        mWeekView.setWeekData(lists);
    }


    private static void find(int pos, List<WeekBean> list) {
        WeekBean bean = list.get(pos);
        int startTime = bean.getStartTime();
        if (pos == 0) return;


        WeekBean allBean = null;
        WeekBean previousBean = null;
        WeekBean vacancyBean = null;
        int previousRowIndex = 0;
        boolean isVacancy = false;
        List<Integer> rowIndexList = new ArrayList<>();
        for (int i = pos - 1; i >= 0; i--) {
            WeekBean oldBean = list.get(i);
            if (bean.isAllDay() && oldBean.isAllDay()) {
                allBean = oldBean;
                break;
            }
            if (oldBean.isAllDay()) continue;


            if (oldBean.getStartTime() < startTime && oldBean.getEndTime() > startTime) {
                previousBean = oldBean;
                previousRowIndex = previousBean.getRowIndex();
                rowIndexList.add(previousRowIndex);
            } else {

                if (rowIndexList.contains(oldBean.getRowIndex())) {
                    //同一列已经没有空位了
                    continue;
                }
                vacancyBean = oldBean;
                isVacancy = true;
            }
        }

        if (allBean == null && bean.isAllDay()) {
            return;
        }

        if (allBean != null) {
            //全天
            bean.setBeforeBean(allBean);

            if (allBean.getRowCount() + 1 > 5) {
                bean.setSpanSize(0);
                return;
            }
            bean.setRowCount(allBean.getRowCount() + 1);
            bean.setRowIndex(bean.getRowCount() - 1);

        } else if (isVacancy) {
            //有空位

            bean.setBeforeBean(vacancyBean);
            vacancyBean.setLaterBean(bean);
            bean.setRowCount(vacancyBean.getRowCount());
            bean.setRowIndex(vacancyBean.getRowIndex());

        } else {
            //没有空位, 取最后一个
            previousBean = list.get(pos - 1);
            bean.setBeforeBean(previousBean);
            previousBean.setLaterBean(bean);

            if (previousBean.getRowCount() + 1 > 5) {
                bean.setSpanSize(0);
                return;
            }
            bean.setRowCount(previousBean.getRowCount() + 1);
            bean.setRowIndex(bean.getRowCount() - 1);
        }
    }

}
