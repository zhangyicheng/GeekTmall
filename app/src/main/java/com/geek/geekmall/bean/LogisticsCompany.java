package com.geek.geekmall.bean;

/**
 * Created by apple on 8/19/15.
 */
public class LogisticsCompany {
    private String name;
    private String code;

    public LogisticsCompany(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
