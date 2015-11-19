package com.geek.geekmall.model;

/**
 * Created by apple on 6/3/15.
 */
public class BuyData {
    private int status;
    private String errorMsg;
    private BuyInfo data;

    public BuyInfo getData() {
        return data;
    }

    public void setData(BuyInfo data) {
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
