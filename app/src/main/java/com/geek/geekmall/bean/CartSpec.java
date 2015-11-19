package com.geek.geekmall.bean;

import java.util.List;

/**
 * Created by apple on 6/18/15.
 */
public class CartSpec {
    private String id;
    private String stock;
    private String memberPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }
}
