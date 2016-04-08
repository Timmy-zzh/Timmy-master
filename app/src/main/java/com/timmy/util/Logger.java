package com.timmy.util;

import android.util.Log;

public class Logger {
	public static String TAG = "Timmy";

	public static void info(String msg) {
		Log.i(TAG, msg);
	}

	public static void d(String msg) {
		Log.d(TAG, msg);
	}

	public static void e(String msg, Throwable tr) {
		Log.e(TAG, msg, tr);
	}
}
