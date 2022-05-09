package com.kenlib.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;
import com.kenlib.capture1.CoverAdjustPositionActivity;
import com.kenlib.util.FileUtil;
import com.kenlib.util.RequestPermissionUtil;

/**
 * 图像裁剪, 网格线
 */
public class Capture1Demo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.capture_demo);

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                RequestPermissionUtil.getPermissionSD(Capture1Demo.this, new RequestPermissionUtil.IHandler() {
                    @Override
                    public void isHavePermission() {
                        to();
                    }
                });
            }


        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RequestPermissionUtil.getPermissionSD(Capture1Demo.this, new RequestPermissionUtil.IHandler() {
            @Override
            public void isHavePermission() {
                to();
            }
        });
    }

    void to() {
        String imgurl = FileUtil.getRootFilePath() + "1.png";
        Intent intent = new Intent(Capture1Demo.this, CoverAdjustPositionActivity.class);
        intent.putExtra("imgurl", imgurl);

        startActivityForResult(intent, 1);
    }

}
