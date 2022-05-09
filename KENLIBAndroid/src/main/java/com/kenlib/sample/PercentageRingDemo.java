package com.kenlib.sample;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.view.PercentageRing;

/**
 * 百分比进度 PercentageRing
 */
public class PercentageRingDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.percentage_ring);

        PercentageRing percentageRing = (PercentageRing) findViewById(R.id.per);
        percentageRing.setTargetPercent(10);

    }

}
