package com.geek.geekmall.model;

import com.geek.geekmall.bean.Address;
import com.geek.geekmall.bean.AlipayResponse;

/**
 * Created by apple on 6/8/15.
 */
public class AlipayResponseData {
    private int status;
    private String errorMsg;
    private AlipayResponse data;

    public AlipayResponse getData() {
        return data;
    }

    public void setData(AlipayResponse data) {
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
