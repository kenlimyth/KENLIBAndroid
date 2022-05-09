package com.kenlib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片工具类
 * Created by ken on 2018/5/16.
 */
public class ImgUtil {

    /**
     * 压缩图像
     *
     * @param path 图像路径
     * @return 压缩图像
     */
    public static Bitmap compressBitmap(String path, boolean compressFlag, int width, int height) {
        if (!compressFlag) return ImgUtil.rotateBitmap(BitmapFactory.decodeFile(path), path);
        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
        bitmapFactoryOptions.inSampleSize = calculateInSampleSize(path, bitmapFactoryOptions, width, height);
//        bitmapFactoryOptions.inSampleSize = getInSampleSize(new File(path));
        bitmapFactoryOptions.inJustDecodeBounds = false;
        bitmapFactoryOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, bitmapFactoryOptions);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        if (bitmap == null) {
            return null;
        }
        bitmap = ImgUtil.rotateBitmap(bitmap, path);
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            baos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 计算压缩率
     *
     * @param path 图像路径
     * @return 压缩率
     */
    public static int calculateInSampleSize(String path, BitmapFactory.Options bitmapFactoryOptions, int CoverImageWidth, int CoverImageHeight) {
        bitmapFactoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bitmapFactoryOptions);
        final int height = bitmapFactoryOptions.outHeight;
        final int width = bitmapFactoryOptions.outWidth;
        int inSampleSize = 1;
        if (height > CoverImageHeight || width > CoverImageWidth) {
            //使用所需纵横的最大值计算比例
            final int suitedValue = Math.max(CoverImageHeight, CoverImageWidth);
            final int heightRatio = Math.round((float) height / (float) suitedValue);
            final int widthRatio = Math.round((float) width / (float) suitedValue);
            inSampleSize = Math.max(heightRatio, widthRatio);
        }
        return inSampleSize;
    }

    /**
     * 取图片压缩比例
     *
     * @param f
     * @return
     */
    public static int getInSampleSize(File f) {
        if (f == null) {
            return 1;
        }
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final int REQUIRED_SIZE = 500;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        return scale;
    }

    /**
     * 图片质量压缩，根据指定大小压缩图片,不大于指定大小
     *
     * @param image
     * @param fileOutPath
     * @param maxSize
     */
    public static void compressImage(Bitmap image, String fileOutPath, int maxSize) {
        try {

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int options = 100;//100表示不压缩图片，这里是想将图片读到ByteArrayOutputStream流中
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
            //maxSize是用户希望将图片压缩成 多少kb，因为options的范围在0到100所以不能这里需要
            // 对options的值做一个判断
            //循环对图片进行压缩
            while (options > 10 && os.toByteArray().length / 1024 > maxSize) {
                os.reset();
                options -= 5;
                image.compress(Bitmap.CompressFormat.JPEG, options, os);
            }
            FileOutputStream fos = new FileOutputStream(fileOutPath);
            fos.write(os.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException ex) {
            Util.showLogDebug(ex.toString());
        }
    }

    /**
     * 保存指定宽高的图片
     *
     * @param srcBmp
     * @param saveFilePath
     * @param width
     * @param height
     * @return
     */
    public static boolean saveBitmap2file(Bitmap srcBmp, String saveFilePath, int width, int height) {

        Bitmap newBmp = Bitmap.createScaledBitmap(srcBmp, width, height, true);
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(saveFilePath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newBmp.compress(Bitmap.CompressFormat.JPEG, quality, stream);
    }

    /**
     * 保存图片
     * @param bitmap
     * @param saveFilePath
     * @return
     */
    public static boolean saveBitmap(Bitmap bitmap, String saveFilePath) {

        try {
            File filePic = new File(saveFilePath);
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * 图片 转 base64编码
     */
    public static String getBase64FromBitmap(Bitmap bitmap, int bitmapQuality) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * base64转换成bitmap
     */
    public static Bitmap getBitmapFromBase64(String string) {
        byte[] bitmapArray = null;
        try {
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory
                .decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }


    /**
     * 压缩图片，防止内存溢出，指定inSampleSize
     */
    public static Bitmap getCompressBitmap(String filePath, int inSampleSize) {
        Bitmap bitmap = null;
        if (!"".equals(filePath)) {

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = inSampleSize;// 防止内存溢出的关键，现在是一般质量
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        }
        return bitmap;
    }

    /**
     * 按比例缩放以减少内存消耗
     *
     * @param f
     * @return
     */
    public static Bitmap getCompressBitmap(File f) {
        try {
            if (f == null) {
                return null;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = getInSampleSize(f);
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    /**
     * 按比例缩放以减少内存消耗
     *
     * @param fileurl
     * @return
     */
    public static Bitmap getCompressBitmap(String fileurl) {
        try {
            if ("".equals(fileurl)) {
                return null;
            }
            File f = new File(fileurl);
            if (f == null || !f.exists()) {
                return null;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = getInSampleSize(f);
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    /**
     * getBitmap
     * @param fileurl
     * @return
     */
    public static Bitmap getBitmap(String fileurl) {
        if ("".equals(fileurl)) {
            return null;
        }
        File f = new File(fileurl);
        if (f == null || !f.exists()) {
            return null;
        }
        return BitmapFactory.decodeFile(fileurl);
    }

    /**
     * Bitmap 转成 ByteArray
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp,
                                        final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static final Canvas sCanvas = new Canvas();

    public static Bitmap createBitmapFromView(View view) {
        view.clearFocus();
        Bitmap bitmap = createBitmapSafely(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized (sCanvas) {
                Canvas canvas = sCanvas;
                canvas.setBitmap(bitmap);
                view.draw(canvas);
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }

    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }

    /**
     * 是否是图片
     *
     * @param filePath
     * @return
     */
    public static boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1 || options.outWidth == 0) {
            return false;
        }
        return true;
    }

    /**
     * 获得图像旋转角度
     *
     * @param path 图像路径
     * @return 图像旋转角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
        }
        return degree;
    }

    /**
     * 旋转图像
     *
     * @param bitmap 图像
     * @param path   图像路径
     * @return 旋转后的图像
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, String path) {
        int rotate = ImgUtil.readPictureDegree(path);
        if (rotate != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

}
