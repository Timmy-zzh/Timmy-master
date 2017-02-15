package com.timmy.project.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.timmy.library.util.Logger;

public class DemoServiceA extends Service {
    private String TAG = this.getClass().getSimpleName();

    public DemoServiceA() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.d(TAG, "onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
