package com.kenlib.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kenlib.android.R;
import com.kenlib.sample.recyclerview.RecyclerViewAdapter;
import com.kenlib.util.Util;

/**
 * 谷歌官方推荐的下拉刷新控件
 */
public class SwipeRefreshLayoutDemo extends AppCompatActivity {

    RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String[] names = new String[]
            {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.titleHide(this);

        setContentView(R.layout.swiperefresh_demo);
        //创建list集合
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        List<Map<String, Object>> listItems =
                new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("names", names[i]);
            listItems.add(listItem);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(15);
        recyclerView.setPadding(0, 0, 0, 10);

        //模拟数据
        ArrayList<String> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add("item" + i);
        }
        recyclerViewAdapter = new RecyclerViewAdapter(lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);


        final SwipeRefreshLayout swip_refresh_layout = findViewById(R.id.swipeLayout);
        //设置 加载的那个圈圈的颜色，最多四种，这个颜色是依次加载的
        swip_refresh_layout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swip_refresh_layout.setProgressBackgroundColorSchemeColor(R.color.red1);
        swip_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swip_refresh_layout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }


}
