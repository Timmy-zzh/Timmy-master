package com.timmy.library.util;

import android.util.Log;

public class Logger {

    public static String TAG = "Timmy_Library";

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg, Throwable tr) {
        Log.e(TAG, msg, tr);
    }
}
