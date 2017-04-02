package com.timmy.framework.netFw.utils;

import android.accounts.NetworkErrorException;
import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by timmy1 on 17/4/2.
 * 网络请求工具类--异步实现方式
 * 通过接口和handler实现
 */
public class AsyncNetUtils {

    private static HttpURLConnection connection;

    /**
     * post请求
     */
    public static void post(final String url, final String content, final NetWorkCallback callback) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = NetUtils.post(url,content);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            callback.onSuccess(result);
                        } else {
                            callback.onFail();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * get请求
     */
    public static void get(final String url, final NetWorkCallback callback) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = NetUtils.get(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (result != null) {
                            callback.onSuccess(result);
                        } else {
                            callback.onFail();
                        }
                    }
                });
            }
        }).start();
    }
}
