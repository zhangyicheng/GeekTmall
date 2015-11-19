package com.geek.geekmall.model;

import com.geek.geekmall.bean.Area;
import com.geek.geekmall.bean.BindStatus;

import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class BindStatusData {
    private int status;
    private String errorMsg;
    private BindStatus data;

    public BindStatus getData() {
        return data;
    }

    public void setData(BindStatus data) {
        this.data = data;
    }

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
}
