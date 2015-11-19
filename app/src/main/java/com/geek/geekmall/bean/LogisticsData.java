package com.geek.geekmall.bean;

import java.io.Serializable;

/**
 * Created by apple on 7/21/15.
 */
public class LogisticsData implements Serializable {
    private String context;
    private String ftime;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
