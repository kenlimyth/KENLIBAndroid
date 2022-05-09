package com.kenlib.sample;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.progress.ratingbar.RatingBar;

import com.kenlib.android.R;
import com.kenlib.util.Util;

/**
 * XUI  星级评分控件，xui使用demo
 */
public class XUIDemo extends AppCompatActivity {

    RatingBar ratingBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XUI.initTheme(this);
//        setTheme(R.style.CustomAppTheme);

        setContentView(R.layout.activity_ratingbar);

        ratingBar = findViewById(R.id.rating_bar);

        ratingBar.setOnRatingChangeListener((ratingBar, rating) -> Util.showToast(XUIDemo.this,"当前星级：" + rating));


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleBottomSheetList();
            }
        });

    }

    private void showSimpleBottomSheetList() {
        new BottomSheet.BottomListSheetBuilder(XUIDemo.this)
                .setTitle("标题")
                .addItem("Item 1")
                .addItem("Item 2")
                .addItem("Item 3")
                .setIsCenter(true)
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();
//                    XToastUtils.toast("Item " + (position + 1));
                })
                .build()
                .show();
    }

}
