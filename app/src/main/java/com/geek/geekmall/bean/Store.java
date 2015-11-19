package com.geek.geekmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 6/19/15.
 */
public class Store implements Serializable {
    private String storeId;
    private String storeName;
    private int totalNum;
    private float totalPrice;
    private List<Cart> goodsInfoMap;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Cart> getGoodsInfoMap() {
        return goodsInfoMap;
    }

    public void setGoodsInfoMap(List<Cart> goodsInfoMap) {
        this.goodsInfoMap = goodsInfoMap;
    }
}
