package com.nodeveloper.myapplication.application;

import android.app.Application;

import com.nodeveloper.myapplication.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APPID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APPID);
    }
}
