package com.geek.geekmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 6/19/15.
 */
public class Order implements Serializable {
    private String id;
    private String storeName;
    private String orderNo;
    /**
     * 订单状态(1. 待支付 2待发货 3待收货 4已完成 5已关闭 6已取消 7待评价)
     */
    private int payStatus;
    private String paySuccTime;
    private String payType;
    private int goodsNum;
    private String payOrderNo;
    private float postage;
    private float totalPrice;
    private long createTime;
    private String addressId;
private int refundStatus;

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    private List<OrderProduct> goodsList;



    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public float getPostage() {
        return postage;
    }

    public void setPostage(float postage) {
        this.postage = postage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getPaySuccTime() {
        return paySuccTime;
    }

    public void setPaySuccTime(String paySuccTime) {
        this.paySuccTime = paySuccTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public List<OrderProduct> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<OrderProduct> goodsList) {
        this.goodsList = goodsList;
    }
}
