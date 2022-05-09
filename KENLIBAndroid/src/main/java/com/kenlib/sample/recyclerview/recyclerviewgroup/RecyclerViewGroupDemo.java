package com.kenlib.sample.recyclerview.recyclerviewgroup;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kenlib.android.R;
import com.kenlib.util.DialogIng;

/**
 * RecyclerView 分组的数据显示，分组选择
 */
public class RecyclerViewGroupDemo extends AppCompatActivity {

    RecyclerView recyclerView;
    AdditionalBookListAdapter groupAdapter;
    List<BookTree> bookTrees;
    DialogIng waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.group_recycle_view);
        recyclerView = findViewById(R.id.recycler_view);

        getDB();

        // 设置缓存大小
        recyclerView.setItemViewCacheSize(15);
        // 当view的大小固定时，处理性能提高
        recyclerView.setHasFixedSize(true);
        groupAdapter = new AdditionalBookListAdapter(this, bookTrees);
        recyclerView.setAdapter(groupAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        groupAdapter.setSpanCount(layoutManager);
        recyclerView.setLayoutManager(layoutManager);

    }


    void getDB() {
        bookTrees = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            BookTree coverInfo = new BookTree();
            if (i == 0 || i == 5) {
                coverInfo.object = "title" + i;
                coverInfo.pid = i;
            } else {
                BookTree.BInfo bInfo = new BookTree.BInfo();
                bInfo.bTitle = "bTitle" + i;
                if (i > 5) {
                    bInfo.pid = 5;

                } else {
                    bInfo.pid = 0;
                }
                coverInfo.object = bInfo;
            }

            bookTrees.add(coverInfo);
        }


    }


}
