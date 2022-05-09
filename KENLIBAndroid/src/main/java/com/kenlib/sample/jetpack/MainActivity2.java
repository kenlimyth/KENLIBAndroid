package com.kenlib.sample.jetpack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Date;

import com.kenlib.android.databinding.ActivityLivedataBinding;
import com.kenlib.sample.jetpack.adapter.RecyclerViewAdapter;
import com.kenlib.sample.jetpack.dto.UserInfo;
import com.kenlib.sample.jetpack.viewmodel.MainViewModel;


/**
 * Jetpack
 * livedata + viewModel + viewBinding  和 RecyclerView 配合使用非常简洁
 */
public class MainActivity2 extends AppCompatActivity {
    private MainViewModel viewModel;
    ActivityLivedataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivedataBinding.inflate(LayoutInflater.from(this));
        View view = binding.getRoot();
        setContentView(view);

        binding.rv.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        subscribeUi(viewModel.userInfoMutableLiveData);

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo userInfo = new UserInfo();
                userInfo.id = "1" + new Date().toString();
                userInfo.name = "test";
                viewModel.userInfoMutableLiveData.postValue(userInfo);

            }
        });

    }

    private void subscribeUi(LiveData<UserInfo> liveData) {
        // Update the list when the data changes
        liveData.observe(this, userInfo -> {
            if (userInfo != null) {
                binding.rv.setAdapter(new RecyclerViewAdapter(userInfo.getList(), MainActivity2.this));
            }
        });
    }


}