package com.kenlib.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import com.kenlib.sample.photo.dto.FileInfo;

/**
 * 文件工具类
 * Created by ken on 2018/5/15.
 */
public class FileUtil {

    private static final int FILE_BUFFER_SIZE = 51200;

    /**
     * uri 转 stringPath 路径
     *
     * @param uri
     * @param context
     * @return
     */
    public static String uriToStringPath(Uri uri, Context context) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 获得根路径（手机内存/SD卡），优先获取SD卡路径
     */
    public static String getRootFilePath() {
        if (SDCardUtil.isSdcardExist()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// /storage/emulated/0
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // /data/data/
        }
    }

    /**
     * 获取设置的文件夹|没有就创建，相对于手机根文件夹
     */
    public static String getRootFileDir(String dir) {

        String fileDir = FileUtil.getRootFilePath() + "/" + dir + "/";
        FileUtil.createDirectory(fileDir);
        return fileDir;

    }

    /**
     * 通过路径生成Base64文件
     *
     * @param path
     * @return
     */
    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {
            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    /**
     * 文件 转 base64
     *
     * @param filePath
     * @return
     */
    public static String encodeBase64File(String filePath) {
        File file = new File(filePath);
        FileInputStream fis;
        byte[] buffer = null;
        try {
            fis = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            fis.read(buffer);
            fis.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        if (buffer == null || buffer.length == 0) {
            return "";
        }
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * 获得时间格式命名的图片名，
     */
    public static String getFileName() {
        Time time = new Time();
        time.setToNow();
        String filename = Integer.toString(time.year)
                + Integer.toString(time.month + 1)
                + Integer.toString(time.monthDay) + System.currentTimeMillis()
                + ".jpg";

        return filename;
    }

    /**
     * getFileName
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        File file = new File(filePath);
        if (file == null || !file.exists())
            return null;
        return file.getName();
    }

    /**
     * 获取文件byte
     *
     * @param filePath
     * @return
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * InputStream 转 byte
     *
     * @param inputStream
     * @return
     */
    public static byte[] getBytes(InputStream inputStream) {
        byte[] buffer = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = inputStream.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            inputStream.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean fileIsExist(String filePath) {
        if (filePath == null || filePath.length() < 1) {
            return false;
        }

        File f = new File(filePath);
        if (!f.exists()) {
            return false;
        }
        return true;
    }

    public static InputStream readFile(String filePath) {
        if (null == filePath) {
            return null;
        }

        InputStream is = null;

        try {
            if (fileIsExist(filePath)) {
                File f = new File(filePath);
                is = new FileInputStream(f);
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
        return is;
    }

    /**
     * 创建文件目录
     *
     * @param filePath
     * @return
     */
    public static boolean createDirectory(String filePath) {
        if (null == filePath) {
            return false;
        }

        File file = new File(filePath);

        if (file.exists()) {
            return true;
        }

        return file.mkdirs();

    }

    /**
     * 删除文件, 或目录下所有文件，递归
     *
     * @param filePathOrDir
     * @return
     */
    public static boolean delFileAndDir(String filePathOrDir) {
        if (null == filePathOrDir) {
            return false;
        }

        File file = new File(filePathOrDir);

        if (file == null || !file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] list = file.listFiles();

            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    delFileAndDir(list[i].getAbsolutePath());
                } else {
                    list[i].delete();
                }
            }
        }
        file.delete();
        return true;
    }

    /**
     * 获取目录下所有文件
     *
     * @param dirPath
     * @param fileList 返回的list
     */
    public static void getFileListFromDir(String dirPath, ArrayList<FileInfo> fileList) {
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            File[] list = dir.listFiles();

            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    getFileListFromDir(list[i].getAbsolutePath(), fileList);
                } else {
                    FileInfo fileInfo = new FileInfo();
                    String hz = getFileSuffix(list[i].getAbsolutePath());
                    fileInfo.size = Util.dataHandler(list[i].length() / 1024, 1024);
                    if (".mp4".equals(hz)) {
                        fileInfo.type = FileInfo.Type.mp4;
                        fileInfo.VideoPath = list[i].getAbsolutePath();
                        fileInfo.thumbPath = list[i].getAbsolutePath();
                    } else {
                        fileInfo.type = FileInfo.Type.img;
                        fileInfo.localImageUrl = list[i].getAbsolutePath();

                    }
                    fileList.add(fileInfo);
                }
            }
        }
    }

    /**
     * 获取文件后缀名
     */
    public static String getFileSuffix(String filePath) {
        if (filePath == null)
            return null;

        File file = new File(filePath);
        if (file.exists()) {
            String fileName = file.getName();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            return fileType;
        }
        return null;
    }

    public static long getFileSize(String filePath) {
        if (null == filePath) {
            return 0;
        }

        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return 0;
        }

        return file.length();
    }

    public static long getFileModifyTime(String filePath) {
        if (null == filePath) {
            return 0;
        }

        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return 0;
        }

        return file.lastModified();
    }


    /**
     * 复制文件
     *
     * @param source
     * @param dest
     */
    public static void copyFileUsingFileChannels(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (IOException ex) {


        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static byte[] readAll(InputStream is) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        byte[] buf = new byte[1024];
        int c = is.read(buf);
        while (-1 != c) {
            baos.write(buf, 0, c);
            c = is.read(buf);
        }
        baos.flush();
        baos.close();
        return baos.toByteArray();
    }

    /**
     * 根据uri 读取文件
     *
     * @param ctx
     * @param uri
     * @return
     */
    public static byte[] readFile(Context ctx, Uri uri) {
        if (null == ctx || null == uri) {
            return null;
        }

        InputStream is = null;
        String scheme = uri.getScheme().toLowerCase();
        if (scheme.equals("file")) {
            is = readFile(uri.getPath());
        }

        try {
            is = ctx.getContentResolver().openInputStream(uri);
            if (null == is) {
                return null;
            }

            byte[] bret = readAll(is);
            is.close();
            is = null;

            return bret;
        } catch (FileNotFoundException fne) {
        } catch (Exception ex) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (Exception ex) {
                }
                ;
            }
        }
        return null;
    }

    /**
     * 根据byte[] 保存文件
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean writeFile(String filePath, byte[] content) {
        if (null == filePath || null == content) {
            return false;
        }

        FileOutputStream fos = null;
        try {
            String pth = filePath.substring(0, filePath.lastIndexOf("/"));
            File pf = null;
            pf = new File(pth);
            if (pf.exists() && !pf.isDirectory()) {
                pf.delete();
            }
            pf = new File(filePath);
            if (pf.exists()) {
                if (pf.isDirectory()) FileUtil.delFileAndDir(filePath);
                else pf.delete();
            }

            pf = new File(pth + File.separator);
            if (!pf.exists()) {
                if (!pf.mkdirs()) {
                }
            }

            fos = new FileOutputStream(filePath);
            fos.write(content);
            fos.flush();
            fos.close();
            fos = null;
            pf.setLastModified(System.currentTimeMillis());

            return true;

        } catch (Exception ex) {
            Util.showLogDebug(ex.getMessage());
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (Exception ex) {
                }
                ;
            }
        }
        return false;
    }


    /**
     * 读取文件 返回byte
     *
     * @param fileName
     * @return
     */
    public static byte[] readFileByte(String fileName) {
        if (fileIsExist(fileName)) {
            try {
                FileInputStream fin = new FileInputStream(fileName);
                int size;
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((size = fin.read(buffer, 0, buffer.length)) != -1) {
                    baos.write(buffer, 0, size);
                }
                return baos.toByteArray();
            } catch (Exception ex) {
            }
        }
        return null;
    }


    //压缩文件-------------------------------------------------------------------------------------
    public static boolean zipFile(String baseDirName, String fileName, String targerFileName) throws IOException {
        if (baseDirName == null || "".equals(baseDirName)) {
            return false;
        }
        File baseDir = new File(baseDirName);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            return false;
        }

        String baseDirPath = baseDir.getAbsolutePath();
        File targerFile = new File(targerFileName);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targerFile));
        File file = new File(baseDir, fileName);

        boolean zipResult = false;
        if (file.isFile()) {
            zipResult = fileToZip(baseDirPath, file, out);
        } else {
            zipResult = dirToZip(baseDirPath, file, out);
        }
        out.close();
        return zipResult;
    }

    public static boolean unZipFile(String fileName, String unZipDir) throws Exception {
        File f = new File(unZipDir);

        if (!f.exists()) {
            f.mkdirs();
        }

        BufferedInputStream is = null;
        ZipEntry entry;
        ZipFile zipfile = new ZipFile(fileName);
        Enumeration<?> enumeration = zipfile.entries();
        byte data[] = new byte[FILE_BUFFER_SIZE];

        while (enumeration.hasMoreElements()) {
            entry = (ZipEntry) enumeration.nextElement();

            if (entry.isDirectory()) {
                File f1 = new File(unZipDir + "/" + entry.getName());
                if (!f1.exists()) {
                    f1.mkdirs();
                }
            } else {
                is = new BufferedInputStream(zipfile.getInputStream(entry));
                int count;
                String name = unZipDir + "/" + entry.getName();
                RandomAccessFile m_randFile = null;
                File file = new File(name);
                if (file.exists()) {
                    file.delete();
                }

                file.createNewFile();
                m_randFile = new RandomAccessFile(file, "rw");
                int begin = 0;

                while ((count = is.read(data, 0, FILE_BUFFER_SIZE)) != -1) {
                    try {
                        m_randFile.seek(begin);
                    } catch (Exception ex) {
                    }

                    m_randFile.write(data, 0, count);
                    begin = begin + count;
                }

                file.delete();
                m_randFile.close();
                is.close();
            }
        }

        return true;
    }

    private static boolean fileToZip(String baseDirPath, File file, ZipOutputStream out) throws IOException {
        FileInputStream in = null;
        ZipEntry entry = null;

        byte[] buffer = new byte[FILE_BUFFER_SIZE];
        int bytes_read;
        try {
            in = new FileInputStream(file);
            entry = new ZipEntry(getEntryName(baseDirPath, file));
            out.putNextEntry(entry);

            while ((bytes_read = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes_read);
            }
            out.closeEntry();
            in.close();
        } catch (IOException e) {
            return false;
        } finally {
            if (out != null) {
                out.closeEntry();
            }

            if (in != null) {
                in.close();
            }
        }
        return true;
    }

    private static boolean dirToZip(String baseDirPath, File dir, ZipOutputStream out) throws IOException {
        if (!dir.isDirectory()) {
            return false;
        }

        File[] files = dir.listFiles();
        if (files.length == 0) {
            ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));

            try {
                out.putNextEntry(entry);
                out.closeEntry();
            } catch (IOException e) {
            }
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                fileToZip(baseDirPath, files[i], out);
            } else {
                dirToZip(baseDirPath, files[i], out);
            }
        }
        return true;
    }

    private static String getEntryName(String baseDirPath, File file) {
        if (!baseDirPath.endsWith(File.separator)) {
            baseDirPath = baseDirPath + File.separator;
        }

        String filePath = file.getAbsolutePath();
        if (file.isDirectory()) {
            filePath = filePath + "/";
        }

        int index = filePath.indexOf(baseDirPath);
        return filePath.substring(index + baseDirPath.length());
    }
}
