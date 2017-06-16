package com.timmy.framework.imageLoaderFw.TimmyImageLoader.policy;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

/**
 * Created by timmy1 on 17/6/13.
 * 逆序加载,先执行后加入到请求队列的请求
 */
public class ReversePolicy implements LoadPolicy {

    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return request2.serialNum - request1.serialNum;
    }
}
