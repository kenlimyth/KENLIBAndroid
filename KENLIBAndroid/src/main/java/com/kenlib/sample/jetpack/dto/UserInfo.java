package com.kenlib.sample.jetpack.dto;

import java.util.ArrayList;

public class UserInfo {

    public String id;
    public String name;

    public ArrayList<UserInfo> getList() {
        ArrayList<UserInfo> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(this);
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

