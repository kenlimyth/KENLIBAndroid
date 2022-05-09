package com.kenlib.sample;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.util.Search;

/**
 * 自定义URL使用Scheme方式唤起Activity或App
 *
 * 两种方式调用：
 * 1.浏览器html使用js调起Activity
 * window.location = "app://test";
 *
 * 2.安卓 使用URL调起Activity
 * Uri uri=Uri.parse("app://test");
 * Intent intent=new Intent(Intent.ACTION_VIEW,uri);
 * startActivity(intent);
 *
 */
public class SchemeDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_demo);

        ImageView img = (ImageView) findViewById(R.id.img);
        Search search=new Search(img,this);
        search.createSearch();


    }

}
