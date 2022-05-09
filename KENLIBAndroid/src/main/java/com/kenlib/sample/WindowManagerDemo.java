package com.kenlib.sample;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.util.WindowManagerUtil;

/**
 * WindowManager  悬浮窗 ,画中画,浮在APP最上层
 */
public class WindowManagerDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.window_manager_demo);


        findViewById(R.id.btnopen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WindowManagerUtil.show(WindowManagerDemo.this,null);
            }
        });


    }


}
