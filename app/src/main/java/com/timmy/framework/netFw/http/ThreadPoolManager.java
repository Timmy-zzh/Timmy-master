package com.timmy.framework.netFw.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by timmy1 on 17/4/4.
 * 线程池：单例模式
 * 将所有的任务放到线程池中来处理
 */
public class ThreadPoolManager {

    private String TAG = "ThreadPoolManager";
    private static ThreadPoolManager instance = new ThreadPoolManager();
    private final static int corePoolSize = 4;//cpu核数
    private final static int maximumPoolSize = 10;//线程池最大线程个数
    private final static long keepAliveTime = 10; //线程保存的时间
    private final ThreadPoolExecutor threadPoolExecutor;
    private LinkedBlockingDeque<Future<?>> taskQueue = new LinkedBlockingDeque<>();


    //构造函数中维护线程池，并且开启线程池轮训
    private ThreadPoolManager() {
        threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                rejectedExecutionHandler
        );
        //执行
        threadPoolExecutor.execute(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 不断的从线程池中取出数据
            while (true) {
                FutureTask futureTask = null;
                try {
                    /**
                     * 阻塞式函数
                     */
                    Log.i(TAG, "等待队列： " + taskQueue.size());
                    futureTask = (FutureTask) taskQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureTask != null) {
                    //正在执行的线程
                    threadPoolExecutor.execute(futureTask);
                }
                Log.i(TAG, "线程池大小： " + threadPoolExecutor.getPoolSize());
            }
        }
    };

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    /**
     * 往池子里面丢任务
     * @param futureTask
     * @param <T>
     */
    public <T> void  execute(FutureTask<T> futureTask) throws InterruptedException {
        taskQueue.put(futureTask);
    }

    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            try {
                taskQueue.put(new FutureTask<Object>(runnable,null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

}
