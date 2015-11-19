package com.geek.geekmall.model;

/**
 * Created by apple on 6/3/15.
 */
public class CartData {
    private int status;
    private String errorMsg;
private CartNumData data;

    public CartNumData getData() {
        return data;
    }

    public void setData(CartNumData data) {
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
