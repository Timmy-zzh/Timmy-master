package com.timmy.framework.mvp.model;

/**
 * Created by admin on 2017/7/19.
 * Model层的接口,主要工作是校验登录判断,校验结果使用接口进行回调
 */
public interface ILoginModel<T> {

    void login(T t, OnLoginCallBack callBack);

    public interface OnLoginCallBack {

        void onSuccess();

        void onFail();

        void onError(Throwable t);
    }

}
