package com.timmy.framework.netFw.utils;

import android.accounts.NetworkErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by timmy1 on 17/4/2.
 * 网络请求工具类--同步实现方式
 */
public class NetUtils {

    private static HttpURLConnection connection;

    /**
     * post请求
     */
    public static String  post(String url, String content) {

        try {
            //创建URL对象
            URL mUrl = new URL(url);
            //获取httpurlconnection对象
            connection = (HttpURLConnection) mUrl.openConnection();
            //设置请求方法未post
            connection.setRequestMethod("POST");
            //设置读取超时未5秒
            connection.setReadTimeout(5000);
            //设置连接超时为10秒
            connection.setConnectTimeout(10000);
            //允许向服务器输出内容
            connection.setDoOutput(true);

            //向服务器写数据
            String data = content;
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();

            //连接
            connection.connect();
            //获取响应信息－响应头
            int responseCode = connection.getResponseCode();
            if (responseCode == 200){
                //获取响应数据
                InputStream inputStream = connection.getInputStream();
                String result = getStringFromInputStream(inputStream);

                return result;
            }else{
                throw new NetworkErrorException("请求失败，响应码为:"+responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();//关闭连接
        }
        return null;
    }


    /**
     * get请求
     */
    public static String  get(String url) {

        try {
            //创建URL对象
            URL mUrl = new URL(url);
            //获取httpurlconnection对象
            connection = (HttpURLConnection) mUrl.openConnection();
            //设置请求方法未post
            connection.setRequestMethod("GET");
            //设置读取超时未5秒
            connection.setReadTimeout(5000);
            //设置连接超时为10秒
            connection.setConnectTimeout(10000);

            //连接
            connection.connect();
            //获取响应信息－响应头
            int responseCode = connection.getResponseCode();
            if (responseCode == 200){
                //获取响应数据
                InputStream inputStream = connection.getInputStream();
                String result = getStringFromInputStream(inputStream);

                return result;
            }else{
                throw new NetworkErrorException("请求失败，响应码为:"+responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();//关闭连接
        }
        return null;
    }

    /**
     * 从输入流中读取数据string
     * @param inputStream
     * @return
     */
    private static String getStringFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos  = new ByteArrayOutputStream();
        //读取－写入－返回数据
        byte[] buffer = new byte[1024];
        int len = -1;
        while ( (len = inputStream.read(buffer)) !=-1){
            //写入baos中
            baos.write(buffer,0,len);
        }
        inputStream.close();
        String result = baos.toString();
        baos.close();
        return result;
    }
}
