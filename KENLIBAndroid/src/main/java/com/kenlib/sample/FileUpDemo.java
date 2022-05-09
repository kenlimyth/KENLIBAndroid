package com.kenlib.sample;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * 上传文件，带进度
 * 原理：监听RequestBody，获取文件传送多少
 */
public class FileUpDemo extends AppCompatActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    String url = "http://192.168.42.245:8080/upload";

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask<String, String, String>() {

            @SuppressLint("StaticFieldLeak")
            @Override
            protected String doInBackground(String... strings) {

                File file = new File(Environment.getExternalStorageDirectory(), "1.mp4");
                OkHttpClient httpClient = new OkHttpClient.Builder().readTimeout(5, TimeUnit.MINUTES).build();
                // 构建请求 Body , 这个我们之前自己动手写过
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
//                builder.addFormDataPart("platform", "android");
                builder.addFormDataPart("photo", file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), file));

                // 这是使用了加强的MultipartBody,通过代理该类的输出流操作，
                // 获取流的总大小和每次写入的大小
                ExMultipartBody exMultipartBody = new ExMultipartBody(builder.build()
                        , new UploadProgressListener() {

                    @Override
                    public void onProgress(long total, long current) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                pb_main_download.setProgress((int) current);
                                pb_main_download.setMax((int) total);

                            }
                        });
                    }
                });

                // 怎么监听上传文件的进度？

                // 构建一个请求
                final Request request = new Request.Builder()
                        .url(url)
                        .post(exMultipartBody).build();

                try {
                    Response response = httpClient.newCall(request).execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();

    }

    private ProgressBar pb_main_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_up_demo);

        pb_main_download = findViewById(R.id.pb_main_download);
    }

    interface UploadProgressListener {
        void onProgress(long total, long current);
    }


    public class ExMultipartBody extends RequestBody {
        private RequestBody mRequestBody;
        private int mCurrentLength;
        private UploadProgressListener mProgressListener;

        public ExMultipartBody(MultipartBody requestBody) {
            this.mRequestBody = requestBody;
        }

        public ExMultipartBody(MultipartBody requestBody, UploadProgressListener progressListener) {
            this.mRequestBody = requestBody;
            this.mProgressListener = progressListener;
        }

        @Nullable
        @Override
        public MediaType contentType() {
            // 静态代理最终还是调用的代理对象的方法
            return mRequestBody.contentType();
        }

        @Override
        public long contentLength() throws IOException {
            return mRequestBody.contentLength();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            Log.e("TAG", "监听");
            // 总的长度
            final long contentLength = contentLength();

            // 又来一个代理 ForwardingSink
            ForwardingSink forwardingSink = new ForwardingSink(sink) {
                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    // 每次写都会来这里
                    mCurrentLength += byteCount;
                    if (mProgressListener != null) {
                        mProgressListener.onProgress(contentLength, mCurrentLength);
                    }
                    Log.e("TAG", contentLength + " : " + mCurrentLength);
                    super.write(source, byteCount);
                }
            };
            // 转一把
            BufferedSink bufferedSink = Okio.buffer(forwardingSink);
            mRequestBody.writeTo(bufferedSink);
            // 刷新，RealConnection 连接池
            bufferedSink.flush();
        }
    }


}
