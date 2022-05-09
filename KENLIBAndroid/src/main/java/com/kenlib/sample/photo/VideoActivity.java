package com.kenlib.sample.photo;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.util.StatusBarUtil;
import com.kenlib.util.Util;


public class VideoActivity extends AppCompatActivity {

    private static String mMP4Path;
    VideoView mVideoView;
    MediaController mMediaController;
    TextView pathTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.titleHide(this);
        StatusBarUtil.setWindowStatusBarColor(this, R.color.hs);
        setContentView(R.layout.video_demo);

        pathTv = findViewById(R.id.path_tv);
        mVideoView = findViewById(R.id.video_view);
        mMediaController = new MediaController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMP4Path = getIntent().getStringExtra("url");

//        mMP4Path = FileUtil.getRootFilePath() + "1.mp4";

        if (!TextUtils.isEmpty(mMP4Path)) {
            mVideoView.setVideoPath(mMP4Path);
            mVideoView.setMediaController(mMediaController);
            mVideoView.seekTo(0);
            mVideoView.requestFocus();
            mVideoView.start();

//            mVideoView.pause();
//            mVideoView.stopPlayback();
//            mVideoView=null;

            pathTv.setText(mMP4Path);
        }

    }
}
