package com.kenlib.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 监听 Activitiy
 */
public class ActivitiyListener implements Application.ActivityLifecycleCallbacks {

    /**
     * 当前的类名（相当于getName（））
     */
    private static String currentActivityName = "";

    /**
     * 当前活动的数量
     **/
    private int activityCount = 0;

    /**
     * 当前活动状态
     **/
    private static boolean isBackgroundToForeGround;


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        isBackgroundToForeGround = false;

        activityCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivityName = activity.getClass().getName();
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    /**
     * 获取当前活动的类名
     */
    public static String getCurrentActivityName() {
        return currentActivityName;
    }

    /**
     * 当前活动状态获取
     */
    public static boolean getCurrentActivityStatus() {
        return isBackgroundToForeGround;
    }
}