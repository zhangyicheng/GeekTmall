package com.geek.geekmall.model;

import com.geek.geekmall.bean.Store;

import java.util.List;

/**
 * Created by apple on 6/19/15.
 */
public class CartNumData {
    private int countNum;
    private List<Store> validInfoList;
    private List<Store> invalidInfoList;

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    public List<Store> getValidInfoList() {
        return validInfoList;
    }

    public void setValidInfoList(List<Store> validInfoList) {
        this.validInfoList = validInfoList;
    }

    public List<Store> getInvalidInfoList() {
        return invalidInfoList;
    }

    public void setInvalidInfoList(List<Store> invalidInfoList) {
        this.invalidInfoList = invalidInfoList;
    }
}
