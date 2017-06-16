package com.timmy.framework.imageLoaderFw.TimmyImageLoader.policy;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

/**
 * Created by timmy1 on 17/6/14.
 * 加载策略：
 * 决定BitmapRequest图片加载请求在请求队列中按照一个什么规则来加载你的请求.
 * 默认是SerialPolicy串行加载FIFO还是 逆序加载 ReversePolicy
 *
 */
public interface LoadPolicy {
    int compare(BitmapRequest request1,BitmapRequest request2);
}
