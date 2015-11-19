package com.geek.geekmall;

import android.app.Application;
import android.content.Context;

import com.geek.geekmall.bean.AgentInfo;
import com.geek.geekmall.bean.OrderInfo;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.bean.UserMoney;
import com.geek.geekmall.http.RequestManager;
import com.geek.geekmall.utils.AppUtils;
import com.geek.geekmall.utils.CrashHandler;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.SDCardUtils;
import com.geek.geekmall.utils.SharedPreUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by apple on 4/22/15.
 */
public class GeekApplication extends Application {
    private static GeekApplication mInstance;

    private static User user = null;
    private static AgentInfo agentInfo = null;
    private static OrderInfo orderInfo = null;
    private static UserMoney userMoney = null;
    private static User token = null;
    /**
     * 订单支付0 还是充值1、升级2
     */
    private static int type = 0;
    public static String PATH = SDCardUtils.getSDCardPath() + "geekmall/";

    public static GeekApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        init();
        SharedPreUtil.initSharedPreference(getApplicationContext());
        AppUtils.getDeviceInfo(this);
//        CrashHandler.getInstance().init(getApplicationContext());
    }

    public static void setType(int type) {
        GeekApplication.type = type;
    }

    public static int getType() {
        return type;
    }

    public static User getUser() {
        if (user == null) {
            //处理崩溃的情况
//            user = SharedPreUtil.getInstance().getUser();
        }
        return user;
    }

    public static User getToken() {
        token = SharedPreUtil.getInstance().getUser();
        return token;
    }

    public static boolean isLogin() {
        user = getUser();
        MyLog.debug(GeekApplication.class, "---------------isLogin---------" + user);
        if (user != null) {
            return true;
        }
        return false;
    }

    public static void setUser(User user) {
        GeekApplication.user = user;
    }

    public static void setAgentInfo(AgentInfo agentInfo) {
        GeekApplication.agentInfo = agentInfo;
    }

    public static void setOrderInfo(OrderInfo orderInfo) {
        GeekApplication.orderInfo = orderInfo;
    }

    public static AgentInfo getAgentInfo() {
        return agentInfo;
    }

    public static OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public static void setUserMoney(UserMoney userMoney) {
        GeekApplication.userMoney = userMoney;
    }

    public static UserMoney getUserMoney() {
        return userMoney;
    }

    private void init() {
        RequestManager.init(this);
    }


}
