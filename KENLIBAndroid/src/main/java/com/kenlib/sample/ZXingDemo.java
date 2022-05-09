package com.kenlib.sample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;
import com.kenlib.zxing.activity.CaptureActivity;

/**
 * 测试用 android
 */
public class ZXingDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_demo);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ZXingDemo.this, CaptureActivity.class);
                startActivityForResult(intent, 1);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String input = intent.getStringExtra("result");
            Log.d("ken", input);
        }
    }


}
