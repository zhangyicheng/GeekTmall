package com.geek.geekmall.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class BuyInfo implements Serializable{
    /**
     * 合并支付总金额
     */
    private float totalPrice;
    private float payMoney;
    /**
     * 用户总余额
     */
    private float userTotalBalance;
    /**
     * 本单能够使用的余额
     */
    private float canUseBalance;
    /**
     * 用户所有特特币
     */
    private float userTotalCoin;
    /**
     * 本单能够使用的特特币
     */
    private float canUseCoin;
    /**
     * 本单返利（特特币）
     */
    private float totalRebateCoin;
    /**
     * 是否能够用特特币支付（1：是；0：否）
     */
    private float isPayCoin;
    /**
     * 是否能够用余额支付（
     * 1：是；0：否）
     */
    private float isPayBalance;
    /**
     * 已经使用返还余额
     */
private float allreadyUseCoin;
    /**
     * 已经使用钱包余额
     */
    private float allreadyUseBalance;
    /**
     * 是否勾选（1：勾选；0：未勾选）
     */
    private int coinSelect;

    public int getCoinSelect() {
        return coinSelect;
    }

    public void setCoinSelect(int coinSelect) {
        this.coinSelect = coinSelect;
    }

    public float getPayMoney() {
        return payMoney;
    }

    public float getAllreadyUseCoin() {
        return allreadyUseCoin;
    }

    public void setAllreadyUseCoin(float allreadyUseCoin) {
        this.allreadyUseCoin = allreadyUseCoin;
    }

    public float getAllreadyUseBalance() {
        return allreadyUseBalance;
    }

    public void setAllreadyUseBalance(float allreadyUseBalance) {
        this.allreadyUseBalance = allreadyUseBalance;
    }

    public void setPayMoney(float payMoney) {
        this.payMoney = payMoney;
    }

    private List<SettlementShopCart> settlementShoppCartList;

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getUserTotalBalance() {
        return userTotalBalance;
    }

    public void setUserTotalBalance(float userTotalBalance) {
        this.userTotalBalance = userTotalBalance;
    }

    public float getCanUseBalance() {
        return canUseBalance;
    }

    public void setCanUseBalance(float canUseBalance) {
        this.canUseBalance = canUseBalance;
    }

    public float getUserTotalCoin() {
        return userTotalCoin;
    }

    public void setUserTotalCoin(float userTotalCoin) {
        this.userTotalCoin = userTotalCoin;
    }

    public float getCanUseCoin() {
        return canUseCoin;
    }

    public void setCanUseCoin(float canUseCoin) {
        this.canUseCoin = canUseCoin;
    }

    public float getTotalRebateCoin() {
        return totalRebateCoin;
    }

    public void setTotalRebateCoin(float totalRebateCoin) {
        this.totalRebateCoin = totalRebateCoin;
    }

    public float getIsPayCoin() {
        return isPayCoin;
    }

    public void setIsPayCoin(float isPayCoin) {
        this.isPayCoin = isPayCoin;
    }

    public float getIsPayBalance() {
        return isPayBalance;
    }

    public void setIsPayBalance(float isPayBalance) {
        this.isPayBalance = isPayBalance;
    }

    public List<SettlementShopCart> getSettlementShoppCartList() {
        return settlementShoppCartList;
    }

    public void setSettlementShoppCartList(List<SettlementShopCart> settlementShoppCartList) {
        this.settlementShoppCartList = settlementShoppCartList;
    }

}
