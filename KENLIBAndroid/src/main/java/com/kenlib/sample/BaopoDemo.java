package com.kenlib.sample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.animation.baopo.ExplodeParticleFactory;
import com.kenlib.animation.baopo.ExplosionField;

/**
 * BaopoDemo
 */
public class BaopoDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.baopo_demo);

        ImageView imageView = findViewById(R.id.img);
        ExplosionField explosionField = new ExplosionField(this, new ExplodeParticleFactory());
        explosionField.addListener(imageView);
        explosionField.addHandler(mHandler);

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    };


}
