package com.geek.geekmall.model;

import com.geek.geekmall.bean.ReFund;

/**
 * Created by apple on 6/3/15.
 */
public class ReFundData {
    private int status;
    private String errorMsg;
    private ReFund data;

    public ReFund getData() {
        return data;
    }

    public void setData(ReFund data) {
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
