package com.kenlib.sample.recyclerview;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kenlib.android.R;
import com.kenlib.util.Util;

/**
 * RecyclerView+ItemTouchHelper拖拽删除
 */
public class RecyclerViewDemo extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    int mColumns = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);

        //模拟数据
        ArrayList<String> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add("item" + i);
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, mColumns));
        recyclerViewAdapter = new RecyclerViewAdapter(lists);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(15);

        //ItemTouchHelper
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            /**
             *
             * @param recyclerView 关联的recyclerView
             * @param viewHolder  操作的viewHolder对象
             * @return 返回运动方向标志的组合，通过makeMovementFlags(dragFlags, swipeFlags)进行组合
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //拖拽的方法标记
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                //滑动方向标记
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                //通过makeMovementFlags方法将将方向标记进行组合，并将复合的值返回
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            /**
             * @param recyclerView 关联的recyclerView
             * @param viewHolder  要移动的viewHolder对象
             * @param target   移动到的目标ViewHolder对象
             * @return 返回true 才会执行ItemTouchHelper.Callback的onMoved方法，
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                recyclerViewAdapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            /**
             * @param viewHolder 滑动的viewHolder对象
             * @param direction  移动的方向标识
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                recyclerViewAdapter.delData(viewHolder.getAdapterPosition());
            }
        });
        //关联RecyclerView
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerViewAdapter.setAbsOnItemClick(new RecyclerViewAdapter.AbsOnItemClick() {
            @Override
            public void onClick(String item) {
                Util.showToast(RecyclerViewDemo.this, item);
            }

            @Override
            public void onLongClick(String item) {
                super.onLongClick(item);
                Util.showToast(RecyclerViewDemo.this, item);
            }
        });


    }
}
