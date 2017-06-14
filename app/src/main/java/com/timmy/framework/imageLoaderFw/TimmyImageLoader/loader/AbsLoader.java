package com.timmy.framework.imageLoaderFw.TimmyImageLoader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.TimmyImageLoader;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache.BitmapCache;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.config.DisplayConfig;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

import java.lang.ref.WeakReference;

/**
 * Created by timmy1 on 17/6/14.
 * 在这个类中，需要实现的功能是
 * 1.从缓存中获取图片
 * 2.缓存中没有就需要去获取（分为网络获取和本地获取）
 * 3.将获取到的图片进行缓存
 * 4.最后是在主线程中进行展示
 */
public abstract class AbsLoader implements Loader {

    BitmapCache bitmapCache = TimmyImageLoader.getInstance().getImageLoaderConfig().mBitmapCache;
    DisplayConfig displayConfig = TimmyImageLoader.getInstance().getImageLoaderConfig().mDisplayConfig;

    @Override
    public void loadImager(BitmapRequest bitmapRequest) {

        Bitmap bitmap = bitmapCache.get(bitmapRequest);

        if (bitmap == null) {
            //加载过程中显示加载中的图片
            showLoadingPlaceholder(bitmapRequest);
            //加载图片
            bitmap = onLoadImage(bitmapRequest);
            //缓存图片
            cacheBitmap(bitmapRequest, bitmap);
        }
        //UI线程进行展示
        displayOnUiThread(bitmapRequest, bitmap);
    }

    /**
     * 显示加载中的图片
     *
     * @param bitmapRequest
     */
    private void showLoadingPlaceholder(BitmapRequest bitmapRequest) {
        if (hasSetLoadingPlaceholder(bitmapRequest)) {
            WeakReference<ImageView> mImageViewRef = bitmapRequest.mImageViewRef;
            if (mImageViewRef.get() != null) {
                final ImageView imageView = mImageViewRef.get();
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(displayConfig.loadingImg);
                    }
                });
            }
        }
    }


    private void cacheBitmap(BitmapRequest bitmapRequest, Bitmap bitmap) {
        if (bitmapRequest != null && bitmap != null) {
            bitmapCache.put(bitmapRequest, bitmap);
        }
    }

    private void displayOnUiThread(BitmapRequest bitmapRequest, final Bitmap bitmap) {
        WeakReference<ImageView> mImageViewRef = bitmapRequest.mImageViewRef;
        if (mImageViewRef.get() != null) {
            final ImageView imageView = mImageViewRef.get();
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    private boolean hasSetLoadingPlaceholder(BitmapRequest bitmapRequest) {
        return bitmapRequest.mDisplayConfig.loadingImg > 0;
    }

    /**
     * 加载器：模版方法模式：不同的url使用不同的加载方式
     * 交给子类自己具体处理
     *
     * @param bitmapRequest
     */
    protected abstract Bitmap onLoadImage(BitmapRequest bitmapRequest);

}
