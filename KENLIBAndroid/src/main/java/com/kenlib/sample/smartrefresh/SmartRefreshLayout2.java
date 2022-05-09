package com.kenlib.sample.smartrefresh;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;

import java.util.ArrayList;

import com.kenlib.android.R;

/**
 * 刷新  带背景图片
 */
public class SmartRefreshLayout2 extends AppCompatActivity {


	private static boolean isFirstEnter = true;
	RecyclerViewAdapter recyclerViewAdapter;
	private int mOffset = 0;
	private int mScrollY = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practice_repast2);

		final View parallax = findViewById(R.id.parallax);
		RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
		refreshLayout.setEnableFooterFollowWhenNoMoreData(true);

		//第一次进入演示刷新
		if (isFirstEnter) {
			isFirstEnter = false;
			refreshLayout.autoRefresh();
		}

		getData();
		RecyclerView recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerViewAdapter=new RecyclerViewAdapter(lists);
		recyclerView.setAdapter(recyclerViewAdapter);

		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(@NonNull RefreshLayout refreshLayout) {
				refreshLayout.getLayout().postDelayed(new Runnable() {
					@Override
					public void run() {
						getData();
						recyclerViewAdapter.notifyDataSetChanged();
						refreshLayout.finishRefresh();
						refreshLayout.resetNoMoreData();//setNoMoreData(false);//恢复上拉状态
					}
				}, 1000);
			}
		});
		refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
				refreshLayout.getLayout().postDelayed(new Runnable() {
					@Override
					public void run() {
						if (recyclerViewAdapter.getItemCount() > 20) {
							Toast.makeText(getBaseContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
							refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
						} else {
							getData();
							recyclerViewAdapter.notifyDataSetChanged();
							refreshLayout.finishLoadMore();
						}
					}
				}, 1000);
			}

		});

		refreshLayout.setOnMultiListener(new SimpleMultiListener(){
			@Override
			public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
				mOffset = offset / 2;
				parallax.setTranslationY(mOffset - mScrollY);
			}
		});



	}

	ArrayList<String> lists=new ArrayList<>();
	int index=1;
	void getData(){
		for (int i = 0; i < 10; i++) {
			lists.add("item " + (index++));
		}
	}

}
