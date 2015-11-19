package com.geek.geekmall.bean;

/**
 * Created by apple on 7/2/15.
 */
public class ReFundType {
    private int id;
    private String name;

    public ReFundType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
