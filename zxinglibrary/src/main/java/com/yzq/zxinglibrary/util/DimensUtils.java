package com.yzq.zxinglibrary.util;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.DimenRes;

/**
 * @author: By Zxu
 * @date: 2021/9/30
 */
public class DimensUtils {
    public static float pixelsFromSpResource(Context context, @DimenRes int sizeRes) {
        final Resources res = context.getResources();
        return res.getDimension(sizeRes) / res.getDisplayMetrics().density;
    }

    /**
     * dp单位转换为px
     * @param context 上下文，需要通过上下文获取到当前屏幕的像素密度
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue){
        return (int)(dpValue * (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /**
     * px单位转换为dp
     * @param context 上下文，需要通过上下文获取到当前屏幕的像素密度
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue){
        return (int)(pxValue / (context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /** sp转换成px */
    public static int sp2px(Context context,float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /** px转换成sp */
    public static int px2sp(Context context,float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取屏幕dpi值
     * @param context
     * @return
     */
    public static float getDensityDpi(Context context){
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取dp和px换算比例
     * @param context
     * @return
     */
    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取设备屏幕宽度
     * @param context
     * @return
     */
    public static final int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备屏幕高度
     * @param context
     * @return
     */
    public static final int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}