package com.kenlib.sample.recyclerview.recyclerviewgroup;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 抽象类 ViewHolder
 */
public abstract class ItemViewHolder extends RecyclerView.ViewHolder {
    public ItemViewHolder(View item) {
        super(item);
    }

    public abstract void setData(int position);
}