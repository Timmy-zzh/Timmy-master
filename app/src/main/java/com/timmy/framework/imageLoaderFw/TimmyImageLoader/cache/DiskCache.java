package com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.disk.DiskLruCache;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.disk.IOUtil;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by timmy1 on 17/6/14.
 * 磁盘缓存
 * 1.单例模式
 * 2.
 */
public class DiskCache implements BitmapCache {

    public static DiskCache mDiskCache;

    //jackwharton的杰作
    private DiskLruCache mDiskLruCache;
    private String cacheDir = "imageloadcache";


    private DiskCache(Context context) {
        initDishCache(context);
    }

    public static DiskCache getInstance(Context context) {
        if (mDiskCache == null) {
            synchronized (DiskCache.class) {
                if (mDiskCache == null) {
                    mDiskCache = new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }

    /**
     * 根据上下文去获取缓存目录
     *
     * @param context
     */
    private void initDishCache(Context context) {
        File directory = getDiskCache(context, cacheDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        /**
         *  实例化DishLruCache
         *  @param directory a writable directory
         * @param appVersion the version of the application
         * @param valueCount the number of values per cache entry. Must be positive.
         * @param maxSize the maximum number of bytes this cache should use to store
         */
        try {
            mDiskLruCache = DiskLruCache.open(directory, 1, 1, 50 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到缓存的目录  android/data/data/packagename/cache/imageloadcache
     *
     * @param context
     * @param cacheDir
     * @return
     */
    private File getDiskCache(Context context, String cacheDir) {
        return new File(Environment.getExternalStorageDirectory(), cacheDir);
    }

    @Override
    public Bitmap get(BitmapRequest key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key.mImageUrlMd5);
            if (snapshot != null){
                InputStream inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void put(BitmapRequest key, Bitmap bitmap) {
        DiskLruCache.Editor editor;
        OutputStream os;
        try {
            editor = mDiskLruCache.edit(key.mImageUrlMd5);
            os = editor.newOutputStream(0);

            if (persistBitmap2Disk(bitmap, os)) {
                editor.commit();
            }else{
                editor.abort();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将bitmap转化为io流
     *
     * @param bitmap
     * @param os
     * @return
     */
    private boolean persistBitmap2Disk(Bitmap bitmap, OutputStream os) {
        BufferedOutputStream bos = new BufferedOutputStream(os);

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(bos);
        }
        return true;
    }

    @Override
    public void remove(BitmapRequest key) {
        try {
            mDiskLruCache.remove(key.mImageUrlMd5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
