package com.kenlib.view.xxk;

import android.content.Context;

import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.kenlib.android.R;

/**
 * 顶部选项卡菜单,文字样式
 * TabLayout + ViewPager + Fragment
 *
 */
public class XXKTop extends LinearLayout {
    private TabLayout mTabTl;
    private ViewPager vp;

    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;
    FragmentActivity context;
    private List<String> tabIndicators;
    Ido ido;

    public interface Ido {
        List<Fragment> setFragments();

        boolean setTabs(TabLayout mTabTl);
    }

    public XXKTop(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = (FragmentActivity) context;
        View view = View.inflate(context, R.layout.tab_viewpage_top, this);
        mTabTl = (TabLayout) view.findViewById(R.id.tl_tab);
        vp = (ViewPager) view.findViewById(R.id.view_page);


    }

    public void create(Ido ido) {

        this.ido = ido;

        initVP();
        initTab();
    }

    private void initVP() {

        tabIndicators = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tabIndicators.add("Tab " + i);
        }

        tabFragments= ido.setFragments();
        if (tabFragments==null)
        {
            tabFragments = new ArrayList<>();
            for (String s : tabIndicators) {
                tabFragments.add(TabContentFragment.newInstance(s));
            }
        }

        contentAdapter = new ContentPagerAdapter(context.getSupportFragmentManager());
        vp.setAdapter(contentAdapter);
    }

    private void initTab() {
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabTextColors(ContextCompat.getColor(context, R.color.gray), ContextCompat.getColor(context, R.color.white));
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.white));
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(vp);

      if ( !ido.setTabs(mTabTl)) {

          for (int i = 0; i < tabIndicators.size(); i++) {
              TabLayout.Tab itemTab = mTabTl.getTabAt(i);
              if (itemTab!=null){

                  itemTab.setText(tabIndicators.get(i)+"tt");
              }
          }
      }

//        vp.setCurrentItem(3);

    }

    public void setXXKSelected(int i) {
        vp.setCurrentItem(i);
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
