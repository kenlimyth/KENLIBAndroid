package com.kenlib.sample.photo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import com.kenlib.android.R;
import com.kenlib.sample.photo.dto.FileInfo;
import com.kenlib.sample.photo.util.Config;
import com.kenlib.util.AppExecutors;
import com.kenlib.util.FileUtil;
import com.kenlib.util.StatusBarUtil;
import com.kenlib.util.Util;
import com.kenlib.view.imgsee.ImgSeeView;

public class ImgLookActivity extends AppCompatActivity {

    ArrayList<FileInfo> arrayList;
    int currIndex;
    String path;
    MiniLoadingDialog progressDialog;
    ImgSeeView imgSeeView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Util.titleHide(this);
        StatusBarUtil.setWindowStatusBarColor(this, R.color.hs);
        setContentView(R.layout.img_see_activity);

        arrayList = (ArrayList<FileInfo>) getIntent().getSerializableExtra("list");
        currIndex = getIntent().getIntExtra("index", 0);

        ImageView button = findViewById(R.id.btn_sub);
        ImageView btnDel = findViewById(R.id.btnDel);
        EditText editText = findViewById(R.id.tag);

        progressDialog = WidgetUtils.getMiniLoadingDialog(ImgLookActivity.this);

        imgSeeView = (ImgSeeView) findViewById(R.id.img_see);
        imgSeeView.create(new ImgSeeView.Ido() {
            @Override
            public ArrayList<FileInfo> setData() {
                return arrayList;
            }

            @Override
            public void setVPScroll(int position) {
                currIndex = position;
            }
        });
        imgSeeView.setSelected(currIndex);

        btnDel.setOnClickListener(v -> {
            new MaterialDialog.Builder(ImgLookActivity.this)
                    .content("确定删除图片？")
                    .positiveText("是")
                    .negativeText("否")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            getPath();
                            del();
                        }
                    })
                    .show();
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.updateMessage("处理中..");
                progressDialog.show();

                AppExecutors.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        String tag = editText.getText().toString();
                        String dir = FileUtil.getRootFileDir(Config.FileUpDir);

                        getPath();

                        String savePath = com.kenlib.sample.photo.util.Util.getSavePath(dir, tag, path);
                        FileUtil.copyFileUsingFileChannels(new File(path), new File(savePath));

                        del();
                    }
                });


            }
        });


    }

    void getPath() {
        //path
        FileInfo fileInfo = arrayList.get(currIndex);
        path = arrayList.get(currIndex).localImageUrl;
        if (fileInfo.type == FileInfo.Type.mp4) {
            path = arrayList.get(currIndex).VideoPath;
        }

    }

    void del() {
        FileUtil.delFileAndDir(path);

        arrayList.remove(currIndex);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (arrayList.size() == 0) {
                    Util.showToast(ImgLookActivity.this, "处理完了");
                    Config.IsRresh = true;
                    finish();
                } else {
                    imgSeeView.setSelected(currIndex);
                    Util.showToast(ImgLookActivity.this, "成功");
                    progressDialog.dismiss();
                }
                imgSeeView.refresh(arrayList);


            }
        });
    }


}
