package com.geek.geekmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 生成订单类
 * Created by apple on 7/5/15.
 */
public class SubmitOrderInfo implements Serializable {
    private int payType;
    private String addressId;
    private String userId;
    private int isPayBalance;
    private String orderIds;

    private List<OrderInfos> orderInfos;

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public int getIsPayBalance() {
        return isPayBalance;
    }

    public void setIsPayBalance(int isPayBalance) {
        this.isPayBalance = isPayBalance;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderInfos> getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(List<OrderInfos> orderInfos) {
        this.orderInfos = orderInfos;
    }
}
