package com.kenlib.sample;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.util.Search;

/**
 * Search 使用
 */
public class SearchDemo extends AppCompatActivity {

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
