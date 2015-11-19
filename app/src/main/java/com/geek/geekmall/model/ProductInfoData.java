package com.geek.geekmall.model;

import com.geek.geekmall.bean.GoodsAttr;
import com.geek.geekmall.bean.GoodsImages;
import com.geek.geekmall.bean.GoodsSpec;
import com.geek.geekmall.bean.Product;

import java.util.List;

/**
 * Created by apple on 6/3/15.
 */
public class ProductInfoData {
    private int status;
    private String errorMsg;
    private List<GoodsSpec> goodsSpec;
    private Product goodsInfo;
    private List<GoodsImages> goodsImages;
    private List<GoodsAttr> goodsAttr;
    private ProductInfoData data;

    public ProductInfoData getData() {
        return data;
    }

    public void setData(ProductInfoData data) {
        this.data = data;
    }

    public List<GoodsSpec> getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(List<GoodsSpec> goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public Product getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(Product goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public List<GoodsImages> getGoodsImages() {
        return goodsImages;
    }

    public void setGoodsImages(List<GoodsImages> goodsImages) {
        this.goodsImages = goodsImages;
    }

    public List<GoodsAttr> getGoodsAttr() {
        return goodsAttr;
    }

    public void setGoodsAttr(List<GoodsAttr> goodsAttrs) {
        this.goodsAttr = goodsAttrs;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
