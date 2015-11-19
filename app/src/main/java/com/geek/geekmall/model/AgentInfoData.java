package com.geek.geekmall.model;

import com.geek.geekmall.bean.Address;
import com.geek.geekmall.bean.AgentInfo;

/**
 * Created by apple on 6/8/15.
 */
public class AgentInfoData {
    private int status;
    private String errorMsg;
    private AgentInfo data;

    public AgentInfo getData() {
        return data;
    }

    public void setData(AgentInfo data) {
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
