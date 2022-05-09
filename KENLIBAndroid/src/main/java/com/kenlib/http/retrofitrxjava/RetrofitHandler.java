package com.kenlib.http.retrofitrxjava;

import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit的封装，Retrofit+Rxjava+okhttp
 * <p>
 * 调用方法：
 * 见 RRDemo
 */
public class RetrofitHandler {
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    private static RetrofitHandler mRetrofitHandler;
    private static ObservableAPI mObservableAPI;
    private static String baseUrl;

    private RetrofitHandler() {
        initRetrofit();
    }

    /**
     * 单例
     *
     * @return
     */
    public static RetrofitHandler getInstance() {
        baseUrl = HttpConfig.getBaseUrl();
        if (mRetrofitHandler == null) {
            synchronized (RetrofitHandler.class) {
                if (mRetrofitHandler == null) {
                    mRetrofitHandler = new RetrofitHandler();
                }
            }
        }
        return mRetrofitHandler;
    }


    /**
     * 获取 Retrofit
     */
    private void initRetrofit() {
        initOkHttpClient();

        //Gson自定义 解析null替换为空字符串
        Gson gson = new GsonHandler().getGson();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .client(mOkHttpClient)
                .build();
        mObservableAPI = mRetrofit.create(ObservableAPI.class);
    }

    /**
     * 单例模式获取 OkHttpClient
     */
    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitHandler.class) {
                if (mOkHttpClient == null) {
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(HttpConfig.DIR_CACHE_FILE, "HttpCache"),
                            1024 * 1024 * 100);
                    mOkHttpClient = new OkHttpClient.Builder()
                            //设置连接超时时间
                            .connectTimeout(HttpConfig.HTTP_TIME_OUT_TIME, TimeUnit.SECONDS)
                            //设置读取超时时间
                            .readTimeout(HttpConfig.HTTP_TIME_OUT_TIME, TimeUnit.SECONDS)
                            //设置写入超时时间
                            .writeTimeout(HttpConfig.HTTP_TIME_OUT_TIME, TimeUnit.SECONDS)
                            //默认重试一次
                            .retryOnConnectionFailure(true)
                            //添加请求头拦截器
                            .addInterceptor(InterceptorHandler.getHeaderInterceptor())
                            //添加日志拦截器
                            .addInterceptor(InterceptorHandler.getLogInterceptor())
                            //添加缓存拦截器
//                            .addInterceptor(InterceptorHelper.getCacheInterceptor())
                            //添加重试拦截器
                            .addInterceptor(InterceptorHandler.getRetryInterceptor())
                            // 信任Https,忽略Https证书验证
                            // https认证,如果要使用https且为自定义证书 可以去掉这两行注释，并自行配制证书。
//                            .sslSocketFactory(SSLSocketTrust.getSSLSocketFactory())
//                            .hostnameVerifier(SSLSocketTrust.getHostnameVerifier())
                            //缓存
//                            .cache(cache)
                            .build();
                }
            }
        }
    }

    /**
     * 对外提供调用 API的接口
     *
     * @return
     */
    public ObservableAPI getAPIService() {
        return mObservableAPI;
    }
}