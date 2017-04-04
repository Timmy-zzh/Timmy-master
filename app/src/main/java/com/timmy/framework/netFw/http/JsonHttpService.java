package com.timmy.framework.netFw.http;

import com.timmy.framework.netFw.http.listener.IHttpListener;
import com.timmy.framework.netFw.http.listener.IHttpService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import java.io.IOException;

/**
 * Created by timmy1 on 17/4/4.
 * 发起网络请求
 * 得到网络响应的数据
 */
public class JsonHttpService implements IHttpService {

    private String url;
    private byte[] requestData;
    private IHttpListener httpListener;

    private HttpClient httpClient = new DefaultHttpClient();
    private HttpResponseHandler httpResponseHandler = new HttpResponseHandler();
    private HttpUriRequest httpPost;
    private HttpGet httpGet;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    @Override
    public void execute() {
        //使用HttpClient发起网络请求

        //发送post请求
//        httpPost = new HttpPost(url);
        httpGet = new HttpGet(url);
        //设置请求参数--httppost方法调用不了setentity方法
//        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestData);
//        httpPost.setEntity()
        try {
//            HttpResponse httpResponse = httpClient.execute(httpPost);
            httpClient.execute(httpGet, httpResponseHandler);
        } catch (IOException e) {
            e.printStackTrace();
            httpListener.onFail(1);
        }
    }

    /**
     * 网络请求后返回的实现类
     */
    private class HttpResponseHandler extends BasicResponseHandler {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException {
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                httpListener.onSuccess(response.getEntity());
            } else {
                httpListener.onFail(code);
            }
            return null;
        }
    }

}
