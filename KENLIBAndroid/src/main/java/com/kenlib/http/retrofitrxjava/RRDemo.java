package com.kenlib.http.retrofitrxjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.kenlib.android.R;
import com.kenlib.http.retrofitrxjava.dto.BaseResponseDto;
import com.kenlib.http.retrofitrxjava.dto.TestDto;
import com.kenlib.util.Util;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit+RxJava的封装,访问网路， 用法展示
 */
public class RRDemo extends AppCompatActivity {

    private static final String TAG = "Rxjava";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rr_demo);

        findViewById(R.id.btn_test).setOnClickListener(v -> {

            RetrofitHandler.getInstance().getAPIService().getTest1()
                    .compose(RxTransformerHandler.observableIO2Main())
                    .subscribe(new BaseObserver<BaseResponseDto<TestDto>>() {
                        @Override
                        protected void onSuccess(BaseResponseDto<TestDto> tBaseEntity) {
                        }

                        @Override
                        protected void onFailure(String errorMessage) {
                            Log.d(TAG, "请求失败" + errorMessage);
                        }


                    });



        });
    }


    /**
     * 一般写写法
     */
    void test() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.124.2:3008") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();


        // 步骤5：创建 网络请求接口 的实例
        ObservableAPI request = RetrofitHandler.getInstance().getAPIService();

//         步骤6：采用Observable<...>形式 对 网络请求 进行封装
        Observable<TestDto> observable = request.getTest();

//         步骤7：发送网络请求
        observable.subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 回到主线程 处理请求结果
                .subscribe(new Observer<TestDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(TestDto result) {
                        // 步骤8：对返回的数据进行处理
                        result.show();
                        Util.showToast(RRDemo.this, "Ok");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "请求失败" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "请求成功");
                    }
                });


    }


}
