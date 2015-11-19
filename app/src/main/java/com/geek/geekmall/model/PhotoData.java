package com.geek.geekmall.model;

import com.geek.geekmall.bean.Area;
import com.geek.geekmall.bean.DescriptionImage;

import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class PhotoData {
    private int status;
    private String errorMsg;
    private List<DescriptionImage> data;

    public List<DescriptionImage> getData() {
        return data;
    }

    public void setData(List<DescriptionImage> data) {
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
