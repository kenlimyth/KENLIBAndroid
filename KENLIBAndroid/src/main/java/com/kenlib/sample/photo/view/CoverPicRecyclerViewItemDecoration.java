package com.kenlib.sample.photo.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kenlib.sample.photo.adapter.LocalFileAdapter;

public class CoverPicRecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    //列数
    private int mSpanCount;

    //上下文
    private Context mContext;

    //分隔线宽度
    private int mDividerWidth;

    //两侧需要距离的旗帜
    private boolean mIsNeedSpace;

    public CoverPicRecyclerViewItemDecoration(Context context, int mDividerWidth, boolean isNeedSpace) {
        this.mContext = context;
        this.mDividerWidth = mDividerWidth;
        this.mIsNeedSpace = isNeedSpace;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter.getItemViewType(itemPosition) == LocalFileAdapter.TYPE_ITEM) {
            int top = 0;
            int left;
            int right;
            int bottom;
            mSpanCount = getSpanCount(parent);
            int maxAllDividerWidth = getMaxDividerWidth(view);
            //与父版式之间的间隔
            int spaceWidth = 0;
            if (mIsNeedSpace) {
                spaceWidth = mDividerWidth;
            }
            //各视图大小（包括left+right）
            int eachItemWidth = maxAllDividerWidth / mSpanCount;

            if (mSpanCount != 1) {
                //项目与项目之间的距离
                int dividerItemWidth = (maxAllDividerWidth - 2 * spaceWidth) / (mSpanCount - 1);

                left = itemPosition % mSpanCount * (dividerItemWidth - eachItemWidth) + spaceWidth;
                right = eachItemWidth - left;
                bottom = dividerItemWidth;
                outRect.set(left, top, right, bottom);
            } else {
                outRect.set(0, 0, 0, 5);
            }


        }

    }

    /**
     * 获取最大剩余宽度
     *
     * @param view 视图
     * @return 最大剩余宽度
     */
    private int getMaxDividerWidth(View view) {
        int itemWidth = view.getLayoutParams().width;
        int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        return screenWidth - itemWidth * mSpanCount;
    }

    /**
     * 获得列数
     *
     * @param parent 再利用
     * @return 列数
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
}
