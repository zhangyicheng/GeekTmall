package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 7/9/15.
 */
public class Update implements Serializable{
    /**
     * 是否强制更新（0：非强制更新；1强制更新）
     */
    private int forceType;
    private String downUrl;
    private String version;

    public int getForceType() {
        return forceType;
    }

    public void setForceType(int forceType) {
        this.forceType = forceType;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
