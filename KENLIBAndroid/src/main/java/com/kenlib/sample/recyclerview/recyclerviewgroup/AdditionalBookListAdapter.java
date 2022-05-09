package com.kenlib.sample.recyclerview.recyclerviewgroup;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.kenlib.android.R;

/**
 * 亮点，采用继承抽象方法的手段，动态实现recyclerView的多布局
 */
public class AdditionalBookListAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    public static final int ITEM = 0;
    public static final int ITEM_TITLE = 1;
    private RecyclerViewGroupDemo mContext;
    private int mWidth;
    private int mHeight;
    private int mWidthPixels;
    private final List<BookTree> mBookTrees = new ArrayList<>();

    public AdditionalBookListAdapter(@NonNull RecyclerViewGroupDemo context, List<BookTree> bookTrees) {
        this.mContext = context;
        this.mWidth = calculateCellWidth();
        this.mHeight = getCellHeight(mWidth);
        this.mBookTrees.addAll(bookTrees);
    }

    boolean isSelected;

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == ITEM) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.addtional_body_item, viewGroup, false);
            return new BookItemViewHolder(v);
        } else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.addtional_title_item, viewGroup, false);
            return new TitleItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.setData(i);
    }

    @Override
    public int getItemCount() {
        return mBookTrees.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mBookTrees.get(position).object instanceof String) {
            return ITEM_TITLE;
        }
        return ITEM;
    }

    public class BookItemViewHolder extends ItemViewHolder {

        ImageView mBookImageView;
        TextView mDescTextView;
        RelativeLayout mRlBook;
        ImageView mIvCheck;
        ImageView mIvCheckBg;
        TextView mTvAdded;

        public BookItemViewHolder(View item) {
            super(item);
            mBookImageView = item.findViewById(R.id.iv_book);
            mDescTextView = item.findViewById(R.id.tv_description);
            mRlBook = item.findViewById(R.id.rl_book);
            mIvCheck = item.findViewById(R.id.iv_check);
            mTvAdded = item.findViewById(R.id.tv_added);
            mIvCheckBg = item.findViewById(R.id.iv_check_bg);
        }

        @Override
        public void setData(final int i) {
            final BookTree bt = mBookTrees.get(i);

            mRlBook.getLayoutParams().width = mWidth;
            mRlBook.getLayoutParams().height = mHeight;
            mBookImageView.getLayoutParams().width = mWidth;
            mBookImageView.getLayoutParams().height = mHeight;

            mDescTextView.setText(bt.object.toString());

            mIvCheck.setVisibility(View.VISIBLE);

            mRlBook.setOnClickListener(null);
            mRlBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bt.isChecked = bt.isChecked ? false : true;
                    notifyDataChanged();

                }
            });
            if (bt.isChecked) {
                mIvCheck.setImageResource(R.drawable.bookselect_checked_border);
                mIvCheckBg.setVisibility(View.VISIBLE);

            } else {
                mIvCheck.setImageResource(R.drawable.bookselect_unchecked_border);
                mIvCheckBg.setVisibility(View.GONE);
            }

        }
    }


    public class TitleItemViewHolder extends ItemViewHolder {

        TextView mTvTitle;
        CheckBox mCbAddBooks;
        TextView mTvCount;
        LinearLayout mTitleLayout;

        public TitleItemViewHolder(View item) {
            super(item);
            mTvTitle = item.findViewById(R.id.tv_title);
            mCbAddBooks = item.findViewById(R.id.cb_addbooks);
            mTvCount = item.findViewById(R.id.tv_count);
            mTitleLayout = item.findViewById(R.id.titleLayout);
        }

        @Override
        public void setData(final int i) {
            final BookTree bookTree = mBookTrees.get(i);
            String title = (String) bookTree.object;
            mTvTitle.setText(title);
            mCbAddBooks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for (BookTree bookTree1 : mBookTrees) {
                        if (bookTree1.object instanceof BookTree.BInfo) {
                            BookTree.BInfo bInfo = ((BookTree.BInfo) bookTree1.object);
                            if (bInfo.pid == bookTree.pid) {
                                bookTree1.isChecked = isChecked;
                            }

                        }
                    }
                    notifyDataChanged();
                }
            });

        }
    }

    /**
     * 计算宽度
     *
     * @return
     */
    private int calculateCellWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        int widthPixels = displayMetrics.widthPixels;
        this.mWidthPixels = widthPixels;
        int paddingLeftAndRight = (int) (widthPixels * 0.04 * 2);
        int space = (int) (widthPixels * 0.04);
        return (widthPixels - paddingLeftAndRight - space * 2) / 3;
    }

    private int getCellHeight(int width) {
        return width * 141 / 100;
    }

    /**
     * 动态设置列数
     *
     * @param layoutManager
     */
    public void setSpanCount(GridLayoutManager layoutManager) {
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                int type = getItemViewType(i);
                switch (type) {
                    default:
                    case ITEM:
                        return 1;
                    case ITEM_TITLE:
                        return 3;
                }
            }
        });
    }


    public void notifyDataChanged() {
        this.mWidth = calculateCellWidth();
        this.mHeight = getCellHeight(mWidth);
        notifyDataSetChanged();
    }

    public void notifyDataChanged(List<BookTree> bookTrees) {
        this.mBookTrees.clear();
        this.mBookTrees.addAll(bookTrees);
        notifyDataSetChanged();
    }
}
