package com.geek.geekmall.model;

import com.geek.geekmall.bean.Order;

/**
 * Created by apple on 6/19/15.
 */
public class OrderData {
    private int status;
    private String errorMsg;
    private Page<Order> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Page<Order> getData() {
        return data;
    }

    public void setData(Page<Order> data) {
        this.data = data;
    }

    public void setErrorMsg(String errorMsg) {

        this.errorMsg = errorMsg;
    }
}
