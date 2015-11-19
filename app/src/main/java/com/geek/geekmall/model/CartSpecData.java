package com.geek.geekmall.model;

import com.geek.geekmall.bean.CartSpec;

/**
 * Created by apple on 6/8/15.
 */
public class CartSpecData {
    private int status;
    private String errorMsg;
    private CartSpec data;

    public CartSpec getData() {
        return data;
    }

    public void setData(CartSpec data) {
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
