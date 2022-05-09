package com.kenlib.sample.recyclerview.recyclerviewgroup;

/**
 * dto
 */
public class BookTree {
    public int pid;
    public boolean isChecked;
    public boolean isEnable = true;
    public Object object;


    public static class BInfo {
        public BInfo() {
        }

        public int pid;
        public String bTitle;
        public String desc;
    }
}
