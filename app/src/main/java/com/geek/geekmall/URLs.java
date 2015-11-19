package com.geek.geekmall;

/**
 * Created by apple on 5/28/15.
 */
public class URLs {
    public static final String HOST = "http://www.geektmall.net/geektmall-api/";
//    public static final String HOST = "http://192.168.1.124:8080/geektmall-api/";
//    public static final String IMAGE_URL = "http://192.168.1.235/geektmall-upload";
//    public static final String UPLOAD_IMAGE_URL = "http://192.168.1.235/geektmall-upload/common/base64Upload";

//    public static final String HOST = "http://www.geektmall.net";
    public static final String IMAGE_URL = "http://www.geektmall.net/geektmall-upload";
    public static final String UPLOAD_IMAGE_URL = "http://www.geektmall.net/geektmall-upload/common/base64Upload";
    //登录 注册
    public static final String LOGIN_URL = HOST + "api/user/login/login";
    public static final String LOGIN_AUTH_URL = HOST + "api/user/login/auth";
    public static final String AUTO_LOGIN_URL = HOST + "api/user/login/autoLogin";
    public static final String REGISTER_URL = HOST + "api/user/register/save";
    public static final String CODE_URL = HOST + "api/user/register/getCode";
    public static final String CHECKCODE_URL = HOST + "api/user/register/checkCode";
    public static final String FORGET_PASSWORD_URL = HOST + "api/user/info/forgetPwd";
    public static final String CHANGE_PASSWORD_URL = HOST + "api/user/info/changePwd";
    public static final String THIRD_LOGIN_URL = HOST + "api/user/register/binding";
    public static final String RECHARGE_URL = HOST + "api/user/recharge";
    public static final String APPLY_URL = HOST + "api/refund/add";
    public static final String APPLY_SERVICES_URL = HOST + "api/refund/refundList";
    public static final String CANCEL_SERVICES_URL = HOST + "api/refund/cancel";
    public static final String ADD_LOGISTICS_URL = HOST + "api/refund/logistics";
    public static final String UPDATE_SERVICES_URL = HOST + "api/refund/update";


    //首页
    public static final String HOME_DATA_URL = HOST + "api/home/data";
    public static final String HOME_RECOMMEND_URL = HOST + "api/home/recommend";
    public static final String HOME_THEME_URL = HOST + "api/home/theme";
    public static final String HOME_FIND_WANT_URL = HOST + "api/home/findWant";
    public static final String ADD_FIND_WANT_URL = HOST + "api/findWant/saveMyWant";
    public static final String DELETE_FIND_WANT_URL = HOST + "api/findWant/deleteMyWant";
    public static final String PRODUCT_INFO_URL = HOST + "api/goods/goodsInfo";
    public static final String PRODUCT_INFO_PHOTO_URL = HOST + "api/goods/goodsInfoPhoto";
    public static final String PRODUCT_INFO_COMMENT_URL = HOST + "api/goods/goodsInfoComment";
    public static final String PRODUCT_PRICE_URL = HOST + "api/goods/findSpec";
    public static final String PRODUCT_GUESS_URL = HOST + "api/goods/guess";


    //设置 用户

    public static final String PAY_PASSWORD_URL = HOST + "api/user/info/installPayPwd";
    public static final String CHECK_PAY_PASSWORD_URL = HOST + "api/user/info/checkPayPwd";
    public static final String BIND_STATUS_URL = HOST + "api/user/info/binding/status";
    public static final String BIND_URL = HOST + "api/user/info/binding";
    public static final String MODIFY_USER_INFO_URL = HOST + "api/user/info/update";
    public static final String GET_ADDRESSLIST_URL = HOST + "api/user/address/list";
    public static final String GET_DEFAULT_ADDRESS_URL = HOST + "api/user/address/default";
    public static final String SAVE_ADDRESS_URL = HOST + "api/user/address/save";
    public static final String UPDATE_ADDRESS_URL = HOST + "api/user/address/";
    public static final String DELETE_ADDRESS_URL = HOST + "api/user/address/delete/";
    public static final String GET_FAVORS_URL = HOST + "api/user/favour/goods/list";
    public static final String ADD_FAVORS_URL = HOST + "api/user/favour/goods/";
    public static final String DELETE_FAVORS_URL = HOST + "api/user/favour/goods/delete";

    public static final String FEEDBACK_URL = HOST + "api/system/feedback/save";
    public static final String FOOTPRINT_URL = HOST + "api/history/allHistory";
    public static final String DELETE_FOOTPRINT_URL = HOST + "api/history/deleteHistory";
    public static final String HOME_MESSAGE_URL = HOST + "api/user/userHomeMessage";


    //订单列表
    public static final String GET_ORDERLIST_URL = HOST + "api/order/allOrder";
    public static final String GET_ORDERLIST_BY_TYPE_URL = HOST + "api/order/orderByType";
    public static final String ADD_COMMENT_URL = HOST + "api/goods/saveComment";
    public static final String DELETE_ORDER_URL = HOST + "api/order/deleteOrder";
    public static final String CANCEL_ORDER_URL = HOST + "api/order/cancelOrder";
    public static final String CALCULATE_ORDER_URL = HOST + "api/order/calculate";
    public static final String ORDER_SUBMIT_URL = HOST + "api/order/submitOrder";
    public static final String COMFIRM_URL = HOST + "api/order/confirmReceipt";


    //商城
    public static final String GET_STORES_URL = HOST + "api/mall/themeList";
    public static final String GET_STORES_BY_URL = HOST + "api/mall/themeByCategory";
    public static final String STORES_THEME_BY_URL = HOST + "api/mall/goodsList";

    //购物车
    public static final String SUB_USER_MONEY_URL = HOST + "api/user/shoppingCart/subUserMoney";
    public static final String ADD_SHOPPINGCART_URL = HOST + "api/user/shoppingCart/add";
    public static final String DELETE_SHOPPINGCART_URL = HOST + "api/user/shoppingCart/delete";
    public static final String MODIFY_SHOPPINGCART_URL = HOST + "api/user/shoppingCart/update/";
    public static final String GET_SHOPPINGCART_URL = HOST + "api/user/shoppingCart/list";
    public static final String SETTLEMENT_SHOPPINGCART_URL = HOST + "api/user/shoppingCart/settlement";
    public static final String PAY_URL = HOST + "api/order/payOrder";
    public static final String ORDER_DETAIL_URL = HOST + "api/order/orderInfo";
    public static final String BUY_URL = HOST + "api/user/shoppingCart/directBuy";
    public static final String SUBMIT_URL = HOST + "api/user/shoppingCart/submitOrder";
    public static final String CALCULATE_URL = HOST + "api/user/shoppingCart/calculate";

    //发现想要
    public static final String FIND_WANT_URL = HOST + "api/findWant/want";
    public static final String MY_WANT_URL = HOST + "api/findWant/myWant";
    public static final String SALE_URL = HOST + "api/findWant/sale";

    //搜索
    public static final String AUTO_URL = HOST + "api/goods/search/words";
    public static final String SEARCH_URL = HOST + "api/goods/search";
    public static final String KEYWORD_URL = HOST + "api/goods/search/hotWords";
    public static final String CATEGORY_URL = HOST + "api/goods/search/category";
    public static final String BRAND_URL = HOST + "api/goods/search/brand";
    //侧边栏

    public static final String FIND_AGENT_BY_ID_URL = HOST + "api/agent/findAgentById";
    public static final String IDENTITY_AGENT_URL = HOST + "api/agent/authentication";
    public static final String FIND_AGENT_URL = HOST + "api/agent/findAgent";
    public static final String FIND_CUSTUMER_URL = HOST + "api/agent/findCustomer";
    public static final String FIND_SALES_URL = HOST + "api/agent/findSales";
    public static final String FIND_DETAIL_URL = HOST + "api/agent/findDetailed";
    //
    public static final String UPDATE_URL = HOST + "api/system/version/getVersion";

    public static final String WITHDRAW_URL = HOST + "api/agent/cash";
    public static final String AGENT_SIDEBAR_URL = HOST + "api/user/agentSidebar";
    public static final String BANNER_URL = HOST + "api/home/getBannerByType";


}
