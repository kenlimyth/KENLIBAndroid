package com.kenlib.http.retrofit_rxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import com.kenlib.android.R;

/**
 * rxjava 用法，通常用法
 */
public class RxjavaDemo extends AppCompatActivity {

    private static final String TAG = "Rxjava";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rxjava_demo);
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 通常用法
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        Log.d(TAG, "subscribe11=" + e + " currentThread=" + Thread.currentThread().getName());
                        e.onNext("t");
                    }
                }).map(new Function<String, Integer>() {

                    @Override
                    public Integer apply(String s) throws Exception {
                        Log.d(TAG, "map  currentThread=" + Thread.currentThread().getName());
                        return 10;
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "onSubscribe=" + d + " currentThread=" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onNext(Integer value) {
                                Log.d(TAG, "onNext=" + value + " currentThread=" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                Log.d(TAG, "------------- currentThread=" + Thread.currentThread().getName());


                // just数组，  map类型转换
//                Observable.just("1", "2", "3").map(new Function<String, Integer>() {
//                    @Override
//                    public Integer apply(String s) throws Exception {
//                        Log.d(TAG, "map=" + s + " currentThread=" + Thread.currentThread().getName());
//                        return Integer.valueOf(s) * 10;
//                    }
//                }).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<Integer>() {
//                            @Override
//                            public void accept(Integer integer) throws Exception {
//                                Log.d(TAG, "Consumer=" + integer + " currentThread=" + Thread.currentThread().getName());
//                            }
//                        });


                // filter过滤
//                Observable.just("1", "2", "3").filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        return !s.equals("2");
//                    }
//                }).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<String>() {
//                            @Override
//                            public void accept(String integer) throws Exception {
//                                Log.d(TAG, "Consumer=" + integer + " currentThread=" + Thread.currentThread().getName());
//                            }
//                        });


                // interval定时周期执行
//                Observable.interval(2, TimeUnit.SECONDS)
//
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Consumer<Long>() {
//                            @Override
//                            public void accept(Long aLong) throws Exception {
//                                Log.d(TAG, "Consumer=" + aLong + " currentThread=" + Thread.currentThread().getName());
//                            }
//                        });

            }
        });
    }

}
