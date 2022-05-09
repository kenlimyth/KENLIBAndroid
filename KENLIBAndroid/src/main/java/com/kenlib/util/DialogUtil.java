package com.kenlib.util;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.kenlib.android.R;

/**
 * 常用对话框
 */
public class DialogUtil {

    private static String TAG = "KEN";

    /**
     * 加载中对话框
     *
     * @param context
     * @param msg
     */
    public static ProgressDialog showIngDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        //progressDialog.setTitle("这是一个 progressDialog");//2.设置标题
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setMessage(msg);//3.设置显示内容
        progressDialog.setCancelable(false);//4.设置可否用back键关闭对话框
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    progressDialog.dismiss();
                }
                return false;
            }
        });
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 加载中对话框 关闭
     *
     * @param ing
     */
    public static void closeProgressDialog(ProgressDialog ing) {
        if (ing != null && ing.isShowing()) {
            ing.dismiss();
        }
    }

    /**
     * WaitDialog
     *
     * @param context
     * @return
     */
    public static DialogIng showIngWaitDialog(Context context, String msg) {
        DialogIng mWaitDialog = DialogIng.getInstance(context, msg);
        mWaitDialog.show();
        return mWaitDialog;
    }

    /**
     * 对话框,自定义view
     */
    public static void showD(Context activity, int viewXml, Iview iview) {

        AlertDialog alertDialog;

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.custom_dialog));
        // 根据Layout资源，inflater一个View对象
        View view = LayoutInflater.from(activity).inflate(viewXml, null);

        builder.setView(view);
        alertDialog = builder.show();
        alertDialog.setCancelable(false);
        iview.setView(view, alertDialog);
    }

    /**
     * 对话框，提示
     *
     * @param activity
     * @param msg
     */
    public static void showD(Context activity, String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(activity, R.style.custom_dialog));
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", null);
        dialog.show();
    }

    /**
     * 对话框，确认
     *
     * @param activity
     * @param title
     * @param msg
     */
    public static void showDConfirm(Context activity, String title, String msg) {
        showDConfirm(activity, title, msg, null);
    }

    public static void showDConfirm(Context activity, String title, String msg, IHandler iHandler) {

        // 根据Layout资源，inflater一个View对象
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog3, null);

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.show();

        TextView titleTv = view.findViewById(R.id.title);
        titleTv.setText(title);
        TextView msgTv = view.findViewById(R.id.msg);
        msgTv.setText(msg);
        Dialog finalAlertDialog = dialog;
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalAlertDialog.cancel();
            }
        });
        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iHandler != null) {
                    iHandler.todo();

                }
                finalAlertDialog.cancel();
            }
        });

        int displayWidth = ScreenUtil.getScreenWidth(activity);
        WindowManager.LayoutParams p = finalAlertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (displayWidth * 0.8);    //宽度设置为屏幕的0.5
//        p.height = (int) (displayHeight * 0.5);    //宽度设置为屏幕的0.5
        finalAlertDialog.getWindow().setAttributes(p);

    }

    /**
     * POP对话框
     *
     * @param activity
     * @param viewXml
     * @param height
     * @param iview
     */
    public static void showPW(final Context activity, int viewXml, int height,
                              IviewP iview) {// 显示POP

        PopupWindow popupWindow;
        View view = LayoutInflater.from(activity).inflate(viewXml, null);

        if (height == 0) {
            popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, true);
        } else {
            popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
                    height, true);
        }

        popupWindow.setTouchable(true);// 点击空白处的时候PopupWindow会消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(activity
                .getResources(), (Bitmap) null));

        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar_kendll);
        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.START, 0, 0);
//        popupWindow.showAsDropDown(parent); // show的另外一种方式

        final Activity activity1 = ((Activity) activity);

        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = activity1.getWindow().getAttributes();
        lp.alpha = 0.7f;
        //此行代码主要是解决在华为手机上半透明效果无效的bug
        activity1.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity1.getWindow().setAttributes(lp);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {

                WindowManager.LayoutParams lp = activity1.getWindow().getAttributes();
                lp.alpha = 1f;
                activity1.getWindow().setAttributes(lp);
            }
        });


        iview.setView(view, popupWindow);

    }

    public static void showPW(final Context activity, int viewXml,
                              IviewP iview) {// 显示POP

        showPW(activity, viewXml, 0, iview);

    }

    /**
     * 通知栏，通知提示
     *
     * @param context
     * @param hangIntent
     * @param title
     * @param msg
     * @param notifyId
     */
    public static void showNotification(Context context, Intent hangIntent, String title, String msg, int notifyId) {
        Notification notification;
        NotificationManager manager;
        manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

//		Intent hangIntent = new Intent(this, LBDemo.class);
        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 1001, hangIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String CHANNEL_ID = "your_custom_id";//应用频道Id唯一值， 长度若太长可能会被截断，
        String CHANNEL_NAME = "your_custom_name";//最长40个字符，太长会被截断

        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.mipmap.arrow_icon)
                .setContentIntent(hangPendingIntent)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.default_cooperate))
                .setAutoCancel(true)//点击消失
                .build();

        //Android 8.0 以上需包添加渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(notificationChannel);
        }

        manager.notify(notifyId, notification);
    }

    /**
     * VIEW接口
     */
    public static interface Iview {

        void setView(View view, AlertDialog alertDialog);

    }

    public static interface IviewP {

        void setView(View view, PopupWindow popupWindow);

    }

    public static interface IHandler {

        void todo();

    }

    public static abstract class ABSHandler {

        public void todo() {
        }

        public void todo(String s) {
        }

        public void todo(View view, ABSHandler absHandler) {
        }

    }

}
