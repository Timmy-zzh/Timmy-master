package com.timmy.framework.netFw.http;

import com.timmy.framework.netFw.http.listener.IHttpListener;
import com.timmy.framework.netFw.http.listener.IHttpService;

/**
 * Created by timmy1 on 17/4/4.
 * 网络请求封装类
 */
public class RequestHolder<M> {

    //执行网络请求
    private IHttpService httpService;

//    网络访问响应接口
    private IHttpListener httpListener;

    //请求参数
    private M requestInfo;

    //url
    private String url;


    public IHttpService getHttpService() {
        return httpService;
    }

    public void setHttpService(IHttpService httpService) {
        this.httpService = httpService;
    }

    public IHttpListener getHttpListener() {
        return httpListener;
    }

    public void setHttpListener(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    public M getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(M requestInfo) {
        this.requestInfo = requestInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
