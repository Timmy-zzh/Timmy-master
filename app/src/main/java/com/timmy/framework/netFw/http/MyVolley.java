package com.timmy.framework.netFw.http;

import com.timmy.framework.netFw.http.listener.IDataListener;
import com.timmy.framework.netFw.http.listener.IHttpListener;
import com.timmy.framework.netFw.http.listener.IHttpService;

import java.util.concurrent.FutureTask;

/**
 * Created by timmy1 on 17/4/4.
 * 框架入口
 * 发起请求：
 * 1.设置参数：url，传参，响应数据的参数对象类型，回调接口
 * 2.封装参数requestholder
 * 3.一个请求就是一个任务－》开启新的任务
 * 4.使用线程池开启任务队列
 */
public class MyVolley {

    public static <M,T>  void sendRequest(M requestInf, String url,
                                          Class<T> responseClass, IDataListener dataListener){
        //1.将所有参数封装到RequestHolder
        RequestHolder<M> requestHolder = new RequestHolder();
        requestHolder.setUrl(url);
        requestHolder.setRequestInfo(requestInf);
        IHttpService httpService = new JsonHttpService();

        requestHolder.setHttpService(httpService);
        IHttpListener httpListener = new JsonHttpListener(responseClass,dataListener);
        requestHolder.setHttpListener(httpListener);
        //2.新建HttpTask
        HttpTask<M> httpTask = new HttpTask<>(requestHolder);
        //3.拿到线程池实例，执行
        try {
            ThreadPoolManager.getInstance().execute(new FutureTask<Object>(httpTask,null));
        } catch (InterruptedException e) {
            e.printStackTrace();
            dataListener.onError(2);
        }
    }

}
