package com.kenlib.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * view 属性动画
 */
public class AnimationUtil {

    /**
     * 方向
     */
    public enum Direction {
        /**
         * 左 -> 右
         */
        LEFT_TO_RIGHT,
        /**
         * 上 -> 下
         */
        TOP_TO_BOTTOM,
        /**
         * 右 -> 左
         */
        RIGHT_TO_LEFT,
        /**
         * 下 -> 上
         */
        BOTTOM_TO_TOP
    }

    /**
     * 旋转
     */
    public static void rotation(View ivAdd) {
        ObjectAnimator rotation;
        rotation = ObjectAnimator.ofFloat(ivAdd, "rotation", -45, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotation);
        animatorSet.setDuration(500).start();
    }

    /**
     * 透明度
     *
     * @param ivAdd
     */
    public static void alpha(View ivAdd) {
        ObjectAnimator alpha;
        alpha = ObjectAnimator.ofFloat(ivAdd, "alpha", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alpha);
        animatorSet.setDuration(500).start();
    }

    /**
     * 透明度动画
     *
     * @param view
     */
    public static void doAlphaAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1, 0, 1);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 平移动画
     *
     * @param view
     */
    public static void doTranslationAnimation(View view) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", 0, 200, 0, -200, 0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "translationY", 0, 200, 0, -200, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).before(animatorY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * 缩放动画
     *
     * @param view
     */
    public static void doScaleAnimation(View view) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1F, 0.5F, 1F, 1.5F, 1F);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1F, 0.5F, 1F, 1.5F, 1F);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * 旋转动画
     *
     * @param view
     */
    public static void doRotationAnimation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 0, 360, 0);
        animator.setDuration(2000);
        //加速查值器，参数越大，速度越来越快
        animator.setInterpolator(new AccelerateInterpolator(2));
//        //减速差值起，和上面相反
//        animator.setInterpolator(new DecelerateInterpolator(10));
//        //先加速后减速插值器
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        //张力值，默认为2，T越大，初始的偏移越大，而且速度越快
//        animator.setInterpolator(new AnticipateInterpolator(3));
//        //张力值tension，默认为2，张力越大，起始时和结束时的偏移越大
//        animator.setInterpolator(new AnticipateOvershootInterpolator(6));
//        //弹跳插值器
//        animator.setInterpolator(new BounceInterpolator());
//        //周期插值器
//        animator.setInterpolator(new CycleInterpolator(2));
//        //线性差值器,匀速
//        animator.setInterpolator(new LinearInterpolator());
//        //张力插值器，扩散反弹一下
//        animator.setInterpolator(new OvershootInterpolator(2));
        animator.start();
    }


    /**
     * 值动画，适合构建复杂的动画
     *
     * @param view
     */
    public static void doValueAnimator(View view) {
        //值的变化，与控件无关
        ValueAnimator animator = ValueAnimator.ofFloat(1, 0, 1);
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            view.setScaleX(value);
            view.setScaleY(value);
            view.setAlpha(value);
            view.setRotation(360 * (1 - value));
        });
        animator.setDuration(2000).start();
    }

    /**
     * 估值器（实现重力下落的效果）
     *
     * @param view
     */
    public static  void doTypeEvaluator(final View view) {
        ValueAnimator animator = new ValueAnimator();
        animator.setDuration(3000);
        animator.setObjectValues(new PointF(0, 0));
        final PointF pointF = new PointF();
        animator.setEvaluator((fraction, startValue, endValue) -> {
            //fraction是运动中的匀速变化的值
            //根据重力计算实际的运动y=vt=0.5*g*t*t
            //g越大效果越明显
            pointF.x = 100 * (fraction * 5);
            pointF.y = 0.5f * 300f * (fraction * 5) * (fraction * 5);
            return pointF;
        });
        animator.addUpdateListener(animation -> {
            PointF p = (PointF) animation.getAnimatedValue();
            view.setX(p.x);
            view.setY(p.y);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //还原到原始的位置
                view.setTranslationX(0);
                view.setTranslationY(0);
            }
        });
        animator.start();
    }


    /**
     * 组合动画
     *
     * @param view
     */
    public static  void doComposeAnimation1(View view) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0, 1);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0, 1);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", 0, 360, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alpha).with(scaleX).with(scaleY).with(rotation);
        animatorSet.setDuration(2000).start();
    }


    /**
     * 组合动画
     *
     * @param view
     */
    public static  void doComposeAnimation2(View view) {
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1, 0, 1);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1, 0, 1);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1, 0, 1);
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation", 0, 360, 0);

        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, scaleX, scaleY, rotation);
        animator.setDuration(2000).start();
    }



    /**
     * <p>对 View 做透明度变化的进场动画。</p>
     * <p>相关方法 {@link #fadeOut(View, int, Animation.AnimationListener, boolean)}</p>
     *
     * @param view            做动画的 View
     * @param duration        动画时长(毫秒)
     * @param listener        动画回调
     * @param isNeedAnimation 是否需要动画
     */
    public static AlphaAnimation fadeIn(View view, int duration, Animation.AnimationListener listener, boolean isNeedAnimation) {
        if (view == null) {
            return null;
        }
        if (isNeedAnimation) {
            view.setVisibility(View.VISIBLE);
            AlphaAnimation alpha = new AlphaAnimation(0, 1);
            alpha.setInterpolator(new DecelerateInterpolator());
            alpha.setDuration(duration);
            alpha.setFillAfter(true);
            if (listener != null) {
                alpha.setAnimationListener(listener);
            }
            view.startAnimation(alpha);
            return alpha;
        } else {
            view.setAlpha(1);
            view.setVisibility(View.VISIBLE);
            return null;
        }
    }

    /**
     * <p>对 View 做透明度变化的退场动画</p>
     * <p>相关方法 {@link #fadeIn(View, int, Animation.AnimationListener, boolean)}</p>
     *
     * @param view            做动画的 View
     * @param duration        动画时长(毫秒)
     * @param listener        动画回调
     * @param isNeedAnimation 是否需要动画
     */
    public static AlphaAnimation fadeOut(final View view, int duration, final Animation.AnimationListener listener, boolean isNeedAnimation) {
        if (view == null) {
            return null;
        }
        if (isNeedAnimation) {
            AlphaAnimation alpha = new AlphaAnimation(1, 0);
            alpha.setInterpolator(new DecelerateInterpolator());
            alpha.setDuration(duration);
            alpha.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (listener != null) {
                        listener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                    if (listener != null) {
                        listener.onAnimationEnd(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (listener != null) {
                        listener.onAnimationRepeat(animation);
                    }
                }
            });
            view.startAnimation(alpha);
            return alpha;
        } else {
            view.setVisibility(View.GONE);
            return null;
        }
    }


    /**
     * <p>对 View 做上下位移的进场动画</p>
     * <p>相关方法 {@link #slideOut(View, int, Animation.AnimationListener, boolean, Direction)}</p>
     *
     * @param view            做动画的 View
     * @param duration        动画时长(毫秒)
     * @param listener        动画回调
     * @param isNeedAnimation 是否需要动画
     * @param direction       进场动画的方向
     * @return 动画对应的 Animator 对象, 注意无动画时返回 null
     */
    @Nullable
    public static TranslateAnimation slideIn(final View view, int duration, final Animation.AnimationListener listener, boolean isNeedAnimation, Direction direction) {
        if (view == null) {
            return null;
        }
        if (isNeedAnimation) {
            TranslateAnimation translate = null;
            switch (direction) {
                case LEFT_TO_RIGHT:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case TOP_TO_BOTTOM:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case RIGHT_TO_LEFT:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case BOTTOM_TO_TOP:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                default:
                    break;
            }
            translate.setInterpolator(new DecelerateInterpolator());
            translate.setDuration(duration);
            translate.setFillAfter(true);
            if (listener != null) {
                translate.setAnimationListener(listener);
            }
            view.setVisibility(View.VISIBLE);
            view.startAnimation(translate);
            return translate;
        } else {
            view.clearAnimation();
            view.setVisibility(View.VISIBLE);
            return null;
        }
    }

    /**
     * <p>对 View 做上下位移的退场动画</p>
     * <p>相关方法 {@link #slideIn(View, int, Animation.AnimationListener, boolean, Direction)}</p>
     *
     * @param view            做动画的 View
     * @param duration        动画时长(毫秒)
     * @param listener        动画回调
     * @param isNeedAnimation 是否需要动画
     * @param direction       进场动画的方向
     * @return 动画对应的 Animator 对象, 注意无动画时返回 null
     */
    @Nullable
    public static TranslateAnimation slideOut(final View view, int duration, final Animation.AnimationListener listener, boolean isNeedAnimation, Direction direction) {
        if (view == null) {
            return null;
        }
        if (isNeedAnimation) {
            TranslateAnimation translate = null;
            switch (direction) {
                case LEFT_TO_RIGHT:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case TOP_TO_BOTTOM:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
                    );
                    break;
                case RIGHT_TO_LEFT:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f
                    );
                    break;
                case BOTTOM_TO_TOP:
                    translate = new TranslateAnimation(
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f
                    );
                    break;
                default:
                    break;
            }
            translate.setInterpolator(new DecelerateInterpolator());
            translate.setDuration(duration);
            translate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (listener != null) {
                        listener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                    if (listener != null) {
                        listener.onAnimationEnd(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (listener != null) {
                        listener.onAnimationRepeat(animation);
                    }
                }
            });
            view.startAnimation(translate);
            return translate;
        } else {
            view.clearAnimation();
            view.setVisibility(View.GONE);
            return null;
        }
    }


    /**
     * 对 View 的做背景闪动的动画
     */
    public static void playBackgroundBlinkAnimation(final View v, @ColorInt int bgColor) {
        if (v == null) {
            return;
        }
        int[] alphaArray = new int[]{0, 255, 0};
        playViewBackgroundAnimation(v, bgColor, alphaArray, 300);
    }

    public static void playViewBackgroundAnimation(final View v, @ColorInt int bgColor, int[] alphaArray, int stepDuration) {
        playViewBackgroundAnimation(v, bgColor, alphaArray, stepDuration, null);
    }

    /**
     * 对 View 做背景色变化的动作
     *
     * @param v            做背景色变化的View
     * @param bgColor      背景色
     * @param alphaArray   背景色变化的alpha数组，如 int[]{255,0} 表示从纯色变化到透明
     * @param stepDuration 每一步变化的时长
     * @param endAction    动画结束后的回调
     */
    public static Animator playViewBackgroundAnimation(final View v, @ColorInt int bgColor, int[] alphaArray, int stepDuration, final Runnable endAction) {
        int animationCount = alphaArray.length - 1;

        Drawable bgDrawable = new ColorDrawable(bgColor);
        final Drawable oldBgDrawable = v.getBackground();
        setBackgroundKeepingPadding(v, bgDrawable);

        List<Animator> animatorList = new ArrayList<>();
        for (int i = 0; i < animationCount; i++) {
            ObjectAnimator animator = ObjectAnimator.ofInt(v.getBackground(), "alpha", alphaArray[i], alphaArray[i + 1]);
            animatorList.add(animator);
        }

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(stepDuration);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setBackgroundKeepingPadding(v, oldBgDrawable);
                if (endAction != null) {
                    endAction.run();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.playSequentially(animatorList);
        animatorSet.start();
        return animatorSet;
    }

    public static void setBackgroundKeepingPadding(View view, Drawable drawable) {
        int[] padding = new int[]{view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom()};
        setBackground(view, drawable);
        view.setPadding(padding[0], padding[1], padding[2], padding[3]);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}





