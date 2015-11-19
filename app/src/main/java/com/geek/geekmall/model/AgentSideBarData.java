package com.geek.geekmall.model;

import com.geek.geekmall.bean.AgentSidebar;

/**
 * Created by apple on 6/3/15.
 */
public class AgentSideBarData {
    private int status;
    private String errorMsg;
    private AgentSidebar data;

    public AgentSidebar getData() {
        return data;
    }

    public void setData(AgentSidebar data) {
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
