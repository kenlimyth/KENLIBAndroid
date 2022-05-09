package com.kenlib.http;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

//OkHttpClient rest 风格，如下执行delete
//OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//        .header("Authorization", "Bearer " + access_token)
//        .url("https://www.googleapis.com/drive/v3/files/"+idLast)
//        .delete()
//        .build();

public class OkHttpUtil {

    String url = "https://www.baidu.com/";

    public interface IHttpHandler {
        void onFailure(Call call, IOException e);

        void onResponse(Call call, Response response);
    }


    private static void newCall(final IHttpHandler iHttpHandler, OkHttpClient okHttpClient, Request request) {

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.toString());
                iHttpHandler.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.body().string());
                iHttpHandler.onResponse(call, response);
            }
        });
    }

    public static void httpGet(IHttpHandler iHttpHandler, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        newCall(iHttpHandler, okHttpClient, request);
    }

    public static void httpPostJson(IHttpHandler iHttpHandler, String json, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        newCall(iHttpHandler, okHttpClient, request);


    }


    public static void httpPostForm(IHttpHandler iHttpHandler, Map<String, String> params, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder requestBody = new FormBody.Builder();
        for (String key : params.keySet()) {
            requestBody.add(key, params.get(key));
        }

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody.build())
                .build();
        newCall(iHttpHandler, okHttpClient, request);

    }

    public static void httpUpFile(byte[] file) {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "fileName",
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer access_token")
                .url("https://www.googleapis.com/upload/drive/v3/files?uploadType=media")
                .post(requestBody)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {

            Log.e("ken", "newCall=" + e.getMessage());
        }
        if (!response.isSuccessful()) try {
            throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            Log.e("ken", "isSuccessful=" + e.getMessage());
        }

        Charset UTF8 = Charset.forName("UTF-8");
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE); // Buffer the entire body.
        } catch (IOException e) {
            Log.e("ken", "source=" + e.getMessage());
        }
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                Log.e("ken", "UnsupportedCharsetException=" + e.getMessage());
            }
        }
        String res = buffer.clone().readString(charset);
        Log.d("ken", "response.body()=" + res);

        try {
            JSONObject jsonObject = new JSONObject(res);
            String id = jsonObject.getString("id");
            Log.d("ken", "id=" + id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
