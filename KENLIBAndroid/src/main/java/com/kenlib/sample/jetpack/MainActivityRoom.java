package com.kenlib.sample.jetpack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kenlib.android.databinding.ActivityLivedataBinding;
import com.kenlib.sample.jetpack.db.entity.ProductEntity;
import com.kenlib.sample.jetpack.viewmodel.RoomViewModel;


/**
 * Jetpack
 * Room 在 SQLite 上提供了一个抽象层，以便在充分利用 SQLite 的强大功能的同时，能够流畅地访问数据库
 */
public class MainActivityRoom extends AppCompatActivity {
    private RoomViewModel viewModel;
    ActivityLivedataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivedataBinding.inflate(LayoutInflater.from(this));
        View view = binding.getRoot();
        setContentView(view);

        binding.rv.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(RoomViewModel.class);

        binding.testROOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.getProductList();
            }
        });
        binding.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.del();
            }
        });
        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.update();
            }
        });
        viewModel.product.observe(this, userInfo -> {
            if (userInfo != null) {
                binding.name.setText("");
                for(ProductEntity item  : userInfo) {
                    binding.name.setText(binding.name.getText()+"--"+item.getName());
                }
            }
        });

    }





}