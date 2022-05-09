package com.kenlib.sample.photo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.kenlib.android.R;

/**
 * 底部选项卡菜单
 * TabLayout + ViewPager + Fragment
 */
public class PhotoXXKView extends LinearLayout {
    private TabLayout mTabTl;
    private ViewPager vp;

    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    FragmentActivity context;
    Ido ido;

    public interface Ido {
        List<Fragment> setFragments();

        boolean setTabs(TabLayout mTabTl);

        void setVPgundong(int pagenum);
    }

    public PhotoXXKView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = (FragmentActivity) context;
        View view = View.inflate(context, R.layout.tab_viewpage_photo, this);
        mTabTl = view.findViewById(R.id.tl_tab);
        vp = view.findViewById(R.id.view_page);
    }

    public void create(Ido ido) {

        this.ido = ido;

        initVP();
        initTab();
    }

    private void initVP() {

        tabFragments = ido.setFragments();

        if (tabFragments == null || tabFragments.size() == 0) {
            return;
        }

        contentAdapter = new ContentPagerAdapter(context.getSupportFragmentManager());
        vp.setAdapter(contentAdapter);
        vp.setOnPageChangeListener(new op());
    }

    private void initTab() {
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setSelectedTabIndicatorHeight(0);
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(vp);
        Log.d("KEN", "initTab");
        Log.d("KEN", "tabFragments:" + tabFragments.size());
        if (!ido.setTabs(mTabTl)) {

            mTabTl.getTabAt(0).setCustomView(R.layout.tab_item_photo1);
            mTabTl.getTabAt(1).setCustomView(R.layout.tab_item_photo2);


        }

//        mTabTl.getTabAt(3).getCustomView().setSelected(true);
//        vp.setCurrentItem(3);
    }

    public void setXXKSelected(int i) {
        vp.setCurrentItem(i, false);
    }

    class op implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            ido.setVPgundong(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabFragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

}
