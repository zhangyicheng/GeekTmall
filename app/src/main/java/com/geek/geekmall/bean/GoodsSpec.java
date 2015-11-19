package com.geek.geekmall.bean;

import java.util.List;

/**
 * Created by apple on 6/18/15.
 */
public class GoodsSpec {
    private String id;
    private String parentId;
    private String displayName;
    private String parentName;
    private String specName;
    private List<GoodsSpec> specList;

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public List<GoodsSpec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<GoodsSpec> specList) {
        this.specList = specList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
