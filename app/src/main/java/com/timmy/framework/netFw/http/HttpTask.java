package com.timmy.framework.netFw.http;

import com.alibaba.fastjson.JSON;
import com.timmy.framework.netFw.http.listener.IHttpListener;
import com.timmy.framework.netFw.http.listener.IHttpService;

/**
 * Created by timmy1 on 17/4/4.
 * 一个请求就是一个任务，就需要开启一个子线程去执行
 * 将封装好的requestholder构造函数传入进来
 */
public class HttpTask<M> implements Runnable {

    private final IHttpService httpService;//请求执行类

    public HttpTask(RequestHolder<M> requestHolder){
        httpService = requestHolder.getHttpService();
        httpService.setHttpListener(requestHolder.getHttpListener());
        httpService.setUrl(requestHolder.getUrl());

        M requestInfo = requestHolder.getRequestInfo();
        String jsonString = JSON.toJSONString(requestInfo);
        httpService.setRequestData(jsonString.getBytes());
    }

    @Override
    public void run() {
        httpService.execute();
    }
}
