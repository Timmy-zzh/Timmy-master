package com.timmy.framework.imageLoaderFw.TimmyImageLoader.request;

import android.util.Log;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.loader.Loader;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Created by admin on 2017/6/14.
 * 请求分发器:
 * 1.从请求队列中拿到BitmapRequest请求,进行分发
 * 2.
 */
public class RequestDispatch extends Thread {

    private BlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatch(BlockingQueue<BitmapRequest> requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    @Override
    public void run() {
        try {
            //判断该线程是否终端
            while (!isInterrupted()) {
                BitmapRequest bitmapRequest = mRequestQueue.take();
                if(bitmapRequest.isCancel()){
                    continue;
                }
                /**
                 * 拿到请求中的图片Url,进行解析判断是需要网络加载
                 * 还是本地加载
                 */
                String schema = parseSchema(bitmapRequest.mImageUrl);
                Loader loader = LoaderManager.getInstance().getLoader(schema);
                loader.loadImager(bitmapRequest);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("RequestDispatch","请求分发器错误:"+e.getMessage());
        }
    }

    private String parseSchema(String mImageUrl) {
        if (mImageUrl.contains("://")){
            return mImageUrl.split("://")[0];
        }else{
            Log.i("RequestDispatch","不支持的url类型");
        }
        return null;
    }
}
