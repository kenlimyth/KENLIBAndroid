package com.kenlib.view.imgsee;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.kenlib.android.R;
import com.kenlib.sample.photo.dto.FileInfo;

/**
 * Glide + PhotoView + ViewPager
 * 图片浏览
 */
public class ImgSeeView extends LinearLayout {

    FragmentActivity context;
    Ido ido;
    private ViewPager viewPager;
    private TextView tv_indicator;
    private ArrayList<FileInfo> fileInfoArrayList;
    PictureSlidePagerAdapter pictureSlidePagerAdapter;

    public interface Ido {
        ArrayList<FileInfo> setData();

        void setVPScroll(int position);
    }

    public ImgSeeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = (FragmentActivity) context;
        View view = View.inflate(context, R.layout.img_see, this);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tv_indicator = (TextView) view.findViewById(R.id.tv_indicator);

    }

    public void create(Ido ido) {
        this.ido = ido;
        fileInfoArrayList = ido.setData();

        initVP();
    }

    private void initVP() {

        if (fileInfoArrayList == null || fileInfoArrayList.size() == 0) {
            return;
        }

        pictureSlidePagerAdapter = new PictureSlidePagerAdapter(context.getSupportFragmentManager());
        viewPager.setAdapter(pictureSlidePagerAdapter);
        viewPager.setOnPageChangeListener(new op());
    }


    public void setSelected(int i) {
        viewPager.setCurrentItem(i, false);
    }

    public void refresh(ArrayList<FileInfo> arrayList) {
        fileInfoArrayList = arrayList;
        pictureSlidePagerAdapter.notifyDataSetChanged();
    }

    class op implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            tv_indicator.setText(String.valueOf(position + 1) + "/" + fileInfoArrayList.size());
        }

        @Override
        public void onPageSelected(int position) {
            ido.setVPScroll(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class PictureSlidePagerAdapter extends FragmentStatePagerAdapter {

        public PictureSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE; //解决不刷新的问题
//            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            return PictureSlideFragment.newInstance(fileInfoArrayList.get(position));//<span style="white-space: pre;">返回展示不同网络图片的PictureSlideFragment</span>
        }

        @Override
        public int getCount() {
            return fileInfoArrayList.size();//<span style="white-space: pre;">指定ViewPager的总页数</span>
        }
    }


}
