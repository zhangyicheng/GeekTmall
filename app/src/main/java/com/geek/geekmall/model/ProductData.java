package com.geek.geekmall.model;

import com.geek.geekmall.bean.Product;

import java.util.List;

/**
 * Created by apple on 6/15/15.
 */
public class ProductData {
    private int status;
    private String errorMsg;
    private Product data;

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
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
