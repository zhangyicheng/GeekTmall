package com.geek.geekmall.http;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.geek.geekmall.bean.Cart;
import com.geek.geekmall.bean.FavorProduct;
import com.geek.geekmall.bean.FootPrintProduct;
import com.geek.geekmall.utils.MyLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by apple on 5/28/15.
 */
public class ApiParams {
    /**
     * 手机号登录
     *
     * @param context
     * @param name
     * @param password
     * @return
     */
    public static HashMap getLoginParams(Context context, String name, String password) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Build bd = new Build();
        String model = bd.MODEL;
        HashMap map = new HashMap<>();
        map.put("username", name);
        map.put("password", password);
        map.put("deviceId", JPushInterface.getRegistrationID(context));
        map.put("deviceName", model);
        map.put("deviceType", "1");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 第三方自动登录
     *
     * @param context
     * @param openId
     * @param accessToken
     * @param type
     * @return
     */
    public static HashMap getLoginAuthParams(Context context, String openId, String accessToken, String type) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Build bd = new Build();
        String model = bd.MODEL;
        HashMap map = new HashMap<>();
        map.put("openId", openId);
        map.put("loginType", type);
        map.put("accessToken", accessToken);
        map.put("deviceName", model);
        map.put("deviceType", "1");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 自动登录
     *
     * @param context
     * @param token
     * @return
     */
    public static HashMap getAutoLoginParams(Context context, String token) {
        HashMap map = new HashMap<>();
        map.put("token", token);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 手机号注册
     *
     * @param context
     * @param phone
     * @param pwd
     * @param twopwd
     * @return
     */
    public static HashMap getRegisterParams(Context context, String phone,
                                            String pwd, String twopwd) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Build bd = new Build();
        String model = bd.MODEL;
        HashMap map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", pwd);
        map.put("twoPassword", twopwd);
        map.put("deviceName", model);
        map.put("deviceType", "1");
        map.put("deviceId", JPushInterface.getRegistrationID(context));
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 获取验证码
     *
     * @param context
     * @param phone
     * @param type
     * @return
     */
    public static HashMap getCodeParams(Context context, String phone,
                                        String type) {
        HashMap map = new HashMap<>();
        map.put("phone", phone);
        map.put("type", type);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 验证验证码
     *
     * @param context
     * @param phone
     * @param phoneCode
     * @return
     */
    public static HashMap checkCodeParams(Context context, String phone,
                                          String phoneCode) {
        HashMap map = new HashMap<>();
        map.put("phone", phone);
        map.put("phoneCode", phoneCode);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 忘记密码
     *
     * @param context
     * @param phone
     * @param password
     * @param twoPassword
     * @return
     */
    public static HashMap forgetPwdParams(Context context, String phone,
                                          String password, String twoPassword) {
        HashMap map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("twoPassword", twoPassword);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 添加想要
     * @param context
     * @param userId
     * @param token
     * @param productId
     * @return
     */
    public static HashMap saveWantParams(Context context, String userId,
                                          String token, String productId) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("goodsId", productId);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    /**
     * 修改密码
     *
     * @param context
     * @param userId
     * @param password
     * @param formerPassword
     * @param newFormerPassword
     * @return
     */
    public static HashMap changePwdParams(Context context, String userId,
                                          String password, String formerPassword,String newFormerPassword) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("password", password);
        map.put("formerPassword", formerPassword);
        map.put("newFormerPassword", newFormerPassword);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    public static HashMap changePayPwdParams(Context context, String userId,
                                          String phone, String phoneCode,String payPwd,String twoPayPwd) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("phone", phone);
        map.put("phoneCode",phoneCode);
        map.put("payPwd", payPwd);
        map.put("twoPayPwd", twoPayPwd);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    public static HashMap checkPayPwdParams(Context context, String userId, String payPwd) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("payPwd", payPwd);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    /**
     * 第三方授权注册
     *
     * @param context
     * @param openId
     * @param phone
     * @param phoneCode
     * @param accessToken
     * @param authType
     * @param imgUrl
     * @param nikename
     * @param sex
     * @return
     */
    public static HashMap thirdAuth(Context context, String openId,
                                    String phone, String phoneCode,
                                    String accessToken, int authType,
                                    String imgUrl, String nikename,
                                    int sex) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Build bd = new Build();
        String model = bd.MODEL;
        HashMap map = new HashMap<>();
        map.put("openId", openId);
        map.put("phone", phone);
        map.put("phoneCode", phoneCode);
        map.put("accessToken", accessToken);
        map.put("authType", authType + "");
        map.put("deviceName", model);
        map.put("deviceType", "1");
        map.put("imgUrl", imgUrl);
        map.put("nickname", nikename);
        map.put("sex", sex + "");
        map.put("deviceId", JPushInterface.getRegistrationID(context));
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 获取首页
     *
     * @param context
     * @param userId
     * @param token
     * @param pageSize
     * @return
     */
    public static HashMap getHomeParams(Context context, String userId,
                                        String token, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("pageSize", pageSize + "");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 获取单个列表
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static HashMap getListParams(Context context, String userId,
                                        String token, int currentPage, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 获取绑定状态
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap getBindStatusParams(Context context, String userId,
                                              String token) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 绑定
     *
     * @param context
     * @param openId
     * @param authType
     * @param phone
     * @param phoneCode
     * @param bindingType
     * @return
     */
    public static HashMap bindParams(Context context, String openId,
                                     String authType, String phone,
                                     String phoneCode, String bindingType) {
        HashMap map = new HashMap<>();
        map.put("openId", openId);
        map.put("authType", authType);
        map.put("phone", phone);
        map.put("phoneCode", phoneCode);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 修改用户信息
     *
     * @param context
     * @param userId
     * @param photo
     * @param nickName
     * @param sex
     * @return
     */
    public static HashMap modifyUserInfoParams(Context context, String userId, String photo,
                                               String nickName, int sex) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("photo", photo);
        map.put("nickName", nickName);
        map.put("sex", sex + "");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    public static HashMap uploadPhoto(Context context,List<String> imgSource,String path){
        HashMap map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
//        builder.append("[");
        for (String photo : imgSource) {
            builder.append(photo).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
//        builder.append("]");
        map.put("imgSource", builder.toString());
        map.put("filePath", path);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 获取地址列表
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap getAddressListParams(Context context, String userId, String token) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 保存地址
     *
     * @param context
     * @param userId
     * @param token
     * @param provinceId
     * @param cityId
     * @param zoneId
     * @param address
     * @param contactName
     * @param contactPhone
     * @param isDefault
     * @return
     */
    public static HashMap saveAddressParams(Context context, String userId, String token,
                                            String provinceId, String cityId,
                                            String zoneId, String address,
                                            String contactName, String contactPhone,
                                            int isDefault
    ) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("zoneId", zoneId);
        map.put("address", address);
        map.put("contactName", contactName);
        map.put("contactPhone", contactPhone);
        map.put("isDefault", isDefault + "");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 删除地址
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap deleteAddressParams(Context context, String userId, String token) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 收藏列表
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static HashMap getFavorListParams(Context context, String userId, String token,
                                             int currentPage, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 删除收藏
     *
     * @param context
     * @param userId
     * @param token
     * @param productList
     * @return
     */
    public static HashMap deleteFavorListParams(Context context, String userId, String token,
                                                List<FavorProduct> productList) {
        HashMap map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        for (FavorProduct product : productList) {
            builder.append(product.getGoodsId()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        map.put("userId", userId);
        map.put("token", token);
        map.put("ids", builder.toString());
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    public static HashMap deleteFavorListParams(Context context, String userId, String token,
                                                String productId) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("ids", productId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    /**
     * 获取首页数据
     *
     * @param context
     * @param pageSize
     * @return
     */
    public static HashMap getStoresParams(Context context, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("pageSize", pageSize + "");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 根据类型获取首页数据
     *
     * @param context
     * @param categoryId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static HashMap getStoresByParams(Context context, String categoryId, String token,
                                            int currentPage, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("categoryId", categoryId);
        map.put("token", token);
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 商品详情
     *
     * @param context
     * @param userId
     * @param token
     * @param id
     * @return
     */
    public static HashMap getProductInfoParams(Context context, String userId, String token,
                                               String id) {
        HashMap map = new HashMap<>();
        map.put("token", token);
        map.put("id", id);
        map.put("userId", userId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 修改用户信息
     *
     * @param context
     * @param userId
     * @param photo
     * @param nickName
     * @param sex
     * @return
     */
    public static HashMap updateUserInfoParams(Context context, String userId, String photo,
                                               String nickName, int sex) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("photo", photo);
        map.put("nickName", nickName);
        map.put("sex", sex);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 关键字获取
     *
     * @param context
     * @param pageSize
     * @return
     */
    public static HashMap searchKeywordsParams(Context context, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("pageSize", pageSize + "");

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 自动补全
     *
     * @param context
     * @param words
     * @return
     */
    public static HashMap autoKeywordsParams(Context context, String words) {
        HashMap map = new HashMap<>();
        map.put("keyWords", words);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 搜索列表
     *
     * @param context
     * @param pageSize
     * @param currentPage
     * @param brandId
     * @param keyWords
     * @param userId
     * @return
     */
    public static HashMap searchListParams(Context context, int pageSize, int currentPage,
                                           String categoryId, String brandId,
                                           String keyWords, String userId) {
        HashMap map = new HashMap<>();
        map.put("pageSize", pageSize + "");
        map.put("currentPage", currentPage + "");
        map.put("categoryId", categoryId);
        map.put("brandId", brandId);
        map.put("keyWords", keyWords);
        map.put("userId", userId);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 通用
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap commonParams(Context context, String userId, String token) {
        HashMap map = new HashMap<>();
        map.put("userId", userId+"");
        map.put("token", token+"");

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 通用
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap commonPageParams(Context context, String userId, String token,
                                           int currentPage, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 反馈
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap feedbackParams(Context context, String userId, String token, String content) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("content", content);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 删除足迹
     *
     * @param context
     * @param productList
     * @param token
     * @return
     */
    public static HashMap deleteFootPrintParams(Context context, List<FootPrintProduct> productList, String userId, String token) {
        HashMap map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        for (FootPrintProduct product : productList) {
            builder.append(product.getId()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        map.put("id", builder.toString());
        map.put("token", token);
        map.put("userId", userId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 增加购物车
     *
     * @param context
     * @param userId
     * @param token
     * @param goodsId
     * @param goodsNumber
     * @param goodsSpecId
     * @return
     */
    public static HashMap addCartParams(Context context, String userId, String token,
                                        String goodsId, String goodsNumber, String goodsSpecId) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("goodsId", goodsId);
        map.put("goodsNumber", goodsNumber);
        map.put("goodsSpecId", goodsSpecId);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    public static HashMap calculateCartParams(Context context, String submitOrderInfo, String token) {
        HashMap map = new HashMap<>();
        map.put("submitOrderInfo", submitOrderInfo);
        map.put("token", token);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 删除购物车
     *
     * @param context
     * @param userId
     * @param token
     * @param productList
     * @return
     */
    public static HashMap deleteCartParams(Context context, String userId, String token,
                                           List<Cart> productList) {
        HashMap map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        for (Cart product : productList) {
            builder.append(product.getShopCartId()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        map.put("userId", userId);
        map.put("token", token);
        map.put("shopCartIds", builder.toString());

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 结算购物车
     *
     * @param context
     * @param userId
     * @param token
     * @param productList
     * @return
     */
    public static HashMap settlementCartParams(Context context, String userId, String token,
                                               List<Cart> productList) {
        HashMap map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        if (productList != null) {
            for (Cart cart : productList) {
                builder.append(cart.getShopCartId()).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
        }

        map.put("userId", userId);
        map.put("token", token);
        map.put("shopCartId", builder.toString());

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 修改购物车
     *
     * @param context
     * @param userId
     * @param token
     * @param goodsNumber
     * @return
     */
    public static HashMap modifyCartParams(Context context, String userId, String token,
                                           String goodsNumber) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("goodsNumber", goodsNumber);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 订单
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap orderParams(Context context, String userId, String token,
                                      int currentPage, int pageSize, int type) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");
        map.put("type", type + "");

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 删除订单
     *
     * @param context
     * @param userId
     * @param token
     * @param orderId
     * @return
     */
    public static HashMap deleteOrderParams(Context context, String userId, String token, String orderId) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("id", orderId);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 取消订单
     *
     * @param context
     * @param userId
     * @param token
     * @param orderId
     * @return
     */
    public static HashMap cancelOrderParams(Context context, String userId, String token, String orderId) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("id", orderId);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 增加评论
     *
     * @param context
     * @return
     */
    public static HashMap addCommentParams(Context context, String data) {
        HashMap map = new HashMap<>();
        map.put("data", data);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 根据主题获取商品
     *
     * @param context
     * @param themeId
     * @param order
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static HashMap getThemeProductParams(Context context, String themeId, String order,
                                                int currentPage, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("themeId", themeId);
        map.put("order", order);
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 获取总收入
     *
     * @param context
     * @param userId
     * @param token
     * @param agentId
     * @param currentPage
     * @param pageSize
     * @return
     */
    public static HashMap getInComeParams(Context context, String userId, String token,String date, String agentId,
                                          int currentPage, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("date", date);
        map.put("agentId", agentId);
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    /**
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap customerRankParams(Context context, String userId, String token,String agentId,
                                             int currentPage, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("agentId", agentId);
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    /**
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap productPhotoParams(Context context, String userId, String token, String goodsId) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("goodsId", goodsId);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public static HashMap productCommentParams(Context context, String userId, String token,
                                               String goodsId, int currentPage, int pageSize) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("token", token);
        map.put("goodsId", goodsId);
        map.put("currentPage", currentPage + "");
        map.put("pageSize", pageSize + "");
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 支付
     *
     * @param context
     * @return
     */
    public static HashMap productPayParams(Context context, String userId, String payOrderId, String type) {
        HashMap map = new HashMap<>();
        map.put("orderId", payOrderId);
        map.put("type", type);
        map.put("userId", userId);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 根据商品规格查询库存和价格
     *
     * @param context
     * @return
     */
    public static HashMap productPriceParams(Context context, String goodsId, String userId, String token,
                                             String format1, String format2, String format3) {
        HashMap map = new HashMap<>();
        map.put("goodsId", goodsId);
        map.put("token", token);
        map.put("format1", format1);
        map.put("format2", format2);
        map.put("format3", format3);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 购买参数
     *
     * @param context
     * @param goodsId
     * @param userId
     * @param token
     * @param goodsNumber
     * @param goodsSpecId
     * @return
     */
    public static HashMap buyParams(Context context, String goodsId, String userId, String token,
                                    String goodsNumber, String goodsSpecId) {
        HashMap map = new HashMap<>();
        map.put("goodsId", goodsId);
        map.put("token", token);
        map.put("userId", userId);
        map.put("goodsNumber", goodsNumber);
        map.put("goodsSpecId", goodsSpecId);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static HashMap submitOrderParams(Context context, String submitOrderInfo,
                                            String userId, String token) {
        HashMap map = new HashMap<>();
        map.put("submitOrderInfo", submitOrderInfo);
        map.put("token", token);
        map.put("userId", userId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static HashMap agentDetailParams(Context context, String token,
                                            int currentPage, String date) {
        HashMap map = new HashMap<>();
        map.put("token", token);
        map.put("currentPage", currentPage + "");
        map.put("date", date);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static HashMap updateParams(Context context, String type) {
        HashMap map = new HashMap<>();
        map.put("type", type);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static HashMap guessLikeParams(Context context, String userId,String pageSize) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("pageSize", pageSize);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static HashMap orderDetailParams(Context context, String orderId) {
        HashMap map = new HashMap<>();
        map.put("orderId", orderId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 充值
     * @param context
     * @param userId
     * @param rechargeType
     * @param payType
     * @param money
     * @return
     */
    public static HashMap rechargeParams(Context context, String userId,int rechargeType,
                                         int payType,int money) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("rechargeType", rechargeType+"");
        map.put("payType", payType+"");
        map.put("money", money+"");

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 售后
     * @param context
     * @param userId
     * @param goodsOrderId
     * @param totalPrice
     * @param refundType
     * @param reason
     * @param applyRemark
     * @param reasonImg
     * @return
     */
    public static HashMap applyParams(Context context, String userId,String goodsOrderId,String totalPrice,
                                      int refundType,int reason,String applyRemark,String reasonImg) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("goodsOrderId", goodsOrderId+"");
        map.put("totalPrice", totalPrice+"");
        map.put("refundType", refundType+"");
        map.put("reason", reason+"");
        map.put("applyRemark", applyRemark+"");
        map.put("reasonImg", reasonImg+"");


        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    public static HashMap identityParams(Context context, String agentId,String identity,String name,String alipayName) {
        HashMap map = new HashMap<>();
        map.put("agentId", agentId);
        map.put("identity", identity);
        map.put("name", name);
        map.put("alipayName", alipayName);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static HashMap findAgentByIdParams(Context context, String agentId) {
        HashMap map = new HashMap<>();
        map.put("agentId", agentId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static Map customerServicesParams(Context context, String goodsOrderId) {
        HashMap map = new HashMap<>();
        map.put("goodsOrderId", goodsOrderId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static Map cancelServiceParams(Context context, String id) {
        HashMap map = new HashMap<>();
        map.put("id", id);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static Map addLogisticsParams(Context context, String id, String sendNo, String logisticsComp, String logisticsRemark) {
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("sendNo", sendNo);
        map.put("logisticsComp", logisticsComp);
        map.put("logisticsRemark", logisticsRemark);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static Map updateApplyParams(Context context, String id, String totalPrice, int refundType, int reason, String applyRemark, String reasonImg) {
        HashMap map = new HashMap<>();
        map.put("id", id);
        map.put("totalPrice", totalPrice);
        map.put("refundType", refundType+"");
        map.put("reason", reason+"");
        map.put("applyRemark", applyRemark);
        map.put("reasonImg", reasonImg);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    /**
     * 确认收货
     * @param context
     * @param orderId
     * @return
     */
    public static Map confirmReceiveParams(Context context, String orderId) {
        HashMap map = new HashMap<>();
        map.put("orderId", orderId);

        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    public static Map withDrawParams(Context context, String userId,String cashMoney) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        map.put("cashMoney", cashMoney);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
    public static Map agentSidebarParams(Context context, String userId) {
        HashMap map = new HashMap<>();
        map.put("userId", userId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static Map getBannerByTypeParams(Context context, String linkTypeId, String type) {
        HashMap map = new HashMap<>();
        map.put("linkTypeId", linkTypeId);
        map.put("type", type);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static Map getDeleteWantParams(Context context, String goodsId, String userId) {
        HashMap map = new HashMap<>();
        map.put("goodsId", goodsId);
        map.put("userId", userId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }

    public static Map subUserMoneyParams(Context context, String submitOrderId) {
        HashMap map = new HashMap<>();
        map.put("submitOrderId", submitOrderId);
        MyLog.debug(ApiParams.class, map.toString());
        return map;
    }
}
