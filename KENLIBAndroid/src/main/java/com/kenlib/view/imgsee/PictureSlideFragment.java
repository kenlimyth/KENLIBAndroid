package com.kenlib.view.imgsee;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.kenlib.android.R;
import com.kenlib.sample.photo.dto.FileInfo;
import com.kenlib.sample.photo.VideoActivity;

public class PictureSlideFragment extends Fragment {

    private FileInfo fileInfo;
    private PhotoViewAttacher mAttacher;
    private PhotoView photoView;

    public static PictureSlideFragment newInstance(FileInfo fileInfo) {
        PictureSlideFragment f = new PictureSlideFragment();
        Bundle args = new Bundle();
        args.putSerializable("fileInfo", fileInfo);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileInfo = getArguments() != null ? (FileInfo) getArguments().getSerializable("fileInfo") : new FileInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (fileInfo.type == FileInfo.Type.img) {

            View v = inflater.inflate(R.layout.fragment_picture_slide, container, false);
            photoView = (PhotoView) v.findViewById(R.id.iv_main_pic);
            TextView tvFileInfo =  v.findViewById(R.id.file_info);
            tvFileInfo.setText(fileInfo.localImageUrl + "\n" + fileInfo.size + "M");
            //使用PhotoViewAttacher为图片添加支持缩放、平移的属性
            mAttacher = new PhotoViewAttacher(photoView);

            Glide.with(getActivity()).load(fileInfo.localImageUrl).crossFade().into(new GlideDrawableImageViewTarget(photoView) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                    super.onResourceReady(resource, animation);

                    //调用PhotoViewAttacher的update()方法，使图片重新适配布局
                    mAttacher.update();
                }
            });
            return v;

        } else {
            View v = inflater.inflate(R.layout.fragment_video_slide, container, false);
            photoView = (PhotoView) v.findViewById(R.id.iv_main_pic);
            TextView textView = v.findViewById(R.id.play);
            TextView tvFileInfo =  v.findViewById(R.id.file_info);
            tvFileInfo.setText(fileInfo.VideoPath + "\n" + fileInfo.size + "M");
            //使用PhotoViewAttacher为图片添加支持缩放、平移的属性
            mAttacher = new PhotoViewAttacher(photoView);

            Glide.with(getActivity()).load(fileInfo.VideoPath).crossFade().into(new GlideDrawableImageViewTarget(photoView) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                    super.onResourceReady(resource, animation);

                    //调用PhotoViewAttacher的update()方法，使图片重新适配布局
                    mAttacher.update();
                }
            });

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(), VideoActivity.class);
                    intent.putExtra("url", fileInfo.VideoPath);
                    startActivity(intent);
                }
            });

            return v;

        }

    }
}
