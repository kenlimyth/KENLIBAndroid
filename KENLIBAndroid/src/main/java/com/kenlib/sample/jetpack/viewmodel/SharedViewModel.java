package com.kenlib.sample.jetpack.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Integer> selected = new MutableLiveData<>();

    public void select(Integer item) {
        selected.setValue(item);
    }
    public MutableLiveData<Integer> getSelected() {
        return selected;
    }


}

