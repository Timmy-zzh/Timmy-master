package com.timmy.framework.netFw.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.timmy.framework.netFw.http.listener.IDataListener;
import com.timmy.framework.netFw.http.listener.IHttpListener;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by timmy1 on 17/4/4.
 * 根据HttpService网络访问获取到的数据，
 * 1.通过接口回调到该类来进行处理
 * 2.并且进行线程切换
 * 3.最后得到想要的响应对象类型的实例对象，并交给IDataListener接口回调处理
 * （需要用到json解析）
 */
public class JsonHttpListener<T> implements IHttpListener {

    private Class<T> responseClass;
    private IDataListener<T> dataListener;
    /**
     * 获取到主线程，通过handle切换
     *
     * @param httpEntity
     */
    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class<T> responseClass, IDataListener<T> dataListener) {
        this.responseClass = responseClass;
        this.dataListener = dataListener;
    }

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        //根据返回的httpEntitys数据获取输入流->解析得到需要返回的对象实例
        try {
            InputStream inputStream = httpEntity.getContent();
            //1.根据输入流获取json数据
            String content = getContent(inputStream);
            //2.Josn解析
            final T response = JSON.parseObject(content, responseClass);
            //3.线程切换
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onSuccess(response);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            dataListener.onError(2);//解析出错
        }
    }

    private String getContent(InputStream inputStream) {
        String content = null;

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            content = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("getContent出错:" + e.toString());
            dataListener.onError(2);
        } finally {
            //关闭流
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                dataListener.onError(2);
            }
        }
        return content;
    }

    @Override
    public void onFail(int stateCode) {
        dataListener.onError(stateCode);
    }
}
