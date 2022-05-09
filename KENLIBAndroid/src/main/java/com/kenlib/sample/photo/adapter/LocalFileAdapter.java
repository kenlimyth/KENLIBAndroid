package com.kenlib.sample.photo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kenlib.android.R;
import com.kenlib.sample.photo.dto.FileInfo;
import com.kenlib.util.AppExecutors;
import com.kenlib.util.ImgUtil;
import com.kenlib.util.LruCacheUtil;
import com.kenlib.util.Util;

/**
 * RecyclerView 多布局
 */
public class LocalFileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //封面图像大小信息
    protected static CoverImageInfo mCoverImageInfo = new CoverImageInfo();

    //列数
    private int mSpanCount;

    //图像布局
    private RelativeLayout.LayoutParams mCoverViewParams;

    //图像数据列表
    private List<FileInfo> mCoverImageList;

    private Context mContext;

    private HashMap<Integer, CheckBox> hashMapCk = new HashMap<>();
    private HashMap<Integer, TextView> hashMapCkTV = new HashMap<>();

    //点击事件
    private onSelectImageListener mOnSelectImageListener;

    //分隔线宽度
    private static final int DIVIDER_WIDTH = 2;

    //项目分隔值
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_ITEM2 = 2;

    public Boolean isCKDisplay = false;

    private OnCKListener onCKListener;


    public LocalFileAdapter(Context context, int spanCount, List<FileInfo> mImageList) {
        this.mContext = context;
        this.mSpanCount = spanCount;
        mCoverImageList = mImageList;
        //计算封面图像信息
        calculateDrawInfo();
        //设置图像布局
        mCoverViewParams = new RelativeLayout.LayoutParams(mCoverImageInfo.CoverImageWidth, mCoverImageInfo.CoverImageHeight);
    }

    @Override
    public int getItemCount() {
        return mCoverImageList.size();
    }

    private void setCheckBoxState(CheckBox checkBox, FileInfo fileInfo, int position,TextView textView) {
        hashMapCk.put(position, checkBox);
        hashMapCkTV.put(position,textView);
        if (isCKDisplay) {
            checkBox.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            checkBox.setChecked(fileInfo.isCheck);
        } else {
            checkBox.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }

    public void setAllCheckBoxState() {
        for (Map.Entry<Integer, CheckBox> entry : hashMapCk.entrySet()) {
//            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            CheckBox checkBox = entry.getValue();
            if (isCKDisplay) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }
        }

        for (Map.Entry<Integer, TextView> entry : hashMapCkTV.entrySet()) {
//            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
            TextView textView = entry.getValue();
            if (isCKDisplay) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.GONE);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_ITEM) {
            View v = layoutInflater.inflate(R.layout.localfile_img_item, parent, false);
            viewHolder = new VH(v);
        } else {
            View v = layoutInflater.inflate(R.layout.localfile_video_item, parent, false);
            viewHolder = new ViewHolderVideo(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        FileInfo fileInfo = mCoverImageList.get(position);
        // 图片布局
        if (viewHolder instanceof VH) {
            final String imageUrl = fileInfo.localImageUrl;
            VH vh = (VH) viewHolder;
//            vh.tvFileInfo.setText(fileInfo.localImageUrl + "\n" + fileInfo.size + "M");
            //设置图像布局参数
            vh.mCoverView.setLayoutParams(mCoverViewParams);
            vh.coverImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnSelectImageListener != null) {
                        mOnSelectImageListener.onClick(fileInfo);
                    }
                }
            });
            vh.coverImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnSelectImageListener != null) {
                        mOnSelectImageListener.setOnLongClickListener(fileInfo);
                        onCKListener.onCheckedChanged(fileInfo, isCKDisplay);
                    }
                    return true;
                }
            });

            vh.checkBox.setOnCheckedChangeListener(null);
            vh.ckTv.setOnClickListener(null);
            setCheckBoxState(vh.checkBox, fileInfo, position,vh.ckTv);
            vh.ckTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh.checkBox.setChecked(fileInfo.isCheck? false : true);
                }
            });
            vh.ckTv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    isCKDisplay = false;
                    setAllCheckBoxState();
                    onCKListener.onCheckedChanged(fileInfo, isCKDisplay);
                    return true;
                }
            });
            vh.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (onCKListener != null) {

                        fileInfo.isCheck = isChecked;
                        if (isChecked) {
                            vh.coverImage.setAlpha(0.5f);
                        } else {
                            vh.coverImage.setAlpha(1f);
                        }
                    }
                }
            });

            vh.coverImage.setTag(imageUrl);
            vh.coverImage.setImageResource(R.drawable.downloading);
            AppExecutors.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    final Bitmap cachedBitmap = LruCacheUtil.getBitmapFromCache(imageUrl);
                    if (cachedBitmap != null) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (vh.coverImage.getTag().equals(imageUrl)) {
                                    vh.coverImage.setImageBitmap(cachedBitmap);
                                }
                            }
                        });
                    } else {
                        final Bitmap bitmap = ImgUtil.compressBitmap(imageUrl, true, mCoverImageInfo.CoverImageWidth, mCoverImageInfo.CoverImageHeight);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (vh.coverImage.getTag().equals(imageUrl)) {
                                    vh.coverImage.setImageBitmap(bitmap);
                                    LruCacheUtil.addBitmapToCache(bitmap, imageUrl);
                                }
                            }
                        });

                    }

                }
            });
        } else {

            //视频布局
            ViewHolderVideo viewHolderVideo = (ViewHolderVideo) viewHolder;
//            viewHolderVideo.tvFileInfo.setText(fileInfo.VideoPath + "\n" + fileInfo.size + "M");
            //设置图像布局参数
            viewHolderVideo.mCoverView.setLayoutParams(mCoverViewParams);
            viewHolderVideo.coverImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnSelectImageListener != null) {
                        mOnSelectImageListener.onClick(fileInfo);
                    }
                }
            });
            viewHolderVideo.coverImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnSelectImageListener != null) {
                        mOnSelectImageListener.setOnLongClickListener(fileInfo);
                        onCKListener.onCheckedChanged(fileInfo, isCKDisplay);
                    }
                    return true;
                }
            });

            viewHolderVideo.checkBox.setOnCheckedChangeListener(null);
            viewHolderVideo.ckTv.setOnClickListener(null);
            setCheckBoxState(viewHolderVideo.checkBox, fileInfo, position,viewHolderVideo.ckTv);
            viewHolderVideo.ckTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolderVideo.checkBox.setChecked(fileInfo.isCheck? false : true);
                }
            });
            viewHolderVideo.ckTv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    isCKDisplay = false;
                    setAllCheckBoxState();
                    onCKListener.onCheckedChanged(fileInfo, isCKDisplay);
                    return true;
                }
            });
            viewHolderVideo.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (onCKListener != null) {

                        fileInfo.isCheck = isChecked;
                        if (isChecked) {
                            viewHolderVideo.coverImage.setAlpha(0.5f);
                        } else {
                            viewHolderVideo.coverImage.setAlpha(1f);
                        }
                    }
                }
            });

            viewHolderVideo.coverImage.setImageResource(R.drawable.downloading);
            Glide.with(mContext).load(fileInfo.VideoPath).crossFade().into(viewHolderVideo.coverImage);

        }
    }

    /**
     * 重新计算布局参数的大小
     */
    public void changeLayout() {
        calculateDrawInfo();
        mCoverViewParams = new RelativeLayout.LayoutParams(mCoverImageInfo.CoverImageWidth, mCoverImageInfo.CoverImageHeight);
        notifyDataSetChanged();
    }

    /**
     * 修改当前列的数量
     *
     * @param mSpanCount 当前列数
     */
    public void setSpanCount(int mSpanCount) {
        this.mSpanCount = mSpanCount;
    }

    /**
     * 更新数据源
     *
     * @param coverImageList 新数据源
     */
    public void notifySetListDataChanged(List<FileInfo> coverImageList) {
        this.mCoverImageList = coverImageList;
        notifyDataSetChanged();
//        localFileAdapter.notifyItemRangeChanged(0,mImageList.size());
    }


    //封面图像大小信息
    private static class CoverImageInfo {
        int CoverImageHeight;
        int CoverImageWidth;
    }

    /**
     * 计算长度和宽度
     */
    private void calculateDrawInfo() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int totalSpace = Util.convertDpToPx(mContext, DIVIDER_WIDTH) * (mSpanCount - 1);
        mCoverImageInfo.CoverImageWidth = (widthPixels - totalSpace) / mSpanCount;
        mCoverImageInfo.CoverImageHeight = (widthPixels - totalSpace) / mSpanCount;
    }

    /**
     * 圧縮率を計算する
     *
     * @param path 画像パス
     * @return 圧縮率
     */
    private static int calculateInSampleSize(String path, BitmapFactory.Options bitmapFactoryOptions) {
        bitmapFactoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bitmapFactoryOptions);
        final int height = bitmapFactoryOptions.outHeight;
        final int width = bitmapFactoryOptions.outWidth;
        int inSampleSize = 1;
        if (height > mCoverImageInfo.CoverImageHeight || width > mCoverImageInfo.CoverImageWidth) {
            //必要なアスペクトの最大値を用いて比率を計算した
            final int suitedValue = Math.max(mCoverImageInfo.CoverImageHeight, mCoverImageInfo.CoverImageWidth);
            final int heightRatio = Math.round((float) height / (float) suitedValue);
            final int widthRatio = Math.round((float) width / (float) suitedValue);
            inSampleSize = Math.max(heightRatio, widthRatio);
        }
        return inSampleSize;
    }


    @Override
    public int getItemViewType(int position) {
        if (mCoverImageList.get(position).type == FileInfo.Type.img) {
            return TYPE_ITEM;
        } else
            return TYPE_ITEM2;
    }

    /**
     * 设定监听
     *
     * @param listener
     */
    public void setSelectImageListener(onSelectImageListener listener) {
        mOnSelectImageListener = listener;
    }

    public void setCKListener(OnCKListener listener) {
        onCKListener = listener;
    }


    public class VH extends RecyclerView.ViewHolder {

        private ImageView coverImage;
        private CheckBox checkBox;
        private FrameLayout mCoverView;
        private TextView tvFileInfo;
        private TextView ckTv;

        public VH(View v) {
            super(v);
            coverImage = v.findViewById(R.id.coverImage);
            mCoverView = v.findViewById(R.id.mCoverView);
            checkBox = v.findViewById(R.id.ck);
            tvFileInfo = v.findViewById(R.id.file_info);
            ckTv = v.findViewById(R.id.ckTv);
        }
    }

    public class ViewHolderVideo extends RecyclerView.ViewHolder {
        private ImageView coverImage;
        private CheckBox checkBox;
        private FrameLayout mCoverView;
        private TextView textView, tvFileInfo;
        private TextView ckTv;

        public ViewHolderVideo(@NonNull View itemView) {
            super(itemView);
            coverImage = itemView.findViewById(R.id.coverImage);
            mCoverView = itemView.findViewById(R.id.mCoverView);
            checkBox = itemView.findViewById(R.id.ck);
            textView = itemView.findViewById(R.id.textView1);
            tvFileInfo = itemView.findViewById(R.id.file_info);
            ckTv = itemView.findViewById(R.id.ckTv);
        }
    }


    /**
     * 事件接口
     */
    public interface onSelectImageListener {

        void onClick(FileInfo info);

        void setOnLongClickListener(FileInfo info);
    }

    public interface OnCKListener {
        void onCheckedChanged(FileInfo info, Boolean isCk);
    }

}