package com.timmy.framework.imageLoaderFw.TimmyImageLoader.config;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache.BitmapCache;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache.MemoryCache;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.loader.LoadPolicy;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.loader.SerialPolicy;

/**
 * Created by timmy1 on 17/6/13.
 * 加载图片设置的参数配置
 * 给这些参数设置默认值
 * 也可以使用builder模式
 */
public class ImageLoaderConfig {

    //图片缓存
    public BitmapCache mBitmapCache = new MemoryCache();

    //线程池个数
    public int mThreadCount = Runtime.getRuntime().availableProcessors() + 1;

    //显示参数设置
    public DisplayConfig mDisplayConfig = new DisplayConfig();

    //加载策略
    public LoadPolicy mLoadPolicy = new SerialPolicy();


    public ImageLoaderConfig setThreadCount(int threadCount) {
        mThreadCount = Math.max(1, threadCount);
        return this;
    }

    public ImageLoaderConfig setBitmapCache(BitmapCache bitmapCache) {
        mBitmapCache = bitmapCache;
        return this;
    }

    public ImageLoaderConfig setLoadPolicy(LoadPolicy loadPolicy) {
        mLoadPolicy = loadPolicy;
        return this;
    }

    public ImageLoaderConfig setLoadingPlaceholder(int loadingRes) {
        mDisplayConfig.loadingImg = loadingRes;
        return this;
    }

    public ImageLoaderConfig setErrorPlaceholder(int errorRes) {
        mDisplayConfig.errorImg = errorRes;
        return this;
    }
}
