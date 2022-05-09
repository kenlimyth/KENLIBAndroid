package com.kenlib.sample.broccoli;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.kenlib.android.R;
import me.samlss.broccoli.Broccoli;
import me.samlss.broccoli.BroccoliGradientDrawable;
import me.samlss.broccoli.PlaceholderParameter;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<String> lists;
    /**
     * 是否已经加载成功
     */
    private boolean mHasLoad = false;
    private Map<View, Broccoli> mBroccoliMap = new HashMap<>();

    public RecyclerViewAdapter(ArrayList<String> lists) {
        this.lists = lists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Broccoli broccoli = mBroccoliMap.get(holder.itemView);
        if (broccoli == null) {
            broccoli = new Broccoli();
            mBroccoliMap.put(holder.itemView, broccoli);
        }
        if (mHasLoad) {
            broccoli.removeAllPlaceholders();
            String item = lists.get(position);
            holder.tv.setText(item);
        } else {
//            broccoli.addPlaceholders(holder.tv);
            broccoli.addPlaceholder(new PlaceholderParameter.Builder()
                    .setView(holder.tv)
                    .setDrawable(new BroccoliGradientDrawable(Color.parseColor("#DDDDDD"),
                            Color.parseColor("#CCCCCC"), 0, 1000, new LinearInterpolator()))
                    .build());
            broccoli.show();
        }

    }
    public void show(){
        mHasLoad=true;
    }

    /**
     * 资源释放，防止内存泄漏
     */
    public void recycle() {
        for (Broccoli broccoli : mBroccoliMap.values()) {
            broccoli.removeAllPlaceholders();
        }
        mBroccoliMap.clear();
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        private MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }


    /**
     * 和接口比优点可以部分实现方法，按需求实现
     */
    public abstract static class AbsOnItemClick {

        public abstract void onClick(String item);

        public void onLongClick(String item) {

        }
    }
}
