package com.kenlib.http;

import com.kenlib.util.Util;

import android.os.Handler;

/**
 * HTTP线程处理封装，也适用于任何耗时的操作
 * 自己封装的HTTP
 */
public class HttpThreadHandler extends Thread {

    Handler uiHandler;
    Huidiao huidiao;

    public HttpThreadHandler(Handler uiHandler, Huidiao huidiao) {
        // TODO Auto-generated constructor stub
        this.uiHandler = uiHandler;
        this.huidiao = huidiao;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        // super.run();
        try {
            System.out.println("dododo thread id "
                    + Thread.currentThread().getId());

            final String jsonObject = huidiao.postOnChildThread();

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    huidiao.postFinishOnUIThread(jsonObject);

                    System.out.println("Runnable thread id "
                            + Thread.currentThread().getId());
                }
            };
            uiHandler.post(runnable);
        } catch (Exception e) {
            // TODO: handle exception
            Util.showLogDebug("ex=============" + e.toString());
        }

    }

    public interface Huidiao {

        String postOnChildThread();

        void postFinishOnUIThread(String jsonString);
    }
}