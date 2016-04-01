package com.timmy;

import android.app.Application;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class TimmyApp extends Application {

    public static final String HEADER_DEALER_TOKEN = "token";
    public static final String USER_NAME = "username";
    public static final String USER_PASS = "userpass";
    public static final String USER_HEADIMG = "userheadimg";

    private static final String PREFS_NAME = "TIMMY_FILE";// 文件名
    public static TimmyApp app;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

    }



    public static TimmyApp getInstance() {
        if (app != null) {
            return app;
        } else {
            return null;
        }

    }

    public void saveSharedPreferences(String key, String value) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * 用户之前登录过,则将用户的信息保存在本地sp文件中
     *
     * @return
     */
    public String getUserToken() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        return settings.getString(HEADER_DEALER_TOKEN, "");
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public String getUserName() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(USER_NAME, "");
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getUserToken());
//        return true;
    }
}
