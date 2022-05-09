package com.kenlib.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by ken on 2017/5/4
 * <p>
 * 关于manifest的操操作类
 * @see #channelValue(Context, String)
 */

public class ManifestUtil {

    public static String channel;
    /**
     * 获取Manifest的Application中meta_data的数据
     *
     */
    public static String channelValue(Context context, String key) {
        String value = "";
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            value = String.valueOf(appInfo.metaData.get(key));
            Log.d("CHANNEL", " channel : " + value);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (null == value) {
            value = "";
        }
        return value;
    }

    /**
     * 获取META-INFO下面的渠道
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        if (!TextUtils.isEmpty(channel)) {
            return channel;
        }
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        final String start_flag = "META-INF/channel_";
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<? extends ZipEntry> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains(start_flag)) {
                    channel = entryName.replaceAll(start_flag, "");
                    return channel;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
