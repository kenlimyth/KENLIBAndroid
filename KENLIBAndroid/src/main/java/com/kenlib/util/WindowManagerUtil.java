package com.kenlib.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.kenlib.android.R;

/**
 * WindowManager  悬浮窗 ,画中画,浮在APP最上层
 */
public class WindowManagerUtil {

    static int mLayoutId = R.layout.window_manager_view;
    static Util.ABSHandler mAbsHandler;

    public static void show(Context context, int layoutId, Util.ABSHandler absHandler) {

        mLayoutId = layoutId;
        mAbsHandler = absHandler;
        showWindowManager(context);
    }
    public static void show(Context context, Util.ABSHandler absHandler) {

        mAbsHandler = absHandler;
        showWindowManager(context);
    }

    private static void showWindowManager(Context context) {
        float[] x = new float[1];
        float[] y = new float[1];
        WindowManager mWindowManager;
        WindowManager.LayoutParams params;
        View mWindowsView;

        // 取得系统窗体
        mWindowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        // 窗体的布局样式
        params = new WindowManager.LayoutParams();

        // 设置窗体焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置显示的模式
        params.format = PixelFormat.RGBA_8888;
        // 设置对齐的方法
        params.gravity = Gravity.TOP | Gravity.LEFT;
        // 设置窗体宽度和高度
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }

        //将指定View解析后添加到窗口管理器里面
        mWindowsView = View.inflate(context, mLayoutId, null);

        mWindowManager.addView(mWindowsView, params);

        mWindowsView.setOnTouchListener(new View.OnTouchListener() {
            float mTouchStartX;
            float mTouchStartY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                x[0] = event.getRawX();
                y[0] = event.getRawY() - 25;//25状态栏大小
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //原始坐标减去移动坐标
                        params.x = (int) (x[0] - mTouchStartX);
                        params.y = (int) (y[0] - mTouchStartY);
                        mWindowManager.updateViewLayout(mWindowsView, params);
                        Log.i("main", "x值=" + x[0] + "\ny值=" + y[0] + "\nmTouchX" + mTouchStartX + "\nmTouchY=" + mTouchStartY);
                        break;
                }
                return true;
            }
        });

        if (mAbsHandler != null) {
            mAbsHandler.todo(mWindowsView ,new Util.ABSHandler(){
                @Override
                public void todo() {
                    mWindowManager.removeView(mWindowsView);
                }
            });
        }

//        mWindowsView.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mWindowManager.removeView(mWindowsView);
//            }
//        });
    }
}
