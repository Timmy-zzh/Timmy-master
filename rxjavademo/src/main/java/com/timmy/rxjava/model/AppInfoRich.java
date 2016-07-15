package com.timmy.rxjava.model;

import android.app.Activity;
import android.content.pm.ResolveInfo;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class AppInfoRich {
    Activity activity;
    ResolveInfo info;

    public AppInfoRich(Activity activity, ResolveInfo info) {
        this.activity = activity;
        this.info = info;
    }
}
