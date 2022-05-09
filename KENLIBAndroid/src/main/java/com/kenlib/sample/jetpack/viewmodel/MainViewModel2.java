package com.kenlib.sample.jetpack.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kenlib.sample.jetpack.bean.TestBean;
import com.kenlib.sample.jetpack.dto.UserInfo;

/**
 * ObservableField
 * MutableLiveData
 * 测试
 */
public class MainViewModel2 extends ViewModel {

    public ObservableField<TestBean> testBeanObservableField = new ObservableField<>();
    public MutableLiveData<TestBean> testBeanMutableLiveData = new MutableLiveData<>();


}

