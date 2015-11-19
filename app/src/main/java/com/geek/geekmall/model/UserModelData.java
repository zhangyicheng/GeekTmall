package com.geek.geekmall.model;

import com.geek.geekmall.bean.AgentInfo;
import com.geek.geekmall.bean.OrderInfo;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.bean.UserMoney;

/**
 * Created by apple on 6/8/15.
 */
public class UserModelData {
    private AgentInfo agentInfo;
    private OrderInfo orderInfo;
    private UserMoney userMoney;

    private User userInfo;

    public UserMoney getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(UserMoney userMoney) {
        this.userMoney = userMoney;
    }

    public AgentInfo getAgentInfo() {
        return agentInfo;
    }

    public void setAgentInfo(AgentInfo agentInfo) {
        this.agentInfo = agentInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

}
