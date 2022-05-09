package com.kenlib.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.kenlib.android.R;
import com.kenlib.util.AnimationUtil;

/**
 * 动画演示
 */
public class AnimationDemo extends AppCompatActivity {

    LinearLayout ll_container;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_demo);

        ll_container = findViewById(R.id.ll_container);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //旋转,透明度动画
//                AnimationUtil.rotation(findViewById(R.id.ll_container));
//                AnimationUtil.alpha(findViewById(R.id.ll_container));

                //以 Fade 动画显示本浮层
//                if (ll_container.getVisibility() == View.GONE) {
//                    AnimationUtil.fadeIn(ll_container, 500, null, true);
//                } else {
//                    AnimationUtil.fadeOut(ll_container, 500, null, true);
//                }

                //以 Slide（上进上出）动画显示本浮层
//                if (ll_container.getVisibility() == View.GONE) {
//                    AnimationUtil.slideIn(ll_container, 500, null, true, TOP_TO_BOTTOM);
//                } else {
//                    AnimationUtil.slideOut(ll_container, 500, null, true, BOTTOM_TO_TOP);
//                }

                //以 Slide（左进右出）动画显示本浮层
//                if (ll_container.getVisibility() == View.GONE) {
//                    AnimationUtil.slideIn(ll_container, 500, null, true, LEFT_TO_RIGHT);
//                } else {
//                    AnimationUtil.slideOut(ll_container, 500, null, true, LEFT_TO_RIGHT);
//                }

                //的做背景闪动的动画
//                AnimationUtil.playBackgroundBlinkAnimation(ll_container, ContextCompat.getColor(AnimationDemo.this
//                        , R.color.xui_config_color_pure_yellow));


//                AnimationUtil.doAlphaAnimation(ll_container);
//                AnimationUtil.doTranslationAnimation(ll_container);
//                AnimationUtil.doScaleAnimation(ll_container);
//                AnimationUtil.doRotationAnimation(ll_container);
//                AnimationUtil.doValueAnimator(ll_container);
//                AnimationUtil.doTypeEvaluator(ll_container);
                AnimationUtil.doComposeAnimation1(ll_container);
//                AnimationUtil.doComposeAnimation2(ll_container);
            }
        });

    }


}
