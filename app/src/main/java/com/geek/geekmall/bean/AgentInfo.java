package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 6/30/15.
 */
public class AgentInfo implements Serializable {
    /**
     *
     */
    private String id;
    private String imgUrl;
    /**
     *
     */
    private String allPrice;
    /**
     *
     */
    private int level;
    /**
     *
     */
    private long overTime;
    /**
     *
     */
    private String nickname;
    /**
     * 已到账
     */
    private String allTruePrice;
    /**
     *
     */
    private int customerNumber;

    private String userPhoto;
    private String identity;
    private String name;
    private String alipayName;
private int isCheck;

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String totalPrice;
    private String bindingToken;

    public String getBindingToken() {
        return bindingToken;
    }

    public void setBindingToken(String bindingToken) {
        this.bindingToken = bindingToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(String allPrice) {
        this.allPrice = allPrice;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getOverTime() {
        return overTime;
    }

    public void setOverTime(long overTime) {
        this.overTime = overTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAllTruePrice() {
        return allTruePrice;
    }

    public void setAllTruePrice(String allTruePrice) {
        this.allTruePrice = allTruePrice;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
