package com.kenlib.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.kenlib.android.R;

/**
 * 浮动按钮 FloatingActionButton
 */
public class FloatingActionButtonDemo extends AppCompatActivity {

    RecyclerView recyclerView;
    int mColumns = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_action_button);


    }
}
