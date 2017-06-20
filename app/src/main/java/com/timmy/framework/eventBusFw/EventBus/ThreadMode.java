package com.timmy.framework.eventBusFw.EventBus;

/**
 * Created by admin on 2017/6/20.
 * 线程模式
 */
public enum ThreadMode {

    /**
     * 发送和接收在同一个线程
     */
    PostThread,

    /**
     * 接收--在主线程
     */
    MainThread,

    /**
     * 接收-在子线程
     */
    BackgroundThread,

    /**
     * 接收--在子线程
     */
    Async;

}
