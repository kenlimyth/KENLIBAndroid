package com.kenlib.capture1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;
import com.kenlib.util.AppExecutors;
import com.kenlib.util.ActivitiyListener;
import com.kenlib.util.DialogUtil;
import com.kenlib.util.ImgUtil;
import com.kenlib.util.DialogIng;


/**
 * 图像裁剪, 网格线
 */
public class CoverAdjustPositionActivity extends AppCompatActivity {

    private Button mComplete;

    private String imgurl;

    //图像编辑视图
    private CustomImageEditorView mCustomImageEditorView;

    private DialogIng mWaitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cover_adjustpostion_activity);
        overridePendingTransition(R.anim.income_from_right, R.anim.leave_from_left);
        imgurl = getIntent().getStringExtra("imgurl");
        init();
        setListener();

    }

    /**
     * setListener
     */
    private void setListener() {
        mComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCustomImageEditorView.getCutFlg()) {
                    return;
                }
                mWaitDialog = DialogUtil.showIngWaitDialog(CoverAdjustPositionActivity.this, null);
                AppExecutors.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        mCustomImageEditorView.saveCroppedImages();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mWaitDialog.dismiss();

                            }
                        });
                    }
                });
            }
        });


    }

    /**
     * init
     */
    private void init() {
        mComplete = findViewById(R.id.complete);
        mCustomImageEditorView = findViewById(R.id.image_view);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onResume() {
        super.onResume();
        if (!ActivitiyListener.getCurrentActivityStatus()) {
            new AsyncBookImageViewService(CoverAdjustPositionActivity.this, imgurl)
                    .execute();
        }
    }

    /**
     * 还进行将指定的URL的图像显示在ImageView上的等待装入图像显示和错误处理
     */
    @SuppressLint("StaticFieldLeak")
    public class AsyncBookImageViewService extends AsyncTask<Void, Void, Throwable> {

        private final Context mContext;

        private Bitmap mImage;

        private final String mImageUrl;


        public AsyncBookImageViewService(Context context, String mImageUrl) {
            this.mContext = context;
            this.mImageUrl = mImageUrl;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mWaitDialog == null) {
                mWaitDialog = DialogIng.getInstance(CoverAdjustPositionActivity.this, null);
                mWaitDialog.setCancelable(false);
                mWaitDialog.show();
            }
        }

        @Override
        protected Throwable doInBackground(Void... params) {
            Throwable returnVal = null;
            mImage = ImgUtil.compressBitmap(mImageUrl, false, 100, 200);
            return returnVal;
        }

        @Override
        protected void onPostExecute(Throwable e) {
            mWaitDialog.dismiss();
            if (this.isCancelled()) {
                return;
            }

            if (e == null) {
                Handler h = new Handler();
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        mCustomImageEditorView.setBitmap(mImage);
                    }
                });
            } else {

            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.income_from_left, R.anim.leave_from_right);
    }
}
