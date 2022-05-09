package com.kenlib.sample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.kenlib.android.R;
import com.kenlib.util.FileUtil;

/**
 * VideoDemo  mp4视频播放
 */
public class VideoDemo extends AppCompatActivity {

    private static String mMP4Path;
    VideoView mVideoView;
    MediaController mMediaController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.video_demo);

        mMP4Path = FileUtil.getRootFilePath() + "1.mp4";

        TextView pathTv = findViewById(R.id.path_tv);
        mVideoView = findViewById(R.id.video_view);
        mMediaController = new MediaController(this);
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
