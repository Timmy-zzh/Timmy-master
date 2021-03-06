package com.timmy.framework.imageLoaderFw.TimmyImageLoader.policy;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

/**
 * Created by timmy1 on 17/6/13.
 * 串行加载策略FIFO
 * 可以按照队列的序列号顺序来执行
 */
public class SerialPolicy implements LoadPolicy {

    @Override
    public int compare(BitmapRequest request1, BitmapRequest request2) {
        return request1.serialNum - request2.serialNum;
    }
}
