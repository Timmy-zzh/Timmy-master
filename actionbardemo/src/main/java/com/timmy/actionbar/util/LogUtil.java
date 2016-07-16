package com.timmy.actionbar.util;

import android.util.Log;

public class LogUtil {
    public static String TAG = "RxJava";


    public static void info(String msg) {
//        if (BuildConfig.DEBUG)
        Log.i(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg, Throwable tr) {
        Log.e(TAG, msg, tr);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }


}
