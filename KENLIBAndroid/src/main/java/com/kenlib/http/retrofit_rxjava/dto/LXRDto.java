package com.kenlib.http.retrofit_rxjava.dto;

import java.util.List;

public class LXRDto {

    public String sex;
    public String name;
    public String code;
    public String phone;
    public String type;
    public String userid;
    public List<LXRDto> children;

    public int layerLevel;
    public boolean isShow = true;


    public int getNumberOfChildren() {
        int size = children.size();
        if (size == 0) {
            return 0;
        }
        int sum = size;
        for (LXRDto item : children) {
            sum += item.getNumberOfChildren();
        }
        return sum;
    }

    public LXRDto getTreeItem(int position) {
        if (position == 0) {
            return this;
        }
        if (position < 0) {
            return null;
        }
        for (LXRDto item : children) {
            position--;
            if (position == 0) {
                return item;
            }
            int num = item.getNumberOfChildren();
            if (position <= num) {
                return item.getTreeItem(position);
            }
            position -= num;
        }
        return null;
    }

    public void setShowAll(boolean b) {
//        isShow = b;
        for (LXRDto skillTree : children) {
            skillTree.isShow = b;
            skillTree.setShowAll(b);
        }
    }

    public boolean childrenIsHaveHide() {
        for (LXRDto skillTree : children) {
            if (!skillTree.isShow) {
                return true;
            }

        }
        return false;
    }
}


