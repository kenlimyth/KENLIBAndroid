package com.kenlib.fragmentviewpager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import com.kenlib.util.*;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 顶部选项卡（已不用），留作代码参考
 * 带下划线动画
 * KEN 2016
 * Fragment+ViewPager
 */
public class FragmentViewPager {

	private int currIndex = 0;// 当前页卡编号

	FragmentActivity fragmentActivity;
	private ViewPager mPager;
	LinearLayout linearLayout;
	HorizontalScrollView horizontalScrollView;// 滚动
	private ImageView ivBottomLine, searchImg; // 下划线图片和搜索图片

	List<Map<String, String>> listTitle = new ArrayList<Map<String, String>>();// 标题列表
	private ArrayList<Fragment> fragmentsList;// fragment列表

	Resources resources;

	IFragmentList ifragmentList;
	PopupWindow popupWindow;

	int textViewWidth = 50;

	public FragmentViewPager(FragmentActivity fragmentActivity,
			ViewPager pager, LinearLayout linearLayout,
			HorizontalScrollView horizontalScrollView, ImageView ivBottomLine,
			ImageView searchImg, List<Map<String, String>> title) {

		resources = fragmentActivity.getResources();
		textViewWidth = Util.dp2Px(textViewWidth
				);
		this.fragmentActivity = fragmentActivity;
		this.mPager = pager;
		this.linearLayout = linearLayout;

		this.horizontalScrollView = horizontalScrollView;
		this.listTitle = title;
		this.ivBottomLine = ivBottomLine;
		this.searchImg = searchImg;

	}

	/**
	 * 设置接口
	 * 
	 * @param fragmentList
	 */
	public void setFragmentList(IFragmentList fragmentList) {
		this.ifragmentList = fragmentList;
	}

	/**
	 * 设置标题TextView宽度
	 * 
	 * @param width
	 */
	public void setTextViewWidth(int width) {
		this.textViewWidth = Util.dp2Px(width);
	}

	/**
	 * 创建
	 */
	public void create() {

		initViewPageFragment(listTitle);

		// 是否设置搜索
		if (this.searchImg != null) {
			Search search = new Search(this.searchImg, fragmentActivity);
			search.createSearch();
		}
	}

	void initViewPageFragment(List<Map<String, String>> list) {
		if (list == null || list.size() < 1) {
			return;
		}
		initTextView(list);
		InitImageView();
		InitViewPager(list);

	}

	/**
	 * 设置ViewPager
	 * 
	 * @param list
	 */
	private void InitViewPager(List<Map<String, String>> list) {
		if (list == null || list.size() < 1) {
			return;
		}

		fragmentsList = new ArrayList<Fragment>();

		fragmentsList = this.ifragmentList.addFragment(list);

		mPager.setAdapter(new MyFragmentPagerAdapter(fragmentActivity
				.getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 设置下划线图片样式
	 */
	private void InitImageView() {

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				textViewWidth, 2);

		layoutParams.setMargins(20, 0, 0, 0);
		this.ivBottomLine.setLayoutParams(layoutParams);

	}

	/**
	 * 设置标题内容和样式
	 * 
	 * @param list
	 */
	private void initTextView(List<Map<String, String>> list) {
		if (list == null || list.size() < 1) {
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			TextView nTv = new TextView(fragmentActivity);
			LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
					textViewWidth, LayoutParams.WRAP_CONTENT);
			lparams.setMargins(20, 0, 0, 0);
			lparams.weight = 1;
			nTv.setLayoutParams(lparams);
			nTv.setGravity(Gravity.CENTER);
			nTv.setTextSize(16);
			nTv.setId(i);
			nTv.setText(list.get(i).get("title"));
			nTv.setSingleLine(true);
			nTv.setOnClickListener(new MyOnClickListener(i));
			linearLayout.addView(nTv);
		}

		setTextColor(0);

	}

	/**
	 * 标题点击事件
	 * 
	 * @author Administrator
	 * 
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * viewpage事件
	 * 
	 * @author Administrator
	 * 
	 */
	public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
		int one = textViewWidth + 20;// 页卡1 -> 页卡2 偏移量

		@SuppressLint("NewApi")
		@Override
		public void onPageSelected(int arg0) {

			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);

			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);

			int screenW = ScreenUtil.getScreen(fragmentActivity).widthPixels;
			int countMax = screenW / one;
			if (arg0 % countMax == 0 && arg0 != 0) {// 自动滚动horizontalScrollView,只适应2屏

				horizontalScrollView.setScrollX(screenW);

			} else if (currIndex % countMax == 0 && arg0 < currIndex) {
				horizontalScrollView.setScrollX(-screenW);
			}

			currIndex = arg0;
			setTextColor(currIndex);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	/**
	 * 设置当前标题样式
	 * 
	 * @param id
	 */
	void setTextColor(int id) {
		if (linearLayout != null) {

			for (int i = 0; i < linearLayout.getChildCount(); i++) {
				View v = linearLayout.getChildAt(i);
				if (v instanceof TextView) {
					TextView textView = (TextView) linearLayout.getChildAt(i);
//					if (id == i) {
//						textView.setTextColor(resources.getColor(R.color.l));
//					} else {
//						textView.setTextColor(resources.getColor(R.color.hs));
//					}

				}

			}

		}

	}

}
