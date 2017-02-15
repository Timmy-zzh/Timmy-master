package com.timmy.project.aidlBinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import com.timmy.IRemoteService;

public class LocalService extends Service {
    public LocalService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends IRemoteService.Stub {

        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String getServiceName() throws RemoteException {
            return "pid: " + Process.myPid();
        }
    }

}
