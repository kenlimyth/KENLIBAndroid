package com.kenlib.sample;

import android.os.Bundle;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.kenlib.android.R;
import com.kenlib.view.xxk.TabContentFragment;
import com.kenlib.view.xxk.XXKTop;

/**
 * 选项卡
 * TabLayout + ViewPager + Fragment
 */
public class XXKTopDemo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xxk_top_demo);

        XXKTop xxk = (XXKTop) findViewById(R.id.xxk);
        xxk.create(new XXKTop.Ido() {
            @Override
            public List<Fragment> setFragments() {
                List<Fragment> list = new ArrayList<Fragment>();
                list.add(new TabContentFragment().newInstance("aaaaaaa1"));
                list.add(new TabContentFragment().newInstance("aaaaaaa2"));
                list.add(new TabContentFragment().newInstance("aaaaaaa3"));
                list.add(new TabContentFragment().newInstance("aaaaaaa4"));
                return list;
            }

            @Override
            public boolean setTabs(TabLayout mTabTl) {
                return false;
            }
        });


    }

}
