package com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache;

import android.graphics.Bitmap;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

/**
 * Created by timmy1 on 17/6/13.
 * 内存缓存
 */
public class MemoryCache implements BitmapCache {

    @Override
    public Bitmap get(BitmapRequest key) {
        return null;
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {

    }

    @Override
    public void remove(BitmapRequest key) {

    }
}
