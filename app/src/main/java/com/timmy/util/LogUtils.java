package com.timmy.util;

import android.util.Log;

import com.timmy.BuildConfig;


/**
 * Created by Gyb on 2015/11/23.               
 */
public final class LogUtils {
    public static final int size = 3500;// log打印的msg大小大概为4k  超过4k 的msg需要分割打印
    public static void w(String msg){
        if(BuildConfig.DEBUG){
            String method = Thread.currentThread().getStackTrace()[1].getMethodName();
            for(int i=0;i*size<msg.length();i++){
                if(msg.length()<(i+1)*size){
                    Log.w(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size));
                }else {
                    Log.w(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size, (i + 1) * size));
                }
            }
        }

    }
    public static void e(String msg){
        if(BuildConfig.DEBUG) {
            String method = Thread.currentThread().getStackTrace()[1].getMethodName();
            for(int i=0;i*size<msg.length();i++){
                if(msg.length()<(i+1)*size){
                    Log.e(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size));
                }else {
                    Log.e(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size, (i + 1) * size));
                }

            }
        }
    }
    public static void d(String msg){
        if(BuildConfig.DEBUG){
            String method = Thread.currentThread().getStackTrace()[3].getMethodName();
            for(int i=0;i*size<msg.length();i++){
                if(msg.length()<(i+1)*size){
                    Log.d(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size));
                }else {
                    Log.d(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size, (i + 1) * size));
                }

            }
        }
    }
    public static void i(String msg){
        if(BuildConfig.DEBUG) {
            String method = Thread.currentThread().getStackTrace()[1].getMethodName();
            for(int i=0;i*size<msg.length();i++){
                if(msg.length()<(i+1)*size){
                    Log.i(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size));
                }else {
                    Log.i(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size, (i + 1) * size));
                }

            }
            
        }

    } public static void v(String msg){
        if(BuildConfig.DEBUG){
            String method = Thread.currentThread().getStackTrace()[1].getMethodName();
            for(int i=0;i*size<msg.length();i++){
                if(msg.length()<(i+1)*size){
                    Log.v(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size));
                }else {
                    Log.v(method, Thread.currentThread().getStackTrace()[3] + " " + msg.substring(i * size, (i + 1) * size));
                }

            }
            
        }
    }
    public static void defaultLog(String msg){
        if(BuildConfig.DEBUG){
            for(int i=0;i*size<msg.length();i++){
                if(msg.length()<(i+1)*size){
                    Log.e("defaultLog", msg.substring(i * size));    
                }else {
                    Log.e("defaultLog",  msg.substring(i * size, (i + 1) * size));
                }
                
            }
        }
    }
}
