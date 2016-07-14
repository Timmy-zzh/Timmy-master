package com.timmy.rxjava.model;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class AppInfo implements Comparable<Object> {


    private String name, icon;

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private long lastUpdateTime;

    public AppInfo(String name, String icon, long lastUpdateTime) {
        this.name = name;
        this.icon = icon;
        this.lastUpdateTime = lastUpdateTime;
    }

    //根据应用名称进行排序
    @Override
    public int compareTo(Object o) {
        AppInfo appInfo = (AppInfo) o;
        return getName().compareTo(appInfo.getName());
    }
}
