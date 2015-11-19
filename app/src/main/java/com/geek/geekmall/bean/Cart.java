package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 4/28/15.
 */
public class Cart implements Serializable {
    private String storeId;
    private String storeName;
    private int totalNum;
    private String shopCartId;
    private String goodsId;
    private float marketPrice;
    private String imageUrl;
    private int goodsNumber;
    private String payType;
    private float memberPrice;
    private String goodsName;
    private float totalPrice;
    private String goodsSpecInfo;

    public String getGoodsSpecInfo() {
        return goodsSpecInfo;
    }

    public void setGoodsSpecInfo(String goodsSpecInfo) {
        this.goodsSpecInfo = goodsSpecInfo;
    }

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

    public String getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(String shopCardId) {
        this.shopCartId = shopCardId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public float getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(float memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (storeId != null ? !storeId.equals(cart.storeId) : cart.storeId != null) return false;
        if (shopCartId != null ? !shopCartId.equals(cart.shopCartId) : cart.shopCartId != null)
            return false;
        return !(goodsId != null ? !goodsId.equals(cart.goodsId) : cart.goodsId != null);

    }

    @Override
    public int hashCode() {
        int result = storeId != null ? storeId.hashCode() : 0;
        result = 31 * result + (shopCartId != null ? shopCartId.hashCode() : 0);
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        return result;
    }
}
