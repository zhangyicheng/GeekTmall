package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 5/20/15.
 */
public class Image implements Serializable {

    private String url;
    private String bigUrl;

    public Image() {
    }

    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBigUrl() {
        return bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }
}
