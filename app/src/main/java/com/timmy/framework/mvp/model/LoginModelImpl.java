package com.timmy.framework.mvp.model;

import android.os.Handler;
import android.os.Looper;

import com.timmy.framework.mvp.bean.User;

/**
 * Created by admin on 2017/7/19.
 */

public class LoginModelImpl implements ILoginModel<User> {

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void login(final User user, final OnLoginCallBack callBack) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(2000);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (user != null) {
                                String name = user.getName();
                                String password = user.getPassword();
                                if (name.equals("timmy") && password.equals("123")) {
                                    callBack.onSuccess();
                                } else {
                                    callBack.onFail();
                                }
                            } else {
                                callBack.onError(new IllegalArgumentException("参数为空"));
                            }
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
