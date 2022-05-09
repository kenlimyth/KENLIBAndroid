package com.kenlib.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.kenlib.android.R;

/**
 * ToolBar 用法
 */
public class ToolBarDemo extends AppCompatActivity {

    private static String mMP4Path;
    VideoView mVideoView;
    MediaController mMediaController;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //当toolbar不连接到系统状态栏时使用这个属性加载资源文件
        //toolbar.inflateMenu(R.menu.zhihu_toolbar_menu);
        //将toolbar连接到系统状态栏
        toolbar.setNavigationIcon(R.mipmap.icon_bussiness);//设置导航栏图标


    }

    /*
     * 将toolbar连接到系统状态栏必须调用的方法
     * 注：也可以不用链接系统状态栏就不需要调用这个方法 直接用tool的点击监听
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //在return之前加载出toolbar要加载的资源文件
        getMenuInflater().inflate(R.menu.my_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //这个方法是菜单的点击监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


}
