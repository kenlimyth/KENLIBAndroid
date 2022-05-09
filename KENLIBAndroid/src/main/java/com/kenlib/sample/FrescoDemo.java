package com.kenlib.sample;

import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;

/**
 * Fresco 圆形头像
 * implementation 'com.facebook.fresco:fresco:1.11.0'
 */
public class FrescoDemo extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //初始化Fresco
        Fresco.initialize(this);

        setContentView(R.layout.fresco_demo);


        Uri uri = Uri.parse("https://pic4.zhimg.com/03b2d57be62b30f158f48f388c8f3f33_b.png");
        SimpleDraweeView commonImageView = (SimpleDraweeView) findViewById(R.id.commonImageView);
        commonImageView.setImageURI(uri);

        SimpleDraweeView circleImageView = (SimpleDraweeView) findViewById(R.id.circleImageView);
        circleImageView.setImageURI(uri);

        SimpleDraweeView roundedImageView = (SimpleDraweeView) findViewById(R.id.roundedImageView);
        roundedImageView.setImageURI(uri);

    }


}
