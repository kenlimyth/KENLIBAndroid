package com.kenlib.sample.photo.dto;

import java.io.Serializable;

/**
 * FileInfo
 */
public class FileInfo implements Serializable {

    public enum Type {
        mp4, img
    }

    public FileInfo() {
    }

    public FileInfo(Type type, String thumbPath, String videoPath, float size,long modifyTime) {
        this.type = type;
        this.thumbPath = thumbPath;
        this.VideoPath = videoPath;
        this.size = size;
        this.modifyTime=modifyTime;
    }

    public Type type;
    public String localImageUrl;
    public boolean isCheck;

    public String thumbPath;
    public String VideoPath;
    public float size;
    public Long modifyTime;
}
