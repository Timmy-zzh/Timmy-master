package com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache;

import android.graphics.Bitmap;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

/**
 * Created by timmy1 on 17/6/13.
 * 图片缓存:分为
 * 1.内存缓存
 * 2.硬盘缓存
 * 3.双缓存
 * 4.没有缓存
 * 主要内容包括：增加，获取，删除
 */
public interface BitmapCache {

    /**
     * 缓存Bitmap
     * @param key
     * @param bitmap
     */
    void put(BitmapRequest key,Bitmap bitmap);

    /**
     * 通过请求,获取Bitmap
     * @param key
     * @return
     */
    Bitmap get(BitmapRequest key);

    /**
     * 从缓存中移除
     * @param key
     */
    void remove(BitmapRequest key);

}
