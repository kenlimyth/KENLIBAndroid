package com.kenlib.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;

/**
 * 多重水波纹
 */
public class MultiWaveHeaderDemo extends AppCompatActivity {

    LinearLayout ll_container;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiwave_demo);

        ll_container = findViewById(R.id.ll_container);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }


}
