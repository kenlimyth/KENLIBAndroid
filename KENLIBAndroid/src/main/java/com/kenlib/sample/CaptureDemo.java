package com.kenlib.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.capture.ImageCropActivity;
import com.kenlib.util.FileUtil;

/**
 * 图像裁剪
 */
public class CaptureDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.capture_demo);

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgurl = FileUtil.getRootFilePath() + "1.jpg";
                Intent intent = ImageCropActivity.createIntent(
                        CaptureDemo.this, imgurl, imgurl, 300, 500, false);
                startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {


        }


    }


}
