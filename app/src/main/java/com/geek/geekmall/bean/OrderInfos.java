package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 7/5/15.
 */
public class OrderInfos implements Serializable {
    private String storeId;
    private String postageType;
    private String shopCartIds;
private String useCoinShopCartIds;
    private String orderId;
    private float postage;

    public String getUseCoinShopCartIds() {
        return useCoinShopCartIds;
    }

    public void setUseCoinShopCartIds(String useCoinShopCartIds) {
        this.useCoinShopCartIds = useCoinShopCartIds;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getPostage() {
        return postage;
    }

    public void setPostage(float postage) {
        this.postage = postage;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPostageType() {
        return postageType;
    }

    public void setPostageType(String postageType) {
        this.postageType = postageType;
    }

    public String getShopCartIds() {
        return shopCartIds;
    }

    public void setShopCartIds(String shopCartIds) {
        this.shopCartIds = shopCartIds;
    }
}
