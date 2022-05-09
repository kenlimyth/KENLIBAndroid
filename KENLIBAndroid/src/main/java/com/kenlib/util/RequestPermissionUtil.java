package com.kenlib.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * 权限设置
 */
public class RequestPermissionUtil {

    public interface IHandler {
        void isHavePermission();
    }

    /**
     * 动态获取SD卡写入权限
     */
    public static void getPermissionSD(Activity activity, IHandler iHandler) {
        getPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, iHandler);
    }

    public static void getPermissionCAMERA(Activity activity, IHandler iHandler) {
        getPermission(activity, Manifest.permission.CAMERA, iHandler);
    }

    /**
     * 浮动窗口权限
     *
     * @param activity
     * @param iHandler
     */
    public static void getPermissionCanDrawOverlays(Activity activity, IHandler iHandler) {

        //权限判断 安卓版本6.0以上，要动态加权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(activity)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                activity.startActivityForResult(intent, 1);
                goToAppSetting(activity);
            } else {
                iHandler.isHavePermission();
            }
        }
    }


    /**
     * 获取权限
     * 需要在activity重写 onRequestPermissionsResult，再次判断权限
     *
     * @param activity
     * @param permission
     * @param iHandler
     */
    private static void getPermission(Activity activity, String permission, IHandler iHandler) {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PERMISSION_GRANTED) {
                //有权限
                iHandler.isHavePermission();
            } else {
                // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                if (!activity.shouldShowRequestPermissionRationale(permission)) {
                    Util.showToast(activity, "用户选择了不再询问，请设置权限");
                    goToAppSetting(activity);
                }
                ActivityCompat.requestPermissions(activity, new String[]{permission},
                        123);
            }
        } else {
            iHandler.isHavePermission();
        }
    }

    /**
     * 跳转到当前应用的设置界面
     * 需要在activity重写 onActivityResult，再次判断权限
     */
    private static void goToAppSetting(Activity context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);

        context.startActivityForResult(intent, 123);
    }

}
