package com.kenlib.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;

/**
 * 透明度Alpha 设置
 */
public class AlphaDemo extends AppCompatActivity {

    LinearLayout ll_container;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_demo);

        ll_container = findViewById(R.id.ll_container);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Java代码 0~255透明度值  透明度
                ll_container.getBackground().setAlpha(100);

            }
        });

    }


}
