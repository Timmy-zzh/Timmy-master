package com.timmy.rxjava.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class AppInfo implements Comparable<Object> {


    private String appName, packageName, versionName;
    private int versionCode;
    private Drawable appIcon;
    private long lastUpdateTime;
    private boolean isSystem;

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    //根据应用名称进行排序
    @Override
    public int compareTo(Object o) {
        AppInfo appInfo = (AppInfo) o;
        return getAppName().compareTo(appInfo.getAppName());
    }
}
