package com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

/**
 * Created by timmy1 on 17/6/13.
 * 内存缓存:使用Lru算法==v4包中的LruCahe
 */
public class MemoryCache implements BitmapCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
        //获取可用内存
        int maxMemeory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //缓存内存大小
        int cacheSize = maxMemeory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //返回一个bitmap的内存
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        return mMemoryCache.get(key.mImageUrl);
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {
        mMemoryCache.put(key.mImageUrl, bitmap);
    }

    @Override
    public void remove(BitmapRequest key) {
        mMemoryCache.remove(key.mImageUrl);
    }
}
