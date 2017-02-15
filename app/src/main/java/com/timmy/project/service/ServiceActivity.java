package com.timmy.project.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.library.util.Logger;
import com.timmy.library.util.Toast;

public class ServiceActivity extends BaseActivity {

    private String TAG = this.getClass().getSimpleName();
    private Intent intentBind;
    private Intent intent;
    private MyServiceConnection myServiceConnection = new MyServiceConnection();
    private DemoBindServiceB mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initToolBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //绑定服务
        Intent intent = new Intent(this, DemoBindServiceB.class);
        bindService(intent, myServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBinded) {
            unbindService(myServiceConnection);
        }
    }

    public void handerService(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                if (intent == null)
                    intent = new Intent(this, DemoServiceA.class);
                startService(intent);
                break;
            case R.id.btn2:
                stopService(intent);
                break;
            case R.id.btn3:
                if (intentBind == null)
                    intentBind = new Intent(this, DemoBindServiceB.class);
                bindService(intentBind, myServiceConnection, 0);
                break;
            case R.id.btn4:
                unbindService(myServiceConnection);
                break;
        }
    }

    public void testClick(View view) {
        if (mBinded) {
//            Toast.makeText(this, "number:" + mService.getRandomNum(), Toast.LENGTH_SHORT).show();
           Toast.toastShort("number:" + mService.getRandomNum());
        }
    }

    private boolean mBinded;

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.d(TAG, "onServiceConnected-name:" + name + ",service:" + service);
            DemoBindServiceB.LocalBinder binder = (DemoBindServiceB.LocalBinder) service;
            mService = binder.getService();
            mBinded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.d(TAG, "onServiceDisconnected-name:" + name);
            mBinded = false;
        }
    }

}
