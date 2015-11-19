package com.geek.geekmall.model;

import java.io.Serializable;

/**
 * Created by apple on 7/1/15.
 */
public class DistributionWay implements Serializable{
    private int type;
    private float postage;
    private int isSelected;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getPostage() {
        return postage;
    }

    public void setPostage(float postage) {
        this.postage = postage;
    }

    public int isSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }
}
