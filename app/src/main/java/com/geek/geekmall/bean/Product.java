package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * 物品
 * Created by apple on 4/28/15.
 */
public class Product implements Serializable {
    private String id;
    private String goodsId;
    private String imgUrl;
    private String marketPrice;
    private String memberPrice;
    private String displayName;
    private String goodsName;
    private String sales;
    private String description;
    private String favourCount;
    private String storeId;
    private int isFavour;
    private String stock;
    private int isCheck;
    private int isFirstFree;
    private String favourNumber;
    private int findWantNumber;
    private int isFindWant;
    private int shoppingCartNumber;
    private String storeName;
    private String storeImg;
    private String postage;
    private String reachFree;

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getReachFree() {
        return reachFree;
    }

    public void setReachFree(String reachFree) {
        this.reachFree = reachFree;
    }

    public int getIsFirstFree() {
        return isFirstFree;
    }

    public void setIsFirstFree(int isFirstFree) {
        this.isFirstFree = isFirstFree;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public int getShoppingCartNumber() {
        return shoppingCartNumber;
    }

    public void setShoppingCartNumber(int shoppingCartNumber) {
        this.shoppingCartNumber = shoppingCartNumber;
    }

    public int getIsFindWant() {
        return isFindWant;
    }

    public void setIsFindWant(int isFindWant) {
        this.isFindWant = isFindWant;
    }

    public int getFindWantNumber() {
        return findWantNumber;
    }

    public void setFindWantNumber(int findWantNumber) {
        this.findWantNumber = findWantNumber;
    }

    public int isCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }


    public int getIsFavour() {
        return isFavour;
    }

    public void setIsFavour(int isFavour) {
        this.isFavour = isFavour;
    }

    public String getFavourCount() {
        return favourCount;
    }

    public void setFavourCount(String favourCount) {
        this.favourCount = favourCount;
    }

    public String getFavourNumber() {
        return favourNumber;
    }

    public void setFavourNumber(String favourNumber) {
        this.favourNumber = favourNumber;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
