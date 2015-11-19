package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 5/13/15.
 */
public class User implements Serializable {
    private int status;
    private User data;
    private String errorMsg;
    private String id;
    private String nickname;
    private String phone;
    private String imgUrl;
    private float cutPrice;
    private float totalPrice;
    private String userPhoto;
    private String email;
    private String token;
    private String openId;
    private String accessToken;
    private int sex;
    private String password;
    private int customerNumber;
    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public float getCutPrice() {
        return cutPrice;
    }

    public void setCutPrice(float cutPrice) {
        this.cutPrice = cutPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return "User{" +
                "sex=" + sex +
                ", openId='" + openId + '\'' +
                ", token='" + token + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    public void setErrorMsg(String errorMsg) {

        this.errorMsg = errorMsg;
    }
}
