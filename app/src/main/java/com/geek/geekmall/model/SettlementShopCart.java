package com.geek.geekmall.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 7/1/15.
 */
public class SettlementShopCart implements Serializable{
    private String storeId;
    private String storeName;
    private String totalNum;
    private String totalPrice;
    private List<DistributionWay> distributionWay;
    private List<GoodsInfoMap> goodsInfoMap;
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

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<DistributionWay> getDistributionWay() {
        return distributionWay;
    }

    public void setDistributionWay(List<DistributionWay> distributionWay) {
        this.distributionWay = distributionWay;
    }

    public List<GoodsInfoMap> getGoodsInfoMap() {
        return goodsInfoMap;
    }

    public void setGoodsInfoMap(List<GoodsInfoMap> goodsInfoMap) {
        this.goodsInfoMap = goodsInfoMap;
    }
}
