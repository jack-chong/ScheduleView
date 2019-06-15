package com.jack.scheduleview;

import android.app.Application;
import android.util.DisplayMetrics;

import com.jack.schedule.utlis.AutoUtils;
import com.jack.schedule.bean.DeviceInfo;
import com.jack.schedule.utlis.WindowUtils;

/**
 * author : jack(黄冲)
 * e-mail : 907755845@qq.com
 * create : 2019-06-14
 * desc   :
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initDevice();
        AutoUtils.setSize(this, false, 750, 1333);
    }




    private void initDevice() {
        DisplayMetrics screenSize = WindowUtils.getScreenSize(this);
        DeviceInfo.sScreenWidth = screenSize.widthPixels;
        DeviceInfo.sScreenHeight = screenSize.heightPixels;
        DeviceInfo.sAutoScaleX = DeviceInfo.sScreenWidth * 1.0f / DeviceInfo.UI_WIDTH;
        DeviceInfo.sAutoScaleY = DeviceInfo.sScreenHeight * 1.0f / DeviceInfo.UI_HEIGHT;
        DeviceInfo.sNavigationBarHeight = WindowUtils.getNavigationBarHeight(this);
        DeviceInfo.sStatusBarHeight = WindowUtils.getStatusBarHeight(this);


    }

}
