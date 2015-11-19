package com.geek.geekmall.model;

import com.geek.geekmall.bean.Comment;

/**
 * Created by apple on 6/8/15.
 */
public class CommentPageData {
    private int status;
    private String errorMsg;
    private Page<Comment> data;

    public Page<Comment> getData() {
        return data;
    }

    public void setData(Page<Comment> data) {
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
