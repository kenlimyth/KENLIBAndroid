package com.kenlib.sample;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.http.HttpUtil;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * OkHttpDemo
 */
public class OkHttpDemo extends AppCompatActivity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    String access_token = "ya29.a0AfH6SMDNe0NtwN-FS1uS3VYTVhp7hrbBsPRB56q-q8iNIPCZf_xLO2eFvPiS0C3amYR96QGWUvaCos9II1c1vHTreIcaIPNnBYs2GX5tnHqppMuvBH2ZSdkSWWzg7Rb7ku5-XIx8fS7UX_B3Dp_wkiEj8L3G4g";
    String idLast = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        new AsyncTask<String, String, String>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected String doInBackground(String... strings) {

                HttpUtil.sendPost("http://www.baidu.com");

                byte[] fileByte = new byte[0];
                AssetManager assetManager = getAssets();
                try {
                    InputStream inputStream = assetManager.open("test.txt");
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bytes = new byte[4096];
                    int len = 0;
                    while ((len = inputStream.read(bytes)) > 0) {
                        byteArrayOutputStream.write(bytes, 0, len);
                    }
                    fileByte = byteArrayOutputStream.toByteArray();
                } catch (IOException e) {
                    Log.e("ken", "AssetManager=" + e.getMessage());
                }

                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", "fileName",
                                RequestBody.create(MediaType.parse("multipart/form-data"), fileByte))
                        .build();

                Request request = new Request.Builder()
                        .header("Authorization", "Bearer " + access_token)
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
                    idLast = id;
                    Log.d("ken", "id=" + id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

        }.execute();


    }
}
