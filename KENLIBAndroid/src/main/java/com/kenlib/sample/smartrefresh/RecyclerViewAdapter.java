package com.kenlib.sample.smartrefresh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.kenlib.android.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<String> lists;

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
        String item = lists.get(position);
        holder.tv.setText(item);

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
