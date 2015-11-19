package com.geek.geekmall.model;

import com.geek.geekmall.bean.Address;

import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class AddressListData {
    private int status;
    private String errorMsg;
    private Page<Address> data;

    public Page<Address> getData() {
        return data;
    }

    public void setData(Page<Address> data) {
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
