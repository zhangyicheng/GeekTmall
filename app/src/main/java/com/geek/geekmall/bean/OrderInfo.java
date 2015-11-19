package com.geek.geekmall.bean;

/**
 * Created by apple on 6/30/15.
 */
public class OrderInfo {
    /**
     * 未收货
     */
    private String falseGoodsReceipt;
    /**
     * 未付款
     */
    private String falsePay;
    /**
     * 收藏数量
     */
    private String favourNumber;
    /**
     * 订单数量
     */
    private String orderNumber;
    /**
     * 退货
     */
    private String refundNumber;
    /**
     * 未评论
     */
    private String falseComment;
    /**
     * 历史数据
     */
    private String historyNumber;
    private String falseSend;

    public String getFalseGoodsReceipt() {
        return falseGoodsReceipt;
    }

    public void setFalseGoodsReceipt(String falseGoodsReceipt) {
        this.falseGoodsReceipt = falseGoodsReceipt;
    }

    public String getFalsePay() {
        return falsePay;
    }

    public void setFalsePay(String falsePay) {
        this.falsePay = falsePay;
    }

    public String getFavourNumber() {
        return favourNumber;
    }

    public void setFavourNumber(String favourNumber) {
        this.favourNumber = favourNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRefundNumber() {
        return refundNumber;
    }

    public void setRefundNumber(String refundNumber) {
        this.refundNumber = refundNumber;
    }

    public String getFalseComment() {
        return falseComment;
    }

    public void setFalseComment(String falseComment) {
        this.falseComment = falseComment;
    }

    public String getHistoryNumber() {
        return historyNumber;
    }

    public void setHistoryNumber(String historyNumber) {

        this.historyNumber = historyNumber;
    }

    public String getFalseSend() {
        return falseSend;
    }

    public void setFalseSend(String falseSend) {
        this.falseSend = falseSend;
    }
}
