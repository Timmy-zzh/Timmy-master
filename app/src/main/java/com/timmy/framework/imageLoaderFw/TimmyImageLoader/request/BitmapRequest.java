package com.timmy.framework.imageLoaderFw.TimmyImageLoader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.TimmyImageLoader;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.config.DisplayConfig;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.policy.LoadPolicy;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.utils.MD5Utils;

import java.lang.ref.WeakReference;

/**
 * Created by timmy1 on 17/6/13.
 * 图片加载请求:只是做了一个封装,减少参数的个数
 * 存储了ImageView,图片加载url,展示参数,回调接口
 * 图片url会设置为ImageView的tag,防止图片错位
 * 实现Compare接口,在请求队列中根据他的序列号进行排序
 */
public class BitmapRequest implements Comparable<BitmapRequest> {

    public String mImageUrlMd5;
    public DisplayConfig mDisplayConfig;
    public String mImageUrl;
    public TimmyImageLoader.ImageListener mImageListener;
    public WeakReference<ImageView> mImageViewRef;
    public int serialNum;//请求序列号
    public LoadPolicy loadPolicy = TimmyImageLoader.getInstance().getImageLoaderConfig().mLoadPolicy;

    public BitmapRequest(ImageView imageView, String url, DisplayConfig displayConfig, TimmyImageLoader.ImageListener listener) {
        mImageUrl = url;
        imageView.setTag(url);
        mImageViewRef = new WeakReference<ImageView>(imageView);
        mDisplayConfig = displayConfig;
        mImageListener = listener;
        //因为在做磁盘缓存时,图片的路径不能使用/反斜杠,可以处理为16位的md5字符串
        mImageUrlMd5 = MD5Utils.toMD5(url);
    }

    public ImageView getImageView(){
        return  mImageViewRef.get();
    }

    //请求是否取消
    public boolean isCancel() {
        return false;
    }

    /**
     * 使用代理方式,图片请求在请求队列中什么时候加载交给LoadPolicy加载策略去决定
     * @return
     */
    @Override
    public int compareTo(@NonNull BitmapRequest another) {
        return loadPolicy.compare(this, another);
    }
}
