package com.geek.geekmall.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 物流信息
 * Created by apple on 7/21/15.
 */
public class Logistics implements Serializable {
    private String expressNo;
    private String logisticsName;
    private Address address;
    private List<LogisticsData> logisticsData;

    public Address getAddress() {
        return address;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<LogisticsData> getLogisticsData() {
        return logisticsData;
    }

    public void setLogisticsData(List<LogisticsData> logisticsData) {
        this.logisticsData = logisticsData;
    }
}
