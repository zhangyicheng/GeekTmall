package com.geek.geekmall.bean;

/**
 * Created by apple on 9/1/15.
 */
public class AgentSidebar {
    private String imgUrl;
    private String nickname;
    /**
     * 客户人数
     */
    private int customerNumber;
    /**
     * 销售总额
     */
    private float totalPrice;
    /**
     * 总收入
     */
    private float allPrice;
    /**
     * 可提现
     */
    private float allTruePrice;
    private int level;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(float allPrice) {
        this.allPrice = allPrice;
    }

    public float getAllTruePrice() {
        return allTruePrice;
    }

    public void setAllTruePrice(float allTruePrice) {
        this.allTruePrice = allTruePrice;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
