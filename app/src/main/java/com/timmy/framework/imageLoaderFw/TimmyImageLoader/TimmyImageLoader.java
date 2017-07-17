package com.timmy.framework.imageLoaderFw.TimmyImageLoader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.config.DisplayConfig;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.config.ImageLoaderConfig;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.RequestQueue;

/**
 * Created by admin on 2017/6/12.
 * 图片加载框架入口:
 * 初始化 请求队列，开启轮询获取加载图片的请求
 * 展示图片
 * 一个展示图片的调用方法就是一个请求，我们需要讲加载图片的请求放入到请求队列中
 * <p>
 * 1.设置ImageLoaderConfig参数
 * 2.单例模式
 */
public class TimmyImageLoader {

    public static TimmyImageLoader instance;
    public ImageLoaderConfig mLoaderConfig;
    public DisplayConfig mDisplayConfig;
    public RequestQueue mRequestQueue;

    private TimmyImageLoader() {
    }

    public static TimmyImageLoader getInstance() {
        if (instance == null) {
            synchronized (TimmyImageLoader.class) {
                if (instance == null) {
                    instance = new TimmyImageLoader();
                }
            }
        }
        return instance;
    }

    public void init(ImageLoaderConfig config) {
        mLoaderConfig = config;
        checkConfig();
        mRequestQueue = new RequestQueue(config.getThreadCount());
        //请求队列开启轮询
        mRequestQueue.start();
    }

    //检查，在初始化时，必须设置ImageLoaderConfig参数设置
    private void checkConfig() {
        if (mLoaderConfig == null) {
            //抛出异常
            throw new RuntimeException("使用前请先设置ImageLoaderConfig");
        }
        //到这一步ImageLoaderConfig对象不为空,里面有默认的加载策略和缓存策略
        mDisplayConfig = mLoaderConfig.getDisplayConfig();
    }


    public void displayImage(ImageView imageView, String url) {
        displayImage(imageView, url, null, null);
    }

    public void displayImage(ImageView imageView, String url,
                             DisplayConfig displayConfig, ImageListener listener) {
        //一条图片展示，就是一个图片请求-->放入到请求队列中
        BitmapRequest request = new BitmapRequest(imageView, url, displayConfig, listener);
        request.mDisplayConfig = displayConfig == null ? mDisplayConfig : displayConfig;

        mRequestQueue.addRequest(request);
    }

    public interface ImageListener {
        void onComplete(ImageView imageView, Bitmap bitmap, String url);
    }

    public ImageLoaderConfig getImageLoaderConfig() {
        return mLoaderConfig;
    }
}
