package com.kenlib.util;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

/**
 * 图像缓存
 */
public class LruCacheUtil {

    //缓存实例
    private static LruCache<String, Bitmap> mLocalPhotoImages;

    /**
     * 图像缓存
     *
     * @return 缓存实例
     */
    private static synchronized LruCache<String, Bitmap> getImagesCache() {
        if (mLocalPhotoImages == null) {
            int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            int cacheSize = maxMemory / 8;
            mLocalPhotoImages = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(@NonNull String key, @NonNull Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }
            };
        }
        return mLocalPhotoImages;
    }

    /**
     * 从缓存获取图像。
     *
     * @param key 画像url
     * @return 画像
     */
    public static Bitmap getBitmapFromCache(String key) {
        if (key == null) {
            return null;
        }
        return getImagesCache().get(key);
    }

    /**
     * 将图像保存到缓存
     *
     * @param bitmap 画像
     * @param key    画像url
     */
    public static void addBitmapToCache(Bitmap bitmap, String key) {
        if (key != null && bitmap != null) {
            getImagesCache().put(key, bitmap);
        }
    }
}