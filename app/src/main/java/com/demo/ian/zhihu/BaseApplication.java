package com.demo.ian.zhihu;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Admin on 2016-9-8.
 */
public class BaseApplication extends Application {
    public static Context context;

    public static Context getAppContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LeakCanary.install(this);
    }
}
