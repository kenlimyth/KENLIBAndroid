package com.kenlib.sample.photo.util;

import com.kenlib.util.FileUtil;

public class Util {

    public static String getSavePath(String dir, String tag, String path) {
        if (path == null) {
            return null;
        }
        String fileName = FileUtil.getFileName(path);
        if (fileName == null) {
            return null;
        }
        fileName = fileName.replace(Config.SeparateZH, Config.Separate);
        if (fileName.indexOf(Config.Separate) != -1) {
            fileName = fileName.substring(fileName.lastIndexOf(Config.Separate)+1);
        }
        String savePath = dir + tag + Config.Separate + fileName;
        return savePath;

    }

}
