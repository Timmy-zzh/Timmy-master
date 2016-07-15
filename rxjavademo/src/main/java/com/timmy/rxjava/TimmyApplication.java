package com.timmy.rxjava;

import android.app.Application;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class TimmyApplication extends Application {

    public static TimmyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
    }

}
