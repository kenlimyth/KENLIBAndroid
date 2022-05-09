package com.kenlib.sample;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.util.FileUtil;
import com.kenlib.util.ImgUtil;
import com.kenlib.util.ScreenUtil;

/**
 * activity加载后调用snapShotWithoutStatusBar
 * 使用
 * FileTool
 * IMGTool
 * ScreenUtils
 */
public class FiletoolIMGToolScreenUitlDemo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.floating_action_button);

        Bitmap bitmap = ScreenUtil.snapShotWithoutStatusBar(FiletoolIMGToolScreenUitlDemo.this);
        String imgurl = FileUtil.getRootFilePath() + "1.jpg";
        ImgUtil.saveBitmap2file(bitmap, imgurl, ScreenUtil.getScreenWidth(FiletoolIMGToolScreenUitlDemo.this),
                ScreenUtil.getScreenHeight(FiletoolIMGToolScreenUitlDemo.this));

    }
}
