package com.kenlib.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.internal.Util;

/**
 * 线程池
 */
public class AppExecutors {

    //线程实例
    private static AppExecutors mInstance = null;

    //线程池
    private static ExecutorService pool = null;

    //线程池的核心线程数
    private static final int THREADS = 20;
    //最大的线程数， 包括核心线程， 也包括非核心线程， 在线程数达到这个值后，新来的任务将会被阻塞.
    private static final int maximumPoolSize = Integer.MAX_VALUE;
    //超时的时间， 闲置的非核心线程超过这个时长，讲会被销毁回收，设置 0 表示没有超时机制
    private static final int keepAliveTime = 10;
    //超时时间的时间单位
    private static final TimeUnit unit = TimeUnit.SECONDS;

    private AppExecutors() {
    }

    /**
     * 获取线程实例
     *
     * @return
     */
    public static AppExecutors getThreadPool() {
        if (mInstance == null) {
            synchronized (AppExecutors.class) {
                if (mInstance == null) {
                    mInstance = new AppExecutors();
                    pool = new ThreadPoolExecutor(THREADS, maximumPoolSize, keepAliveTime, unit,
                            new LinkedBlockingQueue<Runnable>(), Util.threadFactory("KEN Dispatcher", false),
                            new ThreadPoolExecutor.DiscardOldestPolicy());
                }
            }
        }
        return mInstance;
    }

    /**
     * 提交任务执行
     *
     * @param r
     */
    public void execute(Runnable r) {
        pool.execute(r);
    }

    /**
     * 关闭线程
     */
    public void shutDown() {
        if (pool == null || pool.isShutdown()) {
            return;
        }
        try {
            pool.shutdown();
            if (!pool.awaitTermination(500, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
            pool = null;
            mInstance = null;
        } catch (Exception e) {
            e.printStackTrace();
            pool.shutdownNow();
        }
    }
}





