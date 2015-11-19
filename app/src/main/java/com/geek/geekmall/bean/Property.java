package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * 物品属性
 * Created by apple on 4/28/15.
 */
public class Property implements Serializable {
    private int propertyId;
    private String color;
    private String size;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
