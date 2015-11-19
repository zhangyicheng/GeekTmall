package com.geek.geekmall.model;

import java.io.Serializable;

/**
 * Created by apple on 7/1/15.
 */
public class GoodsInfoMap implements Serializable {
    private String imageUrl;
    private String goodsId;
    private String shopCartId;
    private String goodsSpecInfo;
    private String marketPrice;
    private String goodsNumber;
    private String memberPrice;
    private String goodsName;
    /**
     * 是否是活动商品
     */
    private int isFirstFree;
    /**
     * 单品返利（特特币）
     */
    private float rebateCoin;
    /**
     * 是否勾选（1：勾选；0：未勾选）
     */
    private int coinSelect;
    /**
     * 勾选框是否禁用（1：可用；0：禁用）
     */
    private int coinEnabled;
    /**
     * 该商品能使用的特特币数量
     */
    private String canUseCoin;
//    private String payType;
    /**
     * 商品小计
     */
    private String totalPrice;

    public String getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(String shopCartId) {
        this.shopCartId = shopCartId;
    }

    public int getIsFirstFree() {
        return isFirstFree;
    }

    public void setIsFirstFree(int isFirstFree) {
        this.isFirstFree = isFirstFree;
    }

    public float getRebateCoin() {
        return rebateCoin;
    }

    public void setRebateCoin(float rebateCoin) {
        this.rebateCoin = rebateCoin;
    }

    public int getCoinSelect() {
        return coinSelect;
    }

    public void setCoinSelect(int coinSelect) {
        this.coinSelect = coinSelect;
    }

    public int getCoinEnabled() {
        return coinEnabled;
    }

    public void setCoinEnabled(int coinEnabled) {
        this.coinEnabled = coinEnabled;
    }

    public String getCanUseCoin() {
        return canUseCoin;
    }

    public void setCanUseCoin(String canUseCoin) {
        this.canUseCoin = canUseCoin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsSpecInfo() {
        return goodsSpecInfo;
    }

    public void setGoodsSpecInfo(String goodsSpecInfo) {
        this.goodsSpecInfo = goodsSpecInfo;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
