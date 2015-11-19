package com.geek.geekmall.model;

import com.geek.geekmall.bean.StoreTitle;
import com.geek.geekmall.bean.Theme;

import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class StoresData {
    private int status;
    private String errorMsg;
    private ThemeData data;

    public ThemeData getData() {
        return data;
    }

    public void setData(ThemeData data) {
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
