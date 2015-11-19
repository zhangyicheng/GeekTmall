package com.geek.geekmall.model;

import com.geek.geekmall.bean.Advertisement;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.Theme;

import java.util.List;

/**
 * Created by apple on 5/29/15.
 */
public class HomeData {
    private int status;
    private String errorMsg;
    private HomeData data;
    private List<Advertisement> scroll;
    private Page<Product> findWant;
    private Page<Theme> theme;
    private Page<Product> recommend;

    public HomeData getData() {
        return data;
    }

    public void setData(HomeData data) {
        this.data = data;
    }

    public List<Advertisement> getScroll() {
        return scroll;
    }

    public void setScroll(List<Advertisement> scroll) {
        this.scroll = scroll;
    }

    public Page<Product> getFindWant() {
        return findWant;
    }

    public void setFindWant(Page<Product> findWant) {
        this.findWant = findWant;
    }

    public Page<Theme> getTheme() {
        return theme;
    }

    public void setTheme(Page<Theme> theme) {
        this.theme = theme;
    }

    public Page<Product> getRecommend() {
        return recommend;
    }

    public void setRecommend(Page<Product> recommend) {
        this.recommend = recommend;
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
