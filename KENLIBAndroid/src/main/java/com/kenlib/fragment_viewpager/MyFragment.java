package com.kenlib.fragment_viewpager;

import android.util.Log;

import androidx.fragment.app.Fragment;

/**
 * 惰性加载（即当前fragment显示才加载，目的体现数据及时性，不显示不加载。）
 * Fragment
 */
public abstract class MyFragment extends Fragment {

    private String TAG = "MyFragment";
    protected boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "MyFragment-----setUserVisibleHint--" + isVisibleToUser);

        if (isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }

    }

    void onVisible() {
        Lanjiazai();
    }

    protected abstract void Lanjiazai();
}
