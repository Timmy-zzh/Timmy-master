package com.timmy.framework.netFw.http.listener;

import org.apache.http.HttpEntity;

/**
 * Created by timmy1 on 17/4/4.
 * 网络响应接口回调
 * 1.成功－返回的参数
 * 2.失败－错误码，异常信息
 */
public interface IHttpListener {

    //成功
    void onSuccess(HttpEntity httpEntity);

//    失败
    void onFail(int stateCode);

}
