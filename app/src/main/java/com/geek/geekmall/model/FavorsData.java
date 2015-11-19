package com.geek.geekmall.model;

import com.geek.geekmall.bean.FavorProduct;
import com.geek.geekmall.bean.Product;

import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class FavorsData {
    private int status;
    private String errorMsg;
    private Page<FavorProduct> data;

    public Page<FavorProduct> getData() {
        return data;
    }

    public void setData(Page<FavorProduct> data) {
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
