package com.geek.geekmall.model;

import com.geek.geekmall.bean.User;

/**
 * Created by apple on 6/8/15.
 */
public class UserPageData {

    private Page<User> pager;
    private UserOrder userOrder;

    public Page<User> getPager() {
        return pager;
    }

    public void setPager(Page<User> pager) {
        this.pager = pager;
    }

    public UserOrder getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(UserOrder userOrder) {
        this.userOrder = userOrder;
    }
}
