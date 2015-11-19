package com.geek.geekmall.bean;

/**
 * 广告
 *
 * @author zhangbob
 */
public class Advertisement {

    private String id;
    /**
     * 图片地址
     */
    private String imgUrl;
    private String createTime;
    /**
    点击跳转相关ID（结合link_type）
     */
    private String linkTypeId;
    /**
     * 轮播描述
     */
    private String discription;
    /**
     * 轮播展示顺序(已排序)
     */
    private String showOrder;
    /**
     * 部轮跳转类型（1：商品列表；2：商品详情）
     */
    private String linkType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLinkTypeId() {
        return linkTypeId;
    }

    public void setLinkTypeId(String linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "imgUrl='" + imgUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                ", linkTypeId='" + linkTypeId + '\'' +
                '}';
    }
}