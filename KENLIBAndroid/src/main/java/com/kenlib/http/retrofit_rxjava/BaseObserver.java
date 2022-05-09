package com.kenlib.http.retrofit_rxjava;


import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Observer，rxjava封装
 */
public abstract class BaseObserver<T> implements Observer<T> {
    protected Context mContext;

    public BaseObserver() {

    }

    public BaseObserver(Context cxt) {
        this.mContext = cxt;
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(T tBaseEntity) {
        onRequestEnd();
        onSuccess(tBaseEntity);
    }

    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        try {

            onFailure(e.getMessage());

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 返回成功
     *
     * @param tBaseEntity
     */
    protected abstract void onSuccess(T tBaseEntity);

    /**
     * 返回失败
     *
     * @param errorMessage
     */
    protected abstract void onFailure(String errorMessage);

    /**
     * 请求开始
     */
    protected void onRequestStart() {
        showProgressDialog();
    }


    /**
     * 请求结束
     */
    protected void onRequestEnd() {
        closeProgressDialog();
    }

    /**
     * 加载弹窗
     */
    public void showProgressDialog() {

    }

    /**
     * 关闭加载弹窗
     */
    public void closeProgressDialog() {

    }

}
