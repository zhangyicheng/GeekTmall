package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 6/8/15.
 */
public class StoreTitle implements Serializable{
    private String id;
    private String displayName;

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
