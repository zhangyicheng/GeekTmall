package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * 搜索 具体分类
 * Created by apple on 5/14/15.
 */
public class SecCategory implements Serializable {
    private int id;
    private String name;
    private String picUrl;

    public SecCategory() {
    }

    public SecCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
