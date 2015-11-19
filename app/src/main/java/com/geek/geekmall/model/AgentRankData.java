package com.geek.geekmall.model;

/**
 * Created by apple on 6/8/15.
 */
public class AgentRankData {
    private int status;
    private String errorMsg;
    private UserPageData data;

    public UserPageData getData() {
        return data;
    }

    public void setData(UserPageData data) {
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
