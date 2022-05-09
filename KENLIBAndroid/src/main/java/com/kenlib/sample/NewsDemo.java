package com.kenlib.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import com.kenlib.android.R;
import com.kenlib.fragment_viewpager.NewList;
import com.kenlib.fragment_viewpager.FragmentViewPager;
import com.kenlib.fragment_viewpager.IFragmentList;


import android.content.res.Resources;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 模仿汽车之家主页，上部选项卡, 之前做的
 * FragmentViewPager,NewList ，search ,xListView使用
 */
public class NewsDemo extends FragmentActivity {
	private ViewPager mPager;
	private ImageView ivBottomLine, searchimg;
	LinearLayout linearLayout;
	List<Map<String, String>> titlelist = new ArrayList<Map<String, String>>();
	int countType = 0;
	Resources resources;
	HorizontalScrollView horizontalScrollView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.news);

		mPager = findViewById(R.id.vPager);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
		horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		searchimg = (ImageView) findViewById(R.id.search);

		titlelist = getTitleList();

		FragmentViewPager f = new FragmentViewPager(NewsDemo.this, mPager,
				linearLayout, horizontalScrollView, ivBottomLine, searchimg,
				titlelist);
		f.setFragmentList(new IFragmentList() {
			@Override
			public ArrayList<Fragment> addFragment(
					List<Map<String, String>> list) {
				// TODO Auto-generated method stub
				ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
				for (int i = 0; i < list.size(); i++) {
					fragmentsList
							.add(NewList.newInstance(list.get(i).get("id"), false));
				}

				// 或者这样添加fragment
				// fragmentsList.add(new Login());
				// fragmentsList.add(new ZC());

				return fragmentsList;
			}
		});
		f.create();

	}

	List<Map<String, String>> getTitleList() {// 类型

		List<Map<String, String>> listType = new ArrayList<Map<String, String>>();

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", "1");
		map.put("title", "论坛");
		listType.add(map);

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("id", "2");
		map1.put("title", "美食");
		listType.add(map1);

		return listType;

	}

}