package com.timmy.framework.netFw.http.listener;

/**
 * Created by timmy1 on 17/4/4.
 * 主要封装了发起网络请求需要设置的参数
 * 1.设置url
 * 2.设置请求参数
 * 3.设置请求回调接口 ihttplistener
 * 4.执行网络
 */
public interface IHttpService {

    void setUrl(String url);

    void setRequestData(byte[] requestData);

    void setHttpListener(IHttpListener httpListener);

    void execute();

}
