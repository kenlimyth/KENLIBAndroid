package com.kenlib.sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * //Android线程
 * //1.Handler Thread
 * //2.Async Task
 * //3.ThreadPoolExecutor
 * <p>
 *
 * Android四种线程池的基本使用,
 * 底层都是 ThreadPoolExecutor
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                //异步执行任务
                System.out.println("running" + Thread.currentThread().getName());
            }
        };

        // FixedThreadPool 是一种线程数固定的线程池。当线程处于空闲状态时，它们并不会被回收，除非线程池被关闭了。
//        ExecutorService pool1 = Executors.newFixedThreadPool(3);
//        for (int i = 0; i <10 ; i++) {
//            pool1.execute(task);
//
//        }

        // 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
//        ExecutorService pool2 = Executors.newCachedThreadPool();
//        for (int i = 0; i <10 ; i++) {
//            pool2.execute(task);
//
//        }


        //创建一个定长线程池，支持定时及周期性任务执行
//        ScheduledExecutorService pool3 = Executors.newScheduledThreadPool(4);
//        pool3.schedule(task, 1000, TimeUnit.MILLISECONDS);//1000m后执行task
//        pool3.scheduleAtFixedRate(task, 100, 1000, TimeUnit.MILLISECONDS);//延迟100ms后每隔1000ms执行一次task


        //创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
        ExecutorService pool4 = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            pool4.execute(task);

        }


    }


}
