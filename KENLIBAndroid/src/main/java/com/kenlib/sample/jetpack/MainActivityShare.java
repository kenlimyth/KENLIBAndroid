package com.kenlib.sample.jetpack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.databinding.ActivityLivedata1Binding;

/**
 * Jetpack
 * viewModel,livedata
 * Fragment 之间共享数据
 */
public class MainActivityShare extends AppCompatActivity {
    ActivityLivedata1Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivedata1Binding.inflate(LayoutInflater.from(this));
        View view = binding.getRoot();
        setContentView(view);


    }
}