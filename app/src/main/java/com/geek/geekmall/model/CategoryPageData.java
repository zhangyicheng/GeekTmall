package com.geek.geekmall.model;

import com.geek.geekmall.bean.Category;
import com.geek.geekmall.bean.Product;

/**
 * Created by apple on 6/8/15.
 */
public class CategoryPageData {
    private int status;
    private String errorMsg;
    private Page<Category> data;

    public Page<Category> getData() {
        return data;
    }

    public void setData(Page<Category> data) {
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
