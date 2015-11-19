package com.geek.geekmall.model;

import com.geek.geekmall.bean.Theme;

import java.util.List;

/**
 * Created by apple on 9/15/15.
 */
public class BannerTheme  {
    private int status;
    private String errorMsg;
    private List<Theme> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<Theme> getData() {
        return data;
    }

    public void setData(List<Theme> data) {
        this.data = data;
    }
}
