package com.geek.geekmall.model;

import com.geek.geekmall.bean.FootPrintProduct;
import com.geek.geekmall.bean.Product;

/**
 * Created by apple on 6/8/15.
 */
public class FootPrintPageData {
    private int status;
    private String errorMsg;
    private Page<FootPrintProduct> data;

    public Page<FootPrintProduct> getData() {
        return data;
    }

    public void setData(Page<FootPrintProduct> data) {
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
