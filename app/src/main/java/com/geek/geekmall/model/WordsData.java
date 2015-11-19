package com.geek.geekmall.model;

import com.geek.geekmall.bean.Keyword;

import java.util.List;

/**
 * Created by apple on 6/16/15.
 */
public class WordsData {
    private int status;
    private String errorMsg;
    private Page<Keyword> data;

    public Page<Keyword> getData() {
        return data;
    }

    public void setData(Page<Keyword> data) {
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
