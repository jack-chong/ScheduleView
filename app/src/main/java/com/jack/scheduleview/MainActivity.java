package com.jack.scheduleview;

import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

        initView();


        initData();
    }

    private void initView() {
        mWeekView = findViewById(R.id.weekView);
        mWeekView.setOnClickWeekListener((weekView, time) -> {
            Toast.makeText(MainActivity.this, "点击周", Toast.LENGTH_SHORT).show();
        });
        mWeekView.setOnCardClickListener((weekView, bean) -> {
            Toast.makeText(MainActivity.this, "点击卡片: " + bean.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    private void initData(){
        List<WeekBean> weekBeans = new Gson().fromJson(WeekData.json, new TypeToken<List<WeekBean>>() {}.getType());
        List<List<WeekBean>> lists = ListUtils.split(weekBeans, "getStartDay", true);

        for (List<WeekBean> list : lists) {
            ListUtils.sort(list, "getStartTime", true);
            for (int i = 0; i < list.size(); i++) {
                find(i, list);
            }
        }
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
