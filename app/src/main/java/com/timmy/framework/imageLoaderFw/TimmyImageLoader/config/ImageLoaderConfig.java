package com.timmy.framework.imageLoaderFw.TimmyImageLoader.config;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache.BitmapCache;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache.MemoryCache;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.policy.LoadPolicy;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.policy.SerialPolicy;

/**
 * Created by timmy1 on 17/6/13.
 * 加载图片设置的参数配置
 * 给这些参数设置默认值
 * 使用builder模式
 */
public class ImageLoaderConfig {

    //图片缓存
    private BitmapCache mBitmapCache = new MemoryCache();

    //线程池个数
    private int mThreadCount = Runtime.getRuntime().availableProcessors() + 1;

    //显示参数设置
    private DisplayConfig mDisplayConfig = new DisplayConfig();

    //加载策略
    private LoadPolicy mLoadPolicy = new SerialPolicy();

    private ImageLoaderConfig(){}


    public static class Builder {

        //图片缓存
        private BitmapCache mBitmapCache = new MemoryCache();

        //线程池个数
        private int mThreadCount = Runtime.getRuntime().availableProcessors() + 1;

        //显示参数设置
        private DisplayConfig mDisplayConfig = new DisplayConfig();

        //加载策略
        private LoadPolicy mLoadPolicy = new SerialPolicy();

        public Builder setThreadCount(int threadCount) {
            mThreadCount = Math.max(1, threadCount);
            return this;
        }

        public Builder setBitmapCache(BitmapCache bitmapCache) {
            mBitmapCache = bitmapCache;
            return this;
        }

        public Builder setLoadPolicy(LoadPolicy loadPolicy) {
            mLoadPolicy = loadPolicy;
            return this;
        }

        public Builder setLoadingPlaceholder(int loadingRes) {
            mDisplayConfig.loadingImg = loadingRes;
            return this;
        }

        public Builder setErrorPlaceholder(int errorRes) {
            mDisplayConfig.errorImg = errorRes;
            return this;
        }

        public ImageLoaderConfig create(){
            ImageLoaderConfig imageLoaderConfig = new ImageLoaderConfig();
            applyConfig(imageLoaderConfig);
            return imageLoaderConfig;
        }

        private void applyConfig(ImageLoaderConfig loaderConfig) {
            loaderConfig.mThreadCount = this.mThreadCount;
            loaderConfig.mDisplayConfig =this.mDisplayConfig;
            loaderConfig.mLoadPolicy = this.mLoadPolicy;
            loaderConfig.mBitmapCache = this.mBitmapCache;
        }
    }

}
