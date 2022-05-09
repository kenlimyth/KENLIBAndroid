package com.kenlib.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.util.StatusBarUtil;
import com.kenlib.util.Util;

/**
 * 状态栏
 */
public class StatusBarDemo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Util.titleHide(this);
        StatusBarUtil.setWindowStatusBarColor(this, R.color.white);
        StatusBarUtil.setStatusBarLightMode(this);
        setContentView(R.layout.status_bar);


    }


}
