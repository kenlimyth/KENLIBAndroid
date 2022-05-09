package com.kenlib.log;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志文件
 * @author Administrator
 */
public class KENLog {

    private static String rootdir = "";
    private static String tag = "KENLog";
    private static String logname = "log.txt";
    private static String errorname = "error.txt";


    public static void init(String dir1) {

        String filedir = dir1 + "/" + tag + "/";
        File dir = new File(filedir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        rootdir = filedir;
    }

    private static void init1() {

        String filedir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + tag + "/";
        File dir = new File(filedir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        rootdir = filedir;
    }


    private static void writeLine(String filename, String string) {
        try {

            if (rootdir == "") {
                init1();
            }

            String br = "\r\n";
            SimpleDateFormat dFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String msg = dFormat.format(new Date()) + " " + string;
            String fileurl = rootdir + filename;
            File file = new File(fileurl);
            if (file.exists()) {
                msg = br + msg;
            }
            FileOutputStream fos = new FileOutputStream(fileurl, true);
            fos.write(msg.toString().getBytes());
            fos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void logTest(String string) {
        writeLine(logname, string);
    }

    public static void logError(String string) {
        writeLine(errorname, string);
    }

    public static void log(String filename, String string) {
        writeLine(filename, string);
    }
}
