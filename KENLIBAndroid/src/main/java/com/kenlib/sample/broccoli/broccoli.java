package com.kenlib.sample.broccoli;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;
import me.samlss.broccoli.Broccoli;
import me.samlss.broccoli.BroccoliGradientDrawable;
import me.samlss.broccoli.PlaceholderParameter;

/**
 * 占位控件1 普通用法
 */
public class broccoli extends AppCompatActivity {

    Broccoli broccoli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broccoli);

        broccoli = new Broccoli();
        broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(findViewById(R.id.tv1))
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                        Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                .build());
        broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(findViewById(R.id.tv2))
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                        Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                .build());
        broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(findViewById(R.id.tv3))
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                        Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                .build());
        broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                .setView(findViewById(R.id.iv1))
                .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                        Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                .build());
        broccoli.show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                broccoli.clearAllPlaceholders();

            }
        },2000);


    }


}
