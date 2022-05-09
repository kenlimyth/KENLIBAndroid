package com.kenlib.sample.tbs;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tencent.smtt.sdk.TbsReaderView;

import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;
import com.kenlib.http.retrofit_rxjava.BaseObserver;
import com.kenlib.http.retrofit_rxjava.RetrofitHandler;
import com.kenlib.http.retrofit_rxjava.RxTransformerHandler;
import com.kenlib.util.FileUtil;
import com.kenlib.util.Util;
import okhttp3.ResponseBody;

/**
 * tbs 腾讯文件浏览服务
 */
public class MainActivity extends AppCompatActivity {

    private TbsReaderView mTbsReaderView;
    private Button mDownloadBtn;

    private DownloadManager mDownloadManager;
    private long mRequestId;
    String fileurl = FileUtil.getRootFilePath() + "test.docx";
    private String mFileUrl = "http://110.42.247.94/t.docx";
    private String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs);
        mDownloadBtn = (Button) findViewById(R.id.btn_download);

    }

    public void onClickDownload(View v) {

        startDownload();
    }

    void startDownload() {
        RetrofitHandler.getInstance().getAPIService().downloadFile(mFileUrl)
                .compose(RxTransformerHandler.observableIO2Main())
                .subscribe(new BaseObserver<ResponseBody>() {
                    @Override
                    protected void onSuccess(ResponseBody response) {

                        byte[] b = FileUtil.getBytes(response.byteStream());
                        FileUtil.writeFile(fileurl, b);
                        Util.tbsOpenFile(MainActivity.this, fileurl);
                    }

                    @Override
                    protected void onFailure(String errorMessage) {

                    }
                });

    }



}
