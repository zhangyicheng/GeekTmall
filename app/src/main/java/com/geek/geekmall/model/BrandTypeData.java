package com.geek.geekmall.model;

import com.geek.geekmall.bean.Area;
import com.geek.geekmall.bean.Brand;

import java.util.List;

/**
 * Created by apple on 6/8/15.
 */
public class BrandTypeData {
    private List<Brand> brandList;
    private List<Brand> recommendBrandList;

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Brand> getRecommendBrandList() {
        return recommendBrandList;
    }

    public void setRecommendBrandList(List<Brand> recommendBrandList) {
        this.recommendBrandList = recommendBrandList;
    }

}
