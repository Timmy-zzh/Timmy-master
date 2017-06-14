package com.timmy.framework.imageLoaderFw.TimmyImageLoader.request;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by timmy1 on 17/6/13.
 * 请求队列
 * 1.使用AtomicInteger设置请求序列生成器
 * 2.使用BlockingQueue设置为请求队列
 *
 * 请求队列保存了所有的请求,start()方法不断轮询从请求队列中拿到单个的BitmapRequest请求
 * 开启三个线程作为分发器,分发器从请求队列中拿到BitmapRequest请求后
 * 进行分发:判断是加载网络图片还是本地图片
 *
 */
public class RequestQueue {

    //请求队列
    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<BitmapRequest>();

    //默认线程数
    public static int DEFAULT_CORE_NUMS = Runtime.getRuntime().availableProcessors()+1;

    //线程池个数
    private  int mThreadCount;

    //为请求设置序列,生成器
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    //分发器
    private RequestDispatch[] mRequestDispatchs;


    public RequestQueue() {
        this(DEFAULT_CORE_NUMS);
    }

    public RequestQueue(int threadCount) {
        mThreadCount = threadCount;
    }

    /**
     * 线程,先中断,再开启
     */
    public void start() {
        stop();
        startDispatch();
    }

    private void stop() {
        if (mRequestDispatchs != null && mRequestDispatchs.length>0){
            int length = mRequestDispatchs.length;
            for (int i = 0; i < length; i++) {
                mRequestDispatchs[i].interrupt();
            }
        }
    }

    private void startDispatch() {
        mRequestDispatchs = new RequestDispatch[mThreadCount];
        //开启
        int length = mRequestDispatchs.length;
        for (int i = 0; i < length; i++) {
            mRequestDispatchs[i] = new RequestDispatch(mRequestQueue);
            mRequestDispatchs[i].start();
        }
    }

    /**
     * 往请求队列中添加请求
     * @param request
     */
    public void addRequest(BitmapRequest request) {
        if (!mRequestQueue.contains(request)){
            request.serialNum = this.generateSerialNumber();
            mRequestQueue.add(request);
        }else{
            Log.d("RequestQueue","请求队列中已有该请求");
        }
    }

    private int generateSerialNumber() {
        return mSerialNumGenerator.incrementAndGet();
    }
}
