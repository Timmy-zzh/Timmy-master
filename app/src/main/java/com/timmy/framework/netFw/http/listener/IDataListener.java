package com.timmy.framework.netFw.http.listener;

/**
 * Created by timmy1 on 17/4/4.
 * 数据处理回调接口－>主要处理IHttpService发起请求得到的网络数据
 * －>交给IHttpListener回调－>经过一番数据转换处理后,最后交给IDataListener来回调处理
 * 1.
 */
public interface IDataListener<T> {

    void onSuccess(T t);

    void onError(int state);

}
