package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 8/18/15.
 */
public class ReFund implements Serializable {
    private String message;
    private String id;
    private String refundStatus;
    /**
     * 退款状态：0.已取消；1.等待卖家待确认；2.卖家驳回  等待买家修改退款信息；3.卖家同意 等待买家发货；4.买家发货 等待卖家退款5.退款成功6.退款关闭，7退款失败；8：换货
     */
    private int status;
    private long date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
