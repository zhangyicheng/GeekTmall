package com.geek.geekmall.model;

import com.geek.geekmall.bean.Area;

import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class AreaData {
    private int status;
    private String errorMsg;
    private List<Area> data;

    public List<Area> getData() {
        return data;
    }

    public void setData(List<Area> data) {
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
