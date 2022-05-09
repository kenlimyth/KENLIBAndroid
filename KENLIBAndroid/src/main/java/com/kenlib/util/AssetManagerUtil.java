package com.kenlib.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * AssetManager操作
 */
public class AssetManagerUtil {

    public static byte[] getFileByteFromAsset(Context context) {
        byte[] fileByte = new byte[0];
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("test.txt");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[4096];
            int len = 0;
            while ((len = inputStream.read(bytes)) > 0) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
            fileByte = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            Log.e("ken", "AssetManager=" + e.getMessage());
        }

        return fileByte;
    }

}
