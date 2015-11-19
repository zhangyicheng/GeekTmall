package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 6/26/15.
 */
public class Income implements Serializable{
    private String createTime;
    /**
     * 所有未到账
     */
    private String allFalseCutPrice;
    private String id;
    /**
     * 所有已到账
     */
    private String allTrueCutPrice;
    /**
     * 已到账
     */
    private String trueCutPrice;
    /**
     * 未到账
     */
    private String falseCutPrice;

    /**
     * 已到账销售额
     */
    private String allTruePrice;
    /**
     * 未到账销售额
     */
    private String allFalsePrice;

private String cutRatio;

    public String getCutRatio() {
        return cutRatio;
    }

    public void setCutRatio(String cutRatio) {
        this.cutRatio = cutRatio;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAllFalseCutPrice() {
        return allFalseCutPrice;
    }

    public void setAllFalseCutPrice(String allFalseCutPrice) {
        this.allFalseCutPrice = allFalseCutPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAllTrueCutPrice() {
        return allTrueCutPrice;
    }

    public void setAllTrueCutPrice(String allTrueCutPrice) {
        this.allTrueCutPrice = allTrueCutPrice;
    }

    public String getTrueCutPrice() {
        return trueCutPrice;
    }

    public void setTrueCutPrice(String trueCutPrice) {
        this.trueCutPrice = trueCutPrice;
    }

    public String getFalseCutPrice() {
        return falseCutPrice;
    }

    public void setFalseCutPrice(String falseCutPrice) {
        this.falseCutPrice = falseCutPrice;
    }

    public String getAllTruePrice() {
        return allTruePrice;
    }

    public void setAllTruePrice(String allTruePrice) {
        this.allTruePrice = allTruePrice;
    }

    public String getAllFalsePrice() {
        return allFalsePrice;
    }

    public void setAllFalsePrice(String allFalsePrice) {
        this.allFalsePrice = allFalsePrice;
    }
}
