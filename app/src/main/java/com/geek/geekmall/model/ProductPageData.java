package com.geek.geekmall.model;

import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.Theme;

/**
 * Created by apple on 6/8/15.
 */
public class ProductPageData {
    private int status;
    private String errorMsg;
    private Page<Product> data;

    public Page<Product> getData() {
        return data;
    }

    public void setData(Page<Product> data) {
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
