package com.kenlib.sample.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.kenlib.android.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<String> lists;
    private AbsOnItemClick absOnItemClick;

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
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (absOnItemClick != null) {
                    absOnItemClick.onClick(item);
                }
            }
        });

        holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (absOnItemClick != null) {
                    absOnItemClick.onLongClick(item);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    /**
     * 删除数据
     *
     * @param position
     */
    public final void delData(int position) {
        lists.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 移动进行数据交换
     *
     * @param fromPosition
     * @param toPosition
     */
    public final void move(int fromPosition, int toPosition) {
        Collections.swap(lists, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tv;

        private MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
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
