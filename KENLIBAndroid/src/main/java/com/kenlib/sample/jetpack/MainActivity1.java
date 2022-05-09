package com.kenlib.sample.jetpack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kenlib.android.R;
import com.kenlib.android.databinding.ActivityLivedata2Binding;
import com.kenlib.sample.jetpack.bean.TestBean;
import com.kenlib.sample.jetpack.viewmodel.MainViewModel;
import com.kenlib.sample.jetpack.viewmodel.MainViewModel2;


/**
 * Jetpack
 * livedata + viewModel + viewBinding  和 RecyclerView 配合使用非常简洁
 */
public class MainActivity1 extends AppCompatActivity {
    ActivityLivedata2Binding binding;
    private MainViewModel2 viewModel;
    TestBean testBean = new TestBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_livedata2);
        viewModel = new ViewModelProvider(this).get(MainViewModel2.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (true) {
                    try {
                        Thread.sleep(3000);
                        i++;
                        testBean.setImage(""+i);
                        viewModel.testBeanMutableLiveData.postValue(testBean);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


}