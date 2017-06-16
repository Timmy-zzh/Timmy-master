package com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

/**
 * Created by admin on 2017/6/16.
 */

public class DoubleCache implements BitmapCache {

    private MemoryCache memoryCache = new MemoryCache();
    private DiskCache diskCache;

    public DoubleCache(Context context) {
        diskCache = DiskCache.getInstance(context);
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {
        memoryCache.put(key, bitmap);
        diskCache.put(key, bitmap);
    }

    /**
     * 先从内存缓存区
     * 没有再从磁盘中取
     *
     * @param key
     * @return
     */
    @Override
    public Bitmap get(BitmapRequest key) {
        Bitmap bitmap = memoryCache.get(key);
        if (bitmap == null) {
            bitmap = diskCache.get(key);
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest key) {
        memoryCache.remove(key);
        diskCache.remove(key);
    }
}
