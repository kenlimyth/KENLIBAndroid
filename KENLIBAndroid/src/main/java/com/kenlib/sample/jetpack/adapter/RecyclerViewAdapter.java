package com.kenlib.sample.jetpack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import com.kenlib.android.databinding.ItemLivedataBinding;
import com.kenlib.sample.jetpack.dto.UserInfo;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<UserInfo> lists;
    private AbsOnItemClick absOnItemClick;
    Context context;

    public RecyclerViewAdapter(ArrayList<UserInfo> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemLivedataBinding binding = ItemLivedataBinding.inflate(LayoutInflater.from(context));
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserInfo item = lists.get(position);
        holder.viewBinding.id.setText(item.id);
        holder.viewBinding.name.setText(item.name);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemLivedataBinding viewBinding;

        private MyViewHolder(ItemLivedataBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }
    }

    public void setAbsOnItemClick(AbsOnItemClick absOnItemClick) {
        this.absOnItemClick = absOnItemClick;
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
