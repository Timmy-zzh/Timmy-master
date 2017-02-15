package com.timmy.project.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.timmy.library.util.Logger;

import java.util.Random;

public class DemoBindServiceB extends Service {

    private String TAG = this.getClass().getSimpleName();
    private final IBinder mBinder = new LocalBinder();
    private final Random random = new Random();

    //线程内通信
    public class LocalBinder extends Binder {

        DemoBindServiceB getService() {
            return DemoBindServiceB.this;
        }
    }

    public DemoBindServiceB() {
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
        return mBinder;
    }

    /**
     * 服务内的方法
     */
    public int getRandomNum() {
        return random.nextInt(100);
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
