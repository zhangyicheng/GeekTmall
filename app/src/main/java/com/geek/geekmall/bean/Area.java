package com.geek.geekmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 6/10/15.
 */
public class Area implements Serializable {
    private String id;
    private String displayName;
    private List<Area> childrenList;

    public List<Area> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Area> childernList) {
        this.childrenList = childernList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
