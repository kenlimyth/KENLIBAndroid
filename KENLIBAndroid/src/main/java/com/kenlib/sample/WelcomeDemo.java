package com.kenlib.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.util.ManifestUtil;

/**
 * 全屏
 * 无title
 * 实现不闪屏的启动欢迎页面
 *  方法: activity加入主题即可
 *  <activity android:name="com.kenlib.demo.WelcomeDemo"
 *             android:screenOrientation="portrait"
 *             android:theme="@style/WelcomeTheme"
 *             >
 */
public class WelcomeDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Log.d("ken", ManifestUtil.channelValue(this, "UMENG_CHANNEL"));


        setContentView(R.layout.activity_welcome);

    }

}
