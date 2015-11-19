package com.geek.geekmall.model;

/**
 * Created by apple on 6/8/15.
 */
public class UserData {
    private int status;
    private String errorMsg;
    private UserModelData data;

    public UserModelData getData() {
        return data;
    }

    public void setData(UserModelData data) {
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
