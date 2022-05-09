package com.kenlib.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import android.content.Context;

/**
 * 原生HTTP
 * HttpURLConnection
 */
public class HttpUtil {

    public HttpUtil(Context context) {
        // TODO Auto-generated constructor stub

    }

    /**
     * 发送post
     */
    public static String sendPost(String url, JSONObject jsonObject) {

        String poststr = "";
        try {

            HttpPost request = new HttpPost(url);
            request.addHeader("Content-Type", "text/html");
            request.addHeader("charset", HTTP.UTF_8);
            StringEntity se = new StringEntity(jsonObject.toString(),
                    HTTP.UTF_8);
            request.setEntity(se);
            HttpResponse httpResponse = new DefaultHttpClient()
                    .execute(request);

            /* 若状态码为200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {

                // 得到应答的字符串，这也是一个 JSON 格式保存的数据
                poststr = EntityUtils.toString(httpResponse.getEntity(),
                        "utf-8");

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return poststr;

    }

    /**
     * 发送post
     */
    public static String sendPost(String url, JSONArray jsonArray) {

        String poststr = "";
        try {

            HttpPost request = new HttpPost(url);
            request.addHeader("Content-Type", "text/html");
            request.addHeader("charset", HTTP.UTF_8);
            StringEntity se = new StringEntity(jsonArray.toString(), HTTP.UTF_8);
            request.setEntity(se);
            HttpResponse httpResponse = new DefaultHttpClient()
                    .execute(request);

            /* 若状态码为200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 得到应答的字符串，这也是一个 JSON 格式保存的数据
                poststr = EntityUtils.toString(httpResponse.getEntity(),
                        "utf-8");

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        return poststr;

    }

    /**
     * 发送post 没有参数
     */
    public static String sendPost(String url) {
        String poststr = "";
        try {
            HttpPost request = new HttpPost(url);
            request.addHeader("Content-Type", "text/html");
            request.addHeader("charset", HTTP.UTF_8);
            HttpResponse httpResponse = new DefaultHttpClient()
                    .execute(request);

            /* 若状态码为200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 得到应答的字符串，这也是一个 JSON 格式保存的数据
                poststr = EntityUtils.toString(httpResponse.getEntity(),
                        "utf-8");

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return poststr;
    }

}


