package com.kenlib.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.kenlib.sample.photo.dto.FileInfo;
import com.kenlib.version.CheckVersion;
import com.kenlib.version.CheckVersion.checkjg;
import com.kenlib.sqlite.MySqlLite;
import com.kenlib.service.GPSService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActionBar.LayoutParams;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;

import androidx.fragment.app.FragmentActivity;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kenlib.android.R;

import static com.kenlib.util.ImgUtil.isImageFile;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;

/**
 * 常用函数库
 */
public class Util {

    private static String TAG = "ken";

    /**
     * sqlite执行sql
     */
    public static boolean exeSQLSQLiteDB(Context context, String sql) {
        if ("".equals(sql)) {
            return false;
        }

        MySqlLite mySqlLite = new MySqlLite(context);
        return mySqlLite.exeSql(sql);

    }

    /**
     * 取数据sqlite
     */
    public static List<Map<String, String>> getListMapFromSQLiteDB(
            Context context, String sql) {

        if ("".equals(sql)) {
            return null;
        }
        MySqlLite mySqlLite = new MySqlLite(context);
        List<Map<String, String>> lists = mySqlLite.getListMap(sql);

        return lists;
    }

    /**
     * 取一个字段值
     */
    public static String getOneZDFromSQLiteDB(Context Context, String sql,
                                              String zd) {
        String telString = "";
        List<Map<String, String>> lists = Util.getListMapFromSQLiteDB(
                Context, sql);
        if (lists != null && lists.size() > 0) {
            telString = lists.get(0).get(zd);

        }
        return telString;
    }

    /**
     * 取当前登陆的userid
     */
    public static String getUseridFromSQLiteDB(Context Context) {
        String telString = "";
        List<Map<String, String>> lists = Util.getListMapFromSQLiteDB(
                Context, " select * from user where id=1 ");
        if (lists != null && lists.size() > 0) {
            telString = lists.get(0).get("userid");

        }
        return telString;
    }


    /**
     * 开启时钟 service
     */
    public static void startAM(Context context, Class<?> toc, int seconds) {
        // 定时时钟
        AlarmManager aManager = (AlarmManager) context
                .getSystemService(Service.ALARM_SERVICE);
        // 指定启动Service组件
        Intent intent = new Intent(context, GPSService.class);
        // 创建PendingIntent对象
        final PendingIntent pi = PendingIntent
                .getService(context, 0, intent, 0);
        // aManager.setRepeating(AlarmManager.RTC_WAKEUP,
        // System.currentTimeMillis()+seconds, seconds, pi);
        aManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, seconds, pi);
    }

    /***
     * 判断service运行状态, 服务的完整包名
     */
    public static boolean isServiceRunning(Context context,
                                           String serviceFullPageName) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceFullPageName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }


    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 打开相机拍照
     */
    public static void toPhto(String fileUrl, Activity activity, int requestCode) {

        if ("".equals(fileUrl)) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(fileUrl)));
        activity.startActivityForResult(intent, requestCode);

        // 文件夹进行扫描
        MediaScannerConnection.scanFile(activity, new String[]{Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .getPath()
                + "/" + fileUrl}, null, null);
    }

    /**
     * 打开相册
     */
    public static void toXC(Activity activity, int requestCode) {

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        activity.startActivityForResult(intent, requestCode);
        Util.showLogDebug("a------------" + requestCode);
    }

    /**
     * 更新相册
     *
     * @param f
     * @param context
     */
    public static void updateXC(File f, Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }


    /**
     * 加载本地相册图片
     *
     * @param context
     * @return
     */
    public static ArrayList<FileInfo> getLocalPhoto(Context context) {
        ArrayList<FileInfo> mImageList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            assert cursor != null;
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                long modifyTime = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)) / 1024; //单位kb
                if (size < 0) {
                    //某些设备获取size<0，直接计算
                    Log.e("dml", "this video size < 0 " + path);
                    size = new File(path).length() / 1024;
                }
                if (isImageFile(path)) {
                    FileInfo info = new FileInfo();
                    info.localImageUrl = path;
                    info.type = FileInfo.Type.img;
                    info.size = Util.dataHandler(size, 1024);
                    info.modifyTime = modifyTime;
                    mImageList.add(info);
                }
            }
            Collections.reverse(mImageList);

        } catch (Exception ex) {
            Util.showLogDebug(ex.getMessage());
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
        return mImageList;

    }

    /**
     * 获取手机中所有视频的信息
     * 在子线程
     */
    public static ArrayList<FileInfo> getLocalVideo(Context context) {

        ArrayList<FileInfo> allPhotosTemp = new ArrayList<>();//所有照片
        Uri mImageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] proj = {MediaStore.Video.Thumbnails._ID
                , MediaStore.Video.Thumbnails.DATA
                , MediaStore.Video.Media.DURATION
                , MediaStore.Video.Media.SIZE
                , MediaStore.Video.Media.DISPLAY_NAME
                , MediaStore.Video.Media.DATE_MODIFIED};
        Cursor mCursor = context.getContentResolver().query(mImageUri,
                proj,
                MediaStore.Video.Media.MIME_TYPE + "=?",
                new String[]{"video/mp4"},
                MediaStore.Video.Media.DATE_MODIFIED + " desc");
        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                // 获取视频的路径
                int videoId = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media._ID));
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                int duration = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                long size = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.SIZE)) / 1024; //单位kb
                if (size < 0) {
                    //某些设备获取size<0，直接计算
                    Log.e("dml", "this video size < 0 " + path);
                    size = new File(path).length() / 1024;
                }
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                long modifyTime = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));//暂未用到

                //提前生成缩略图，再获取：http://stackoverflow.com/questions/27903264/how-to-get-the-video-thumbnail-path-and-not-the-bitmap
                MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), videoId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
                String[] projection = {MediaStore.Video.Thumbnails._ID, MediaStore.Video.Thumbnails.DATA};
                Cursor cursor = context.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI
                        , projection
                        , MediaStore.Video.Thumbnails.VIDEO_ID + "=?"
                        , new String[]{videoId + ""}
                        , null);
                String thumbPath = "";
                while (cursor.moveToNext()) {
                    thumbPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                }
                cursor.close();
                // 获取该视频的父路径名
//                        String dirPath = new File(path).getParentFile().getAbsolutePath();

                allPhotosTemp.add(new FileInfo(FileInfo.Type.mp4, thumbPath, path, Util.dataHandler(size, 1024), modifyTime));

            }
            mCursor.close();
        }
        return allPhotosTemp;
    }


    /**
     * 当前时间，格式yyyy-MM-dd HH:mm:ss
     */
    public static String getTimeNow() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        String time = df.format(new Date());
        return time;
    }

    /**
     * 时间差
     */
    public static long getTimeC(String beng, String end) throws ParseException {

        if (beng.indexOf("-") == -1 || end.indexOf("-") == -1) {
            return -1;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        Date bDate = df.parse(beng);
        Date eDate = df.parse(end);

        long diff = eDate.getTime() - bDate.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }

    /**
     * 系统默认提示
     *
     * @param context
     * @param msg
     */
    @SuppressLint("WrongConstant")
    public static void showToast(Context context, String msg) {

        Toast.makeText(context, msg, 1000).show();
    }

    /**
     * 提示Toast,自定义
     */
    @SuppressLint("WrongConstant")
    public static void showToastCustom(Context context, String msg) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.mytoast, null);// 自定义toast
        TextView msg1 = (TextView) view.findViewById(R.id.msg);
        msg1.setText(msg);
        Toast toast = new Toast(context);

        // 可以控制toast显示的位置
        toast.setGravity(Gravity.CENTER, 0, 10);
        toast.setDuration(1000);
        toast.setView(view);
        toast.show();
    }

    /**
     * 打印测试
     */
    public static void showLogDebug(String msg) {

        Log.d("kenLog", msg);
    }

    public static void showLogError(String msg) {

        Log.e("kenLog", msg);
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int convertDpToPx(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private static final float GESTURE_THRESHOLD_DIP = 16.0f;

    public static float convertDpToPixel(float dp, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return GESTURE_THRESHOLD_DIP * scale + dp;
    }

    public static int dp2Px(int dp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return Math.round(dp * displayMetrics.density);
    }

    /**
     * px转dp
     *
     * @param
     * @return
     */
    public static int convertPxToDp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int px2Dp(int pixel) {
        DisplayMetrics displayMetrics = Resources.getSystem()
                .getDisplayMetrics();
        return (int) (pixel / displayMetrics.density);
    }

    /**
     * sp转px
     *
     * @param
     * @param
     * @return
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float px2sp(float pxVal) {
        return (pxVal / Resources.getSystem().getDisplayMetrics().scaledDensity);
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

    public static abstract class ABSHandler {

        public void todo() {
        }

        public void todo(String s) {
        }
        public void todo(View view ,ABSHandler absHandler) {
        }

    }

    /**
     * 检查更新
     */
    public static void checkVersion(Context context, checkjg checkjg) {
        CheckVersion.getInstance().check(context, checkjg);
    }

    /**
     * 取版本号
     */
    public static String getVersionName(Context context) {
        return CheckVersion.getVersionName(context);
    }

    /**
     * 获取当前程序的包名
     *
     * @param context
     * @return
     */
    public static String getAppPackageName(Context context) {
        String versionName = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo;

            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);

            versionName = packInfo.applicationInfo.packageName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * 把Stream转换成String
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 修改整个界面所有控件的字体
     *
     * @param root
     * @param path
     * @param act
     */
    public static void changeFonts(ViewGroup root, String path, Activity act) {
        // path是字体路径
        Typeface tf = Typeface.createFromAsset(act.getAssets(), path);
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(tf);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(tf);
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(tf);
            } else if (v instanceof ViewGroup) {
                changeFonts((ViewGroup) v, path, act);
            }
        }
    }

    /**
     * 修改整个界面所有控件的字体大小
     *
     * @param root
     * @param size
     * @param act
     */
    public static void changeTextSize(ViewGroup root, int size, Activity act) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View v = root.getChildAt(i);
            if (v instanceof TextView) {
                ((TextView) v).setTextSize(size);
            } else if (v instanceof Button) {
                ((Button) v).setTextSize(size);
            } else if (v instanceof EditText) {
                ((EditText) v).setTextSize(size);
            } else if (v instanceof ViewGroup) {
                changeTextSize((ViewGroup) v, size, act);
            }
        }
    }

    /**
     * 获取GUID
     */
    public static String getGUID() {
        // 创建 GUID 对象
        UUID uuid = UUID.randomUUID();
        // 得到对象产生的ID
        String a = uuid.toString();
        // 转换为大写
        a = a.toUpperCase();
        // 替换 -
        // a = a.replaceAll("-", "");
        System.out.println(a);
        return a;
    }

    /**
     * openORcloseKeybord
     *
     * @param mContext
     */
    public static void hideORopenKeybord(Context mContext) {

        InputMethodManager inputMethodManager = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭软键盘
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 改变图片亮度
     *
     * @param mBitmap
     * @return
     */
    public static Bitmap setImgLD(Bitmap mBitmap) {
        Bitmap bmp = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Config.ARGB_8888);
        int brightness = 10;
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        return bmp;
    }

    /**
     * 改变ImageView亮度
     *
     * @param imageView
     * @param brightness
     */
    public static void setImageViewLD(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }

    /**
     * 改变img对比度
     *
     * @param mBitmap
     * @return
     */
    public static Bitmap setImgDBD(Bitmap mBitmap) {
        Bitmap bmp = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Config.ARGB_8888);
        // int brightness = progress - 127;
        int progress = 10;
        float contrast = (float) ((progress + 64) / 128.0);
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{contrast, 0, 0, 0, 0, 0, contrast, 0, 0, 0,// 改变对比度
                0, 0, contrast, 0, 0, 0, 0, 0, 1, 0});

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        return bmp;
    }

    /**
     * 设置饱和度
     *
     * @param mBitmap
     * @return
     */
    public static Bitmap setImgBHD(Bitmap mBitmap) {
        Bitmap bmp = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Config.ARGB_8888);
        ColorMatrix cMatrix = new ColorMatrix();
        int progress = 200;
        // 设置饱和度
        cMatrix.setSaturation((float) (progress / 100.0));

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));

        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        return bmp;
    }

    /**
     * setImgColor
     *
     * @param mBitmap
     * @param mColor
     * @return
     */
    public static Bitmap setImgColor(Bitmap mBitmap, int mColor) {

        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(mColor);
        // 从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        // 在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);
        return mAlphaBitmap;
    }

    /**
     * InputStream转string
     *
     * @param is
     * @return
     */
    public static String inputStream2String(InputStream is) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            int i = -1;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }

        } catch (IOException e) {
            // TODO: handle exception
        } finally {

            try {
                baos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return baos.toString();
    }

    /**
     * string转InputStream
     *
     * @param str
     * @return
     */
    public static InputStream string2InputStream(String str) {

        InputStream in_withcode = null;
        try {
            in_withcode = new ByteArrayInputStream(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return in_withcode;
    }

    /***
     * 截取时间
     * @param s
     * @return
     */
    public static String cutTime(String s) {
        String time = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy/MM/dd HH:mm:ss");
            Date d = formatter.parse(s);

            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String date = format.format(d);
            time = date;
            System.out.println("date===" + date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return time;

    }

    /**
     * 获取随机数
     *
     * @param length
     * @return
     */
    public static String getRandom(int length) {
        String sjsString = "";
        java.util.Random r = new java.util.Random();
        for (int i = 0; i < length; i++) {
            sjsString += r.nextInt(9);
        }
        return sjsString;
    }

    /**
     * 安全场景的随机数
     * google Verifier 使用这个生成
     *
     * @param entropyBytes
     * @return
     */
    public static String generateRandomCodeVerifier(int entropyBytes) {

        byte[] randomBytes = new byte[entropyBytes];
        //安全随机数
        SecureRandom entropySource = new SecureRandom();
        entropySource.nextBytes(randomBytes);
        //base64
        return Base64.encodeToString(randomBytes, Base64.NO_WRAP | Base64.NO_PADDING | Base64.URL_SAFE);
    }

    /**
     * 检测某Activity是否在当前Task的栈顶 ,一般用在service
     * 要加权限
     * <uses-permission  android:name="android.permission.GET_TASKS" />
     */
    public static boolean isTopActivy(String className, Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        String classNameCurr = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return className.equals(classNameCurr);

    }

    /**
     * 释放bitmap资源
     *
     * @param bmp
     */
    public static void shifangBitmap(Bitmap bmp) {
        if (bmp != null && !bmp.isRecycled()) {
            bmp.recycle();
            bmp = null;
            System.gc();
        }

    }

    /**
     * 打开系统默认浏览器
     *
     * @param context
     */
    public static void openLiulanqi(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 带动画的Intent跳转
     *
     * @param activity
     * @param targetActivity
     * @param bundle
     */
    public static void intentWithAnim(FragmentActivity activity, Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(activity, targetActivity);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * TextView里各个位置加图片
     *
     * @param str
     * @param context
     * @return
     */
    public static SpannableStringBuilder TextViewAddToIMG(String str, int imgRId, Context context) {
        CharSequence text = str + "★";
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        String rexgString = "★";
        Pattern pattern = Pattern.compile(rexgString);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            builder.setSpan(
                    new ImageSpan(context, imgRId), matcher.start(), matcher
                            .end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    /**
     * 获取系统当前使用的语言,设置成简体中文zh  英语en
     *
     * @return
     */
    public static String getCurrentLauguage() {
        String mCurrentLanguage = Locale.getDefault().getLanguage();
        return mCurrentLanguage;
    }


    /**
     * 无title
     *
     * @param activity
     */
    public static void titleHide(AppCompatActivity activity) {

        //无title
        activity.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 从一个数组中删除和另一个数组中相同的项
     *
     * @param arrayList
     * @param arrayList1
     */
    public static void ListRemoveListAny(ArrayList<FileInfo> arrayList, ArrayList<FileInfo> arrayList1) {
        for (int i = 0; i < arrayList1.size(); i++) {

            for (int j = 0; j < arrayList.size(); j++) {
                if (arrayList.get(j).thumbPath != null && arrayList.get(j).thumbPath.equals(arrayList1.get(i).thumbPath)) {
                    arrayList.remove(j);
                } else if (arrayList.get(j).localImageUrl != null && arrayList.get(j).localImageUrl.equals(arrayList1.get(i).localImageUrl)) {
                    arrayList.remove(j);
                } else if (arrayList.get(j).VideoPath != null && arrayList.get(j).VideoPath.equals(arrayList1.get(i).VideoPath)) {
                    arrayList.remove(j);
                }
            }
        }
    }

    /**
     * 数据计算结果处理(四舍五入，保留一位小数)
     *
     * @param member      分子
     * @param denominator 分母
     * @return
     */
    public static Float dataHandler(long member, int denominator) {
        float num = (float) member / denominator;
        DecimalFormat df = new DecimalFormat("0.0");
        return Float.parseFloat(df.format(num));
    }

    /**
     * 手机震动
     * <uses-permission android:name="android.permission.VIBRATE"/>
     *
     * @param context
     * @return
     */
    public static Vibrator shake(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
//        vibrator.vibrate(1000);//震动一下
        long[] patter = {1000, 1000, 2000, 50};//震动时间数组，单位毫秒
        vibrator.vibrate(patter, 0);//循环震动
        return vibrator;
    }

    /**
     * 播放声音,时间很短反复播放，占用资源少
     * SoundPool
     *
     * @param context
     * @return
     */
    public static SoundPool playSound(Context context) {
        SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);

        soundPool.load(context, R.raw.beep, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                // 第一个参数为id，id即为放入到soundPool中的顺序，比如现在collide.wav是第一个，因此它的id就是1。
                // 第二个和第三个参数为左右声道的音量控制。第四个参数为优先级，由于只有这一个声音，因此优先级在这里并不重要。第五个参数为是否循环播放，
                // 0为不循环，-1为循环。最后一个参数为播放比率，从0.5到2，一般为1，表示正常播放。
                soundPool.play(1, 1, 1, 0, 0, 1);
            }
        });

        return soundPool;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * TBS 打开文件
     *
     * @param context
     */
    public static void tbsOpenFile(Context context, String fileurl) {
//        String fileurl=FileUtil.getRootFilePath()+"test.xlsx";
        QbSdk.getMiniQBVersion(context);
        QbSdk.openFileReader(context, fileurl, null, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Util.showLogDebug(s);
            }
        });
    }

    /**
     * 打电话
     *
     * @param context
     * @param tel
     */
    public static void callTel(Context context, String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        context.startActivity(intent);
    }

}
