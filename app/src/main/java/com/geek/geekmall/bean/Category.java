package com.geek.geekmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索分类
 * Created by apple on 5/14/15.
 */
public class Category implements Serializable {
    private String imgUrl;
    private String displayName;
    private String id;
    private List<Category> children;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}
