package com.kenlib.sample;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.kenlib.android.R;
import com.kenlib.util.DialogBottomSheetUtil;

/**
 * 测试用 android
 */
public class DialogBottomSheetUtilDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_demo);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogBottomSheetUtil.getInstance(DialogBottomSheetUtilDemo.this,R.layout.dialog3).show();


            }
        });

        BarUtils.setStatusBarColor(DialogBottomSheetUtilDemo.this, getResources().getColor(R.color.red1));
//        BarUtils.setNavBarColor(TestDemo.this, getResources().getColor(R.color.red1));
        BarUtils.setNavBarVisibility(DialogBottomSheetUtilDemo.this, false);
//        StatusBarUtil.setNavigationBarColor(TestDemo.this, getResources().getColor(R.color.red1));


    }


}
