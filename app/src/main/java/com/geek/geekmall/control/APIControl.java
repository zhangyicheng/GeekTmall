package com.geek.geekmall.control;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.Response;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.AgentSidebar;
import com.geek.geekmall.bean.Cart;
import com.geek.geekmall.bean.FavorProduct;
import com.geek.geekmall.bean.FootPrintProduct;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.bean.WeXinResponse;
import com.geek.geekmall.http.ApiParams;
import com.geek.geekmall.http.GsonRequest;
import com.geek.geekmall.http.RequestManager;
import com.geek.geekmall.model.AddressData;
import com.geek.geekmall.model.AddressListData;
import com.geek.geekmall.model.AgentInfoData;
import com.geek.geekmall.model.AgentRankData;
import com.geek.geekmall.model.AgentSideBarData;
import com.geek.geekmall.model.AlipayResponseData;
import com.geek.geekmall.model.BannerTheme;
import com.geek.geekmall.model.BindStatusData;
import com.geek.geekmall.model.BrandData;
import com.geek.geekmall.model.BuyData;
import com.geek.geekmall.model.CartData;
import com.geek.geekmall.model.CartSpecData;
import com.geek.geekmall.model.CategoryPageData;
import com.geek.geekmall.model.CommentPageData;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.FavorsData;
import com.geek.geekmall.model.FootPrintPageData;
import com.geek.geekmall.model.HomeData;
import com.geek.geekmall.model.IncomePageData;
import com.geek.geekmall.model.LogisticsData;
import com.geek.geekmall.model.OrderData;
import com.geek.geekmall.model.PageData;
import com.geek.geekmall.model.PhotoData;
import com.geek.geekmall.model.PhotoPathData;
import com.geek.geekmall.model.ProductInfoData;
import com.geek.geekmall.model.ProductPageData;
import com.geek.geekmall.model.ReFundData;
import com.geek.geekmall.model.StoresData;
import com.geek.geekmall.model.UpdateData;
import com.geek.geekmall.model.UserData;
import com.geek.geekmall.model.WXResultData;
import com.geek.geekmall.model.WordsData;
import com.geek.geekmall.utils.MD5;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.wxapi.WeiXinConstants;

import java.util.List;

/**
 * Created by apple on 6/3/15.
 */
public class APIControl {

    private static APIControl mInstance;

    private APIControl() {

    }

    public static APIControl getInstance() {
        if (mInstance == null) {
            mInstance = new APIControl();
        }
        return mInstance;
    }

    protected void executeRequest(Context context, Request<?> request) {
        RequestManager.addRequest(request, context);
    }

    public void login(final Context context, String phone, String password, final DataResponseListener<UserData> listener, Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<UserData>(Request.Method.POST, URLs.LOGIN_URL,
                UserData.class, null,
                ApiParams.getLoginParams(context, phone, MD5.hexdigest(password)),
                new Response.Listener<UserData>() {
                    @Override
                    public void onResponse(UserData o) {
                        if (o.getStatus() == 200) {
                            context.sendBroadcast(new Intent("com.geek.geekmall.action.user_login"));
                        }
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void authLogin(final Context context, String openId, String accessToken, String type,
                          final DataResponseListener<UserData> listener, Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<UserData>(Request.Method.POST, URLs.LOGIN_AUTH_URL,
                UserData.class, null,
                ApiParams.getLoginAuthParams(context, openId, accessToken, type),
                new Response.Listener<UserData>() {
                    @Override
                    public void onResponse(UserData o) {
                        if (o.getStatus() == 200) {
                            context.sendBroadcast(new Intent("com.geek.geekmall.action.user_login"));
                        }
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void autoLogin(final Context context, String token, final DataResponseListener<UserData> listener,
                          Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<UserData>(Request.Method.POST, URLs.AUTO_LOGIN_URL,
                UserData.class, null,
                ApiParams.getAutoLoginParams(context, token),
                new Response.Listener<UserData>() {
                    @Override
                    public void onResponse(UserData o) {
                        if (o.getStatus() == 200) {
                            context.sendBroadcast(new Intent("com.geek.geekmall.action.user_login"));
                        }
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void forgetPwd(Context context, String phone, String password, String twoPassword,
                          final DataResponseListener<CommonData> listener, Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.FORGET_PASSWORD_URL,
                CommonData.class, null,
                ApiParams.forgetPwdParams(context, phone, MD5.hexdigest(password), MD5.hexdigest(twoPassword)),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void changePwd(Context context, String userId,
                          String password, String formerPassword, String newFormerPassword,
                          final DataResponseListener<CommonData> listener, Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.CHANGE_PASSWORD_URL,
                CommonData.class, null,
                ApiParams.changePwdParams(context, userId, MD5.hexdigest(password),
                        MD5.hexdigest(formerPassword), MD5.hexdigest(newFormerPassword)),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void changePayPwd(Context context, String userId,
                             String phone, String phoneCode, String payPwd, String twoPayPwd,
                             final DataResponseListener<CommonData> listener, Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.PAY_PASSWORD_URL,
                CommonData.class, null,
                ApiParams.changePayPwdParams(context, userId, phone, phoneCode, MD5.hexdigest(payPwd),
                        MD5.hexdigest(twoPayPwd)),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void checkPayPwd(Context context, String userId, String payPwd,
                            final DataResponseListener<CommonData> listener, Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.CHECK_PAY_PASSWORD_URL,
                CommonData.class, null,
                ApiParams.checkPayPwdParams(context, userId, MD5.hexdigest(payPwd)),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void authRegister(Context context, String openId,
                             String phone, String phoneCode,
                             String accessToken, int authType,
                             String imgUrl, String nickname, int sex,
                             final DataResponseListener<UserData> listener, Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<UserData>(Request.Method.POST, URLs.THIRD_LOGIN_URL,
                UserData.class, null,
                ApiParams.thirdAuth(context, openId, phone, phoneCode, accessToken, authType, imgUrl, nickname, sex),
                new Response.Listener<UserData>() {
                    @Override
                    public void onResponse(UserData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
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
    public void getHome(Context context, String userId,
                        String token, int pageSize,
                        final DataResponseListener<HomeData> listener,
                        Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<HomeData>(Request.Method.POST, URLs.HOME_DATA_URL,
                HomeData.class, null,
                ApiParams.getHomeParams(context, userId, token, pageSize),
                new Response.Listener<HomeData>() {
                    @Override
                    public void onResponse(HomeData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取首页推荐列表
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public void getHomeRecommend(Context context, String userId,
                                 String token, int currentPage, int pageSize,
                                 final DataResponseListener<ProductPageData> listener,
                                 Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductPageData>(Request.Method.POST, URLs.HOME_RECOMMEND_URL,
                ProductPageData.class, null,
                ApiParams.getListParams(context, userId, token, currentPage, pageSize),
                new Response.Listener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取首页发现列表
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public void getHomeFind(Context context, String userId,
                            String token, int currentPage, int pageSize,
                            final DataResponseListener<ProductPageData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductPageData>(Request.Method.POST, URLs.HOME_FIND_WANT_URL,
                ProductPageData.class, null,
                ApiParams.getListParams(context, userId, token, currentPage, pageSize),
                new Response.Listener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取首页主题列表
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public void getHomeTheme(Context context, String userId,
                             String token, int currentPage, int pageSize,
                             final DataResponseListener<PageData> listener,
                             Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<PageData>(Request.Method.POST, URLs.HOME_THEME_URL,
                PageData.class, null,
                ApiParams.getListParams(context, userId, token, currentPage, pageSize),
                new Response.Listener<PageData>() {
                    @Override
                    public void onResponse(PageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取绑定状态
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public void getBindStatus(Context context, String userId,
                              String token, final DataResponseListener<BindStatusData> listener,
                              Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<BindStatusData>(Request.Method.POST, URLs.BIND_STATUS_URL,
                BindStatusData.class, null,
                ApiParams.getBindStatusParams(context, userId, token),
                new Response.Listener<BindStatusData>() {
                    @Override
                    public void onResponse(BindStatusData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
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
    public void bind(Context context, String openId,
                     String authType, String phone,
                     String phoneCode, String bindingType,
                     final DataResponseListener<CommonData> listener,
                     Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.BIND_URL,
                CommonData.class, null,
                ApiParams.bindParams(context, openId, authType, phone, phoneCode, bindingType),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
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
    public void modifyUserInfo(final Context context, String userId, String photo,
                               String nickName, int sex,
                               final DataResponseListener<User> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<User>(Request.Method.POST, URLs.MODIFY_USER_INFO_URL,
                User.class, null,
                ApiParams.modifyUserInfoParams(context, userId, photo, nickName, sex),
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User o) {
                        if (o.getStatus() == 200) {
                            context.sendBroadcast(new Intent("com.geek.geekmall.action.user_login"));
                        }
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void uploadPhoto(Context context, List<String> imgSource, String path,
                            final DataResponseListener<PhotoPathData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<PhotoPathData>(Request.Method.POST, URLs.UPLOAD_IMAGE_URL,
                PhotoPathData.class, null,
                ApiParams.uploadPhoto(context, imgSource, path),
                new Response.Listener<PhotoPathData>() {
                    @Override
                    public void onResponse(PhotoPathData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 获取地址列表
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public void getAddressList(Context context, String userId, String token,
                               final DataResponseListener<AddressListData> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<AddressListData>(Request.Method.POST, URLs.GET_ADDRESSLIST_URL,
                AddressListData.class, null,
                ApiParams.getAddressListParams(context, userId, token),
                new Response.Listener<AddressListData>() {
                    @Override
                    public void onResponse(AddressListData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取地址列表
     *
     * @param context
     * @param userId
     * @param token
     * @return
     */
    public void getDefaultAddress(Context context, String userId, String token,
                                  final DataResponseListener<AddressData> listener,
                                  Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<AddressData>(Request.Method.POST, URLs.GET_DEFAULT_ADDRESS_URL,
                AddressData.class, null,
                ApiParams.getAddressListParams(context, userId, token),
                new Response.Listener<AddressData>() {
                    @Override
                    public void onResponse(AddressData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
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
    public void saveAddress(Context context, String userId, String token,
                            String provinceId, String cityId,
                            String zoneId, String address,
                            String contactName, String contactPhone,
                            int isDefault, final DataResponseListener<AddressData> listener,
                            Response.ErrorListener errorListener
    ) {
        executeRequest(context, new GsonRequest<AddressData>(Request.Method.POST, URLs.SAVE_ADDRESS_URL,
                AddressData.class, null,
                ApiParams.saveAddressParams(context, userId, token, provinceId, cityId, zoneId,
                        address, contactName, contactPhone, isDefault),
                new Response.Listener<AddressData>() {
                    @Override
                    public void onResponse(AddressData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 修改地址
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
    public void updateAddress(Context context, String addressId, String userId, String token,
                              String provinceId, String cityId,
                              String zoneId, String address,
                              String contactName, String contactPhone,
                              int isDefault, final DataResponseListener<AddressData> listener,
                              Response.ErrorListener errorListener
    ) {
        executeRequest(context, new GsonRequest<AddressData>(Request.Method.POST, URLs.UPDATE_ADDRESS_URL + addressId,
                AddressData.class, null,
                ApiParams.saveAddressParams(context, userId, token, provinceId, cityId, zoneId,
                        address, contactName, contactPhone, isDefault),
                new Response.Listener<AddressData>() {
                    @Override
                    public void onResponse(AddressData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 删除地址
     *
     * @param context
     * @param addressId
     * @param userId
     * @param token
     * @param listener
     * @param errorListener
     */
    public void deleteAddress(Context context, String addressId, String userId, String token, final DataResponseListener<CommonData> listener,
                              Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.DELETE_ADDRESS_URL + addressId,
                CommonData.class, null,
                ApiParams.deleteAddressParams(context, userId, token),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
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
    public void getFavorList(Context context, String userId, String token,
                             int currentPage, int pageSize,
                             final DataResponseListener<FavorsData> listener,
                             Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<FavorsData>(Request.Method.POST, URLs.GET_FAVORS_URL,
                FavorsData.class, null,
                ApiParams.getFavorListParams(context, userId, token, currentPage, pageSize),
                new Response.Listener<FavorsData>() {
                    @Override
                    public void onResponse(FavorsData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 新增收藏
     *
     * @param context
     * @param userId
     * @param token
     * @param listener
     * @param errorListener
     */
    public void addFavor(Context context, String userId, String token, String id,
                         final DataResponseListener<CommonData> listener,
                         Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.ADD_FAVORS_URL + id,
                CommonData.class, null,
                ApiParams.commonParams(context, userId, token),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 删除收藏
     *
     * @param context
     * @param userId
     * @param token
     * @param products
     * @param listener
     * @param errorListener
     */
    public void deleteFavorList(Context context, String userId, String token,
                                List<FavorProduct> products,
                                final DataResponseListener<CommonData> listener,
                                Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.DELETE_FAVORS_URL,
                CommonData.class, null,
                ApiParams.deleteFavorListParams(context, userId, token, products),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void deleteFavorList(Context context, String userId, String token,
                                String productId,
                                final DataResponseListener<CommonData> listener,
                                Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.DELETE_FAVORS_URL,
                CommonData.class, null,
                ApiParams.deleteFavorListParams(context, userId, token, productId),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取商城数据
     *
     * @param context
     * @param pageSize
     * @return
     */
    public void getStores(Context context, int pageSize, final DataResponseListener<StoresData> listener,
                          Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<StoresData>(Request.Method.POST, URLs.GET_STORES_URL,
                StoresData.class, null,
                ApiParams.getStoresParams(context, pageSize),
                new Response.Listener<StoresData>() {
                    @Override
                    public void onResponse(StoresData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 根据类型获取商城数据
     *
     * @param context
     * @param categoryId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public void getStoresBy(Context context, String categoryId, String token,
                            int currentPage, int pageSize,
                            final DataResponseListener<PageData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<PageData>(Request.Method.POST, URLs.GET_STORES_BY_URL,
                PageData.class, null,
                ApiParams.getStoresByParams(context, categoryId, token, currentPage, pageSize),
                new Response.Listener<PageData>() {
                    @Override
                    public void onResponse(PageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 修改用户信息
     *
     * @param context
     * @param userId
     * @param photo
     * @param nickName
     * @param sex
     * @param listener
     * @param errorListener
     */
    public void updateUserInfo(Context context, String userId, String photo,
                               String nickName, int sex,
                               final DataResponseListener<CommonData> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.MODIFY_USER_INFO_URL,
                CommonData.class, null,
                ApiParams.updateUserInfoParams(context, userId, photo, nickName, sex),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取发现想要列表
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public void getFindWantList(Context context, String userId,
                                String token, int currentPage, int pageSize,
                                final DataResponseListener<ProductPageData> listener,
                                Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductPageData>(Request.Method.POST, URLs.FIND_WANT_URL,
                ProductPageData.class, null,
                ApiParams.getListParams(context, userId, token, currentPage, pageSize),
                new Response.Listener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取发现想要开售列表
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public void getFindWantSaleList(Context context, String userId,
                                    String token, int currentPage, int pageSize,
                                    final DataResponseListener<ProductPageData> listener,
                                    Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductPageData>(Request.Method.POST, URLs.SALE_URL,
                ProductPageData.class, null,
                ApiParams.getListParams(context, userId, token, currentPage, pageSize),
                new Response.Listener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取发现想要开售列表
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @return
     */
    public void getMyFindWantList(Context context, String userId,
                                  String token, int currentPage, int pageSize,
                                  final DataResponseListener<ProductPageData> listener,
                                  Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductPageData>(Request.Method.POST, URLs.MY_WANT_URL,
                ProductPageData.class, null,
                ApiParams.getListParams(context, userId, token, currentPage, pageSize),
                new Response.Listener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }


    /**
     * 关键字获取
     *
     * @param context
     * @param pageSize
     * @return
     */
    public void searchKeywords(Context context, int pageSize,
                               final DataResponseListener<WordsData> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<WordsData>(Request.Method.POST, URLs.KEYWORD_URL,
                WordsData.class, null,
                ApiParams.searchKeywordsParams(context, pageSize),
                new Response.Listener<WordsData>() {
                    @Override
                    public void onResponse(WordsData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 自动补全
     *
     * @param context
     * @param words
     * @return
     */
    public void autoKeywords(Context context, String words, final DataResponseListener<WordsData> listener,
                             Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<WordsData>(Request.Method.POST, URLs.AUTO_URL,
                WordsData.class, null,
                ApiParams.autoKeywordsParams(context, words),
                new Response.Listener<WordsData>() {
                    @Override
                    public void onResponse(WordsData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 搜索列表
     *
     * @param context
     * @param pageSize
     * @param currentPage
     * @param categoryId
     * @param brandId
     * @param keyWords
     * @param userId
     * @return
     */
    public void searchList(Context context, int pageSize, int currentPage,
                           String categoryId, String brandId,
                           String keyWords, String userId,
                           final DataResponseListener<ProductPageData> listener,
                           Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductPageData>(Request.Method.POST, URLs.SEARCH_URL,
                ProductPageData.class, null,
                ApiParams.searchListParams(context, pageSize, currentPage, categoryId, brandId,
                        keyWords, userId),
                new Response.Listener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void searchList(Context context,
                           final DataResponseListener<ProductPageData> listener,
                           Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductPageData>(Request.Method.POST, URLs.CATEGORY_URL,
                ProductPageData.class, null,
                null,
                new Response.Listener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 意见反馈
     *
     * @param context
     * @param userId
     * @param token
     * @param listener
     * @param errorListener
     */
    public void feedback(Context context, String userId, String token, String content,
                         final DataResponseListener<CommonData> listener,
                         Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.FEEDBACK_URL,
                CommonData.class, null,
                ApiParams.feedbackParams(context, userId, token, content),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 足迹
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @param listener
     * @param errorListener
     */
    public void footPrint(Context context, String userId, String token,
                          int currentPage, int pageSize,
                          final DataResponseListener<FootPrintPageData> listener,
                          Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<FootPrintPageData>(Request.Method.POST, URLs.FOOTPRINT_URL,
                FootPrintPageData.class, null,
                ApiParams.commonPageParams(context, userId, token, currentPage, pageSize),
                new Response.Listener<FootPrintPageData>() {
                    @Override
                    public void onResponse(FootPrintPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 删除足迹
     *
     * @param context
     * @param productList
     * @param token
     * @param listener
     * @param errorListener
     */
    public void deleteFootPrint(Context context, List<FootPrintProduct> productList, String userId, String token,
                                final DataResponseListener<CommonData> listener,
                                Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.DELETE_FOOTPRINT_URL,
                CommonData.class, null,
                ApiParams.deleteFootPrintParams(context, productList, userId, token),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取产品详情
     *
     * @param context
     * @param userId
     * @param token
     * @param id
     * @param listener
     * @param errorListener
     */
    public void getProductInfo(Context context, String userId, String token, String id,
                               final DataResponseListener<ProductInfoData> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductInfoData>(Request.Method.POST, URLs.PRODUCT_INFO_URL,
                ProductInfoData.class, null,
                ApiParams.getProductInfoParams(context, userId, token, id),
                new Response.Listener<ProductInfoData>() {
                    @Override
                    public void onResponse(ProductInfoData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 添加购物车
     *
     * @param context
     * @param userId
     * @param token
     * @param goodsId
     * @param goodsNumber
     * @param goodsSpecId
     * @param listener
     * @param errorListener
     */
    public void addCart(Context context, String userId, String token,
                        String goodsId, String goodsNumber, String goodsSpecId,
                        final DataResponseListener<CommonData> listener,
                        Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.ADD_SHOPPINGCART_URL,
                CommonData.class, null,
                ApiParams.addCartParams(context, userId, token, goodsId, goodsNumber, goodsSpecId),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void calculateCart(Context context, String submitOrderInfo, String token,
                              final DataResponseListener<BuyData> listener,
                              Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<BuyData>(Request.Method.POST, URLs.CALCULATE_URL,
                BuyData.class, null,
                ApiParams.calculateCartParams(context, submitOrderInfo, token),
                new Response.Listener<BuyData>() {
                    @Override
                    public void onResponse(BuyData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void calculateOrder(Context context, String submitOrderInfo, String token,
                               final DataResponseListener<BuyData> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<BuyData>(Request.Method.POST, URLs.CALCULATE_ORDER_URL,
                BuyData.class, null,
                ApiParams.calculateCartParams(context, submitOrderInfo, token),
                new Response.Listener<BuyData>() {
                    @Override
                    public void onResponse(BuyData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 删除购物车
     *
     * @param context
     * @param userId
     * @param token
     * @param productList
     * @param listener
     * @param errorListener
     */
    public void deleteCart(Context context, String userId, String token,
                           List<Cart> productList,
                           final DataResponseListener<CommonData> listener,
                           Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.DELETE_SHOPPINGCART_URL,
                CommonData.class, null,
                ApiParams.deleteCartParams(context, userId, token, productList),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 结算接口
     *
     * @param context
     * @param userId
     * @param token
     * @param cartList
     * @param listener
     * @param errorListener
     */
    public void settlementCart(Context context, String userId, String token,
                               List<Cart> cartList,
                               final DataResponseListener<BuyData> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<BuyData>(Request.Method.POST, URLs.SETTLEMENT_SHOPPINGCART_URL,
                BuyData.class, null,
                ApiParams.settlementCartParams(context, userId, token, cartList),
                new Response.Listener<BuyData>() {
                    @Override
                    public void onResponse(BuyData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 修改购物车
     *
     * @param context
     * @param userId
     * @param token
     * @param goodsNumber
     * @param listener
     * @param errorListener
     */
    public void modifyCart(Context context, String userId, String token,
                           String goodsNumber, String cardId,
                           final DataResponseListener<CommonData> listener,
                           Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.MODIFY_SHOPPINGCART_URL + cardId,
                CommonData.class, null,
                ApiParams.modifyCartParams(context, userId, token, goodsNumber),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取购物车列表
     *
     * @param context
     * @param userId
     * @param token
     * @param listener
     * @param errorListener
     */
    public void getCartList(Context context, String userId, String token,
                            final DataResponseListener<CartData> listener,
                            Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<CartData>(Request.Method.POST, URLs.GET_SHOPPINGCART_URL,
                CartData.class, null,
                ApiParams.commonParams(context, userId, token),
                new Response.Listener<CartData>() {
                    @Override
                    public void onResponse(CartData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取订单列表
     *
     * @param context
     * @param userId
     * @param token
     * @param currentPage
     * @param pageSize
     * @param listener
     * @param errorListener
     */
    public void getOrdeList(Context context, String userId, String token, int currentPage, int pageSize,
                            int type, final DataResponseListener<OrderData> listener,
                            Response.ErrorListener errorListener) {

        executeRequest(context, new GsonRequest<OrderData>(Request.Method.POST, URLs.GET_ORDERLIST_URL,
                OrderData.class, null,
                ApiParams.orderParams(context, userId, token, currentPage, pageSize, type),
                new Response.Listener<OrderData>() {
                    @Override
                    public void onResponse(OrderData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 增加评论
     *
     * @param context
     * @param listener
     * @param errorListener
     */
    public void addComment(Context context, String data,
                           final DataResponseListener<CommonData> listener,
                           Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.ADD_COMMENT_URL,
                CommonData.class, null,
                ApiParams.addCommentParams(context, data),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取主题产品
     *
     * @param context
     * @param themeId
     * @param order
     * @param currentPage
     * @param pageSize
     * @param listener
     * @param errorListener
     */
    public void getThemeProduct(Context context, String themeId, String order,
                                int currentPage, int pageSize,
                                final DataResponseListener<ProductPageData> listener,
                                Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductPageData>(Request.Method.POST, URLs.STORES_THEME_BY_URL,
                ProductPageData.class, null,
                ApiParams.getThemeProductParams(context, themeId, order, currentPage, pageSize),
                new Response.Listener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取分类列表
     *
     * @param context
     * @param listener
     * @param errorListener
     */
    public void getCategory(Context context,
                            final DataResponseListener<CategoryPageData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CategoryPageData>(Request.Method.POST, URLs.CATEGORY_URL,
                CategoryPageData.class, null,
                null,
                new Response.Listener<CategoryPageData>() {
                    @Override
                    public void onResponse(CategoryPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取品牌列表
     *
     * @param context
     * @param listener
     * @param errorListener
     */
    public void getBrand(Context context,
                         final DataResponseListener<BrandData> listener,
                         Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<BrandData>(Request.Method.POST, URLs.BRAND_URL,
                BrandData.class, null,
                null,
                new Response.Listener<BrandData>() {
                    @Override
                    public void onResponse(BrandData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 销售总额排名
     *
     * @param context
     * @param listener
     * @param errorListener
     */
    public void rankSales(Context context, String userId, String token, String agentId, int currentPage, int pageSize,
                          final DataResponseListener<AgentRankData> listener,
                          Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<AgentRankData>(Request.Method.POST, URLs.FIND_SALES_URL,
                AgentRankData.class, null,
                ApiParams.customerRankParams(context, userId, token, agentId, currentPage, pageSize),
                new Response.Listener<AgentRankData>() {
                    @Override
                    public void onResponse(AgentRankData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 客户人数排名
     *
     * @param context
     * @param listener
     * @param errorListener
     */
    public void rankCustomer(Context context, String userId, String token, String agentId, int currentPage, int pageSize,
                             final DataResponseListener<AgentRankData> listener,
                             Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<AgentRankData>(Request.Method.POST, URLs.FIND_CUSTUMER_URL,
                AgentRankData.class, null,
                ApiParams.customerRankParams(context, userId, token, agentId, currentPage, pageSize),
                new Response.Listener<AgentRankData>() {
                    @Override
                    public void onResponse(AgentRankData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
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
     * @param listener
     * @param errorListener
     */
    public void totalIncomeCustomer(Context context, String userId, String token, String date, String agentId,
                                    int currentPage, int pageSize,
                                    final DataResponseListener<IncomePageData> listener,
                                    Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<IncomePageData>(Request.Method.POST, URLs.FIND_AGENT_URL,
                IncomePageData.class, null,
                ApiParams.getInComeParams(context, userId, token, date, agentId, currentPage, pageSize),
                new Response.Listener<IncomePageData>() {
                    @Override
                    public void onResponse(IncomePageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void productPhoto(Context context, String userId, String token, String goodsId,
                             final DataResponseListener<PhotoData> listener,
                             Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<PhotoData>(Request.Method.POST, URLs.PRODUCT_INFO_PHOTO_URL,
                PhotoData.class, null,
                ApiParams.productPhotoParams(context, userId, token, goodsId),
                new Response.Listener<PhotoData>() {
                    @Override
                    public void onResponse(PhotoData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void productComment(Context context, String userId, String token, String goodsId,
                               int currentPage, int pageSize,
                               final DataResponseListener<CommentPageData> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommentPageData>(Request.Method.POST, URLs.PRODUCT_INFO_COMMENT_URL,
                CommentPageData.class, null,
                ApiParams.productCommentParams(context, userId, token, goodsId, currentPage, pageSize),
                new Response.Listener<CommentPageData>() {
                    @Override
                    public void onResponse(CommentPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void payOrder(Context context, String userId, String payOrderId, String type,
                         final DataResponseListener<WXResultData> listener,
                         Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<WXResultData>(Request.Method.POST, URLs.PAY_URL,
                WXResultData.class, null,
                ApiParams.productPayParams(context, userId, payOrderId, type),
                new Response.Listener<WXResultData>() {
                    @Override
                    public void onResponse(WXResultData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void deleteOrder(Context context, String userId, String token, String orderId,
                            final DataResponseListener<CommonData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.DELETE_ORDER_URL,
                CommonData.class, null,
                ApiParams.deleteOrderParams(context, userId, token, orderId),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void cancelOrder(Context context, String userId, String token, String orderId,
                            final DataResponseListener<CommonData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.CANCEL_ORDER_URL,
                CommonData.class, null,
                ApiParams.cancelOrderParams(context, userId, token, orderId),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void getProductPrice(Context context, String goodsId, String userId, String token,
                                String format1, String format2, String format3,
                                final DataResponseListener<CartSpecData> listener,
                                Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CartSpecData>(Request.Method.POST, URLs.PRODUCT_PRICE_URL,
                CartSpecData.class, null,
                ApiParams.productPriceParams(context, goodsId, userId, token,
                        format1, format2, format3),
                new Response.Listener<CartSpecData>() {
                    @Override
                    public void onResponse(CartSpecData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    public void buyProduct(Context context, String goodsId, String userId, String token,
                           String goodsNumber, String goodsSpecId,
                           final DataResponseListener<BuyData> listener,
                           Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<BuyData>(Request.Method.POST, URLs.BUY_URL,
                BuyData.class, null,
                ApiParams.buyParams(context, goodsId, userId, token,
                        goodsNumber, goodsSpecId),
                new Response.Listener<BuyData>() {
                    @Override
                    public void onResponse(BuyData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取收入明细
     *
     * @param context
     * @param token
     * @param date
     * @param currentPage
     * @param listener
     * @param errorListener
     */
    public void agentDetail(Context context, String token, String date, int currentPage,
                            final DataResponseListener<CommonData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.FIND_DETAIL_URL,
                CommonData.class, null,
                ApiParams.agentDetailParams(context, token,
                        currentPage, date),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    public void submitOrder(Context context, String submitOrderInfo, String userId, String token,
                            final DataResponseListener<WXResultData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<WXResultData>(Request.Method.POST, URLs.SUBMIT_URL,
                WXResultData.class, null,
                ApiParams.submitOrderParams(context, submitOrderInfo, userId, token),
                new Response.Listener<WXResultData>() {
                    @Override
                    public void onResponse(WXResultData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    public void orderSubmitOrder(Context context, String submitOrderInfo, String userId, String token,
                                 final DataResponseListener<WXResultData> listener,
                                 Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<WXResultData>(Request.Method.POST, URLs.ORDER_SUBMIT_URL,
                WXResultData.class, null,
                ApiParams.submitOrderParams(context, submitOrderInfo, userId, token),
                new Response.Listener<WXResultData>() {
                    @Override
                    public void onResponse(WXResultData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    public void updateVersion(Context context, String type,
                              final DataResponseListener<UpdateData> listener,
                              Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<UpdateData>(Request.Method.POST, URLs.UPDATE_URL,
                UpdateData.class, null,
                ApiParams.updateParams(context, type),
                new Response.Listener<UpdateData>() {
                    @Override
                    public void onResponse(UpdateData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    public void getHomeMessage(Context context, String userId, String token,
                               final DataResponseListener<UserData> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<UserData>(Request.Method.POST, URLs.HOME_MESSAGE_URL,
                UserData.class, null,
                ApiParams.commonParams(context, userId, token),
                new Response.Listener<UserData>() {
                    @Override
                    public void onResponse(UserData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 猜你喜欢
     * @param context
     * @param userId
     * @param pageSize
     * @param listener
     * @param errorListener
     */
    public void guessLike(Context context, String userId, String pageSize,
                          final DataResponseListener<ProductPageData> listener,
                          Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ProductPageData>(Request.Method.POST, URLs.PRODUCT_GUESS_URL,
                ProductPageData.class, null,
                ApiParams.guessLikeParams(context, userId, pageSize),
                new Response.Listener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 订单详情
     * @param context
     * @param orderId
     * @param listener
     * @param errorListener
     */
    public void orderDetail(Context context, String orderId, final DataResponseListener<LogisticsData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<LogisticsData>(Request.Method.POST, URLs.ORDER_DETAIL_URL,
                LogisticsData.class, null,
                ApiParams.orderDetailParams(context, orderId),
                new Response.Listener<LogisticsData>() {
                    @Override
                    public void onResponse(LogisticsData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    public void weixinLogin(Context context, String code, final DataResponseListener<WeXinResponse> listener,
                            Response.ErrorListener errorListener) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + WeiXinConstants.APP_ID + "&secret=" + WeiXinConstants.AppSecret + "&code=" + code + "&grant_type=authorization_code";
        executeRequest(context, new GsonRequest<WeXinResponse>(Request.Method.GET, url, WeXinResponse.class, null, null, new Response.Listener<WeXinResponse>() {
            @Override
            public void onResponse(WeXinResponse weXinResponse) {
//                            MyLog.debug(WXEntryActivity.class, weXinResponse.toString());
                listener.onResponse(weXinResponse);
            }
        }, errorListener));
    }

    public void recharge(Context context, String userId, int rechargeType, int payType, int money, final DataResponseListener<AlipayResponseData> listener,
                         Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<AlipayResponseData>(Request.Method.POST, URLs.RECHARGE_URL,
                AlipayResponseData.class, null,
                ApiParams.rechargeParams(context, userId, rechargeType, payType, money),
                new Response.Listener<AlipayResponseData>() {
                    @Override
                    public void onResponse(AlipayResponseData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    public void wexinrecharge(Context context, String userId, int rechargeType, int payType, int money, final DataResponseListener<WXResultData> listener,
                              Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<WXResultData>(Request.Method.POST, URLs.RECHARGE_URL,
                WXResultData.class, null,
                ApiParams.rechargeParams(context, userId, rechargeType, payType, money),
                new Response.Listener<WXResultData>() {
                    @Override
                    public void onResponse(WXResultData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 申请退货
     * @param context
     * @param userId
     * @param orderId
     * @param totalPrice
     * @param refundType
     * @param reason
     * @param applyRemark
     * @param reasonImg
     * @param listener
     * @param errorListener
     */
    public void apply(Context context, String userId, String orderId, String totalPrice,
                      int refundType, int reason, String applyRemark, String reasonImg,
                      final DataResponseListener<CommonData> listener,
                      Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.APPLY_URL,
                CommonData.class, null,
                ApiParams.applyParams(context, userId, orderId, totalPrice, refundType, reason, applyRemark, reasonImg),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 添加想要
     * @param context
     * @param userId
     * @param token
     * @param productId
     * @param listener
     * @param errorListener
     */
    public void saveWant(Context context, String userId, String token, String productId,
                         final DataResponseListener<CommonData> listener,
                         Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.ADD_FIND_WANT_URL,
                CommonData.class, null,
                ApiParams.saveWantParams(context, userId, token, productId),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 经销商认证
     * @param context
     * @param agentId
     * @param identity
     * @param name
     * @param alipayName
     * @param listener
     * @param errorListener
     */
    public void identity(Context context, String agentId, String identity, String name, String alipayName,
                         final DataResponseListener<CommonData> listener,
                         Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.IDENTITY_AGENT_URL,
                CommonData.class, null,
                ApiParams.identityParams(context, agentId, identity, name, alipayName),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 经销商数据
     *
     * @param context
     * @param agentId
     * @param listener
     * @param errorListener
     */
    public void findAgentById(Context context, String agentId,
                              final DataResponseListener<AgentInfoData> listener,
                              Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<AgentInfoData>(Request.Method.POST, URLs.FIND_AGENT_BY_ID_URL,
                AgentInfoData.class, null,
                ApiParams.findAgentByIdParams(context, agentId),
                new Response.Listener<AgentInfoData>() {
                    @Override
                    public void onResponse(AgentInfoData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }
    /**
     * 退货列表
     * @param context
     * @param id
     * @param listener
     * @param errorListener
     */
    public void customerServices(Context context, String goodsOrderId,
                                 final DataResponseListener<ReFundData> listener,
                                 Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<ReFundData>(Request.Method.POST, URLs.APPLY_SERVICES_URL,
                ReFundData.class, null,
                ApiParams.customerServicesParams(context, goodsOrderId),
                new Response.Listener<ReFundData>() {
                    @Override
                    public void onResponse(ReFundData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 取消退货
     * @param context
     * @param id
     * @param listener
     * @param errorListener
     */
    public void cancelService(Context context, String id,
                              final DataResponseListener<CommonData> listener,
                              Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.CANCEL_SERVICES_URL,
                CommonData.class, null,
                ApiParams.cancelServiceParams(context, id),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 添加物流单号
     * @param context
     * @param id
     * @param sendNo
     * @param logisticsComp
     * @param logisticsRemark
     * @param listener
     * @param errorListener
     */
    public void addLogistics(Context context, String id, String sendNo, String logisticsComp, String logisticsRemark,
                             final DataResponseListener<CommonData> listener,
                             Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.ADD_LOGISTICS_URL,
                CommonData.class, null,
                ApiParams.addLogisticsParams(context, id, sendNo, logisticsComp, logisticsRemark),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 修改退货申请
     * @param context
     * @param id
     * @param totalPrice
     * @param refundType
     * @param reason
     * @param applyRemark
     * @param reasonImg
     * @param listener
     * @param errorListener
     */
    public void updateApply(Context context, String id, String totalPrice,
                            int refundType, int reason, String applyRemark, String reasonImg,
                            final DataResponseListener<CommonData> listener,
                            Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.UPDATE_SERVICES_URL,
                CommonData.class, null,
                ApiParams.updateApplyParams(context, id, totalPrice, refundType, reason, applyRemark, reasonImg),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));

    }

    /**
     * 确认收货
     * @param context
     * @param orderId
     * @param listener
     * @param errorListener
     */
    public void confirmReceive(Context context, String orderId, final DataResponseListener<CommonData> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.COMFIRM_URL,
                CommonData.class, null,
                ApiParams.confirmReceiveParams(context, orderId),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 提现
     * @param context
     * @param userId
     * @param cashMoney
     * @param listener
     * @param errorListener
     */
    public void withDraw(Context context, String userId, String cashMoney, final DataResponseListener<CommonData> listener,
                         Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.WITHDRAW_URL,
                CommonData.class, null,
                ApiParams.withDrawParams(context, userId, cashMoney),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 获取侧边栏数据
     * @param context
     * @param userId
     * @param listener
     * @param errorListener
     */
    public void agentSidebar(Context context, String userId, final DataResponseListener<AgentSideBarData> listener,
                             Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<AgentSideBarData>(Request.Method.POST, URLs.AGENT_SIDEBAR_URL,
                AgentSideBarData.class, null,
                ApiParams.agentSidebarParams(context, userId),
                new Response.Listener<AgentSideBarData>() {
                    @Override
                    public void onResponse(AgentSideBarData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 保存侧边栏数据
     * @param context
     * @param sidebar
     */
    public void saveSideBar(Context context, AgentSidebar sidebar) {
        SharedPreUtil.getInstance().put(context, "customerNumber", sidebar.getCustomerNumber());
        SharedPreUtil.getInstance().put(context, "totalPrice", sidebar.getTotalPrice());
        SharedPreUtil.getInstance().put(context, "allPrice", sidebar.getAllPrice());
        SharedPreUtil.getInstance().put(context, "allTruePrice", sidebar.getAllTruePrice());

    }

    /**
     * 取出侧边栏数据
     * @param context
     * @return
     */
    public AgentSidebar getSideBar(Context context) {
        AgentSidebar sidebar = new AgentSidebar();
        sidebar.setCustomerNumber((Integer) SharedPreUtil.getInstance().get(context, "customerNumber", 0));
        sidebar.setAllPrice((Float) SharedPreUtil.getInstance().get(context, "allPrice", 0f));
        sidebar.setAllTruePrice((Float) SharedPreUtil.getInstance().get(context, "allTruePrice", 0f));
        sidebar.setTotalPrice((Float) SharedPreUtil.getInstance().get(context, "totalPrice", 0f));

        return sidebar;
    }

    /**
     * 根据类型 获取banner
     * @param context
     * @param linkTypeId
     * @param type
     * @param listener
     * @param errorListener
     */
    public void getBannerByType(Context context, String linkTypeId,String type, final DataResponseListener<BannerTheme> listener,
                               Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<BannerTheme>(Request.Method.POST, URLs.BANNER_URL,
                BannerTheme.class, null,
                ApiParams.getBannerByTypeParams(context, linkTypeId, type),
                new Response.Listener<BannerTheme>() {
                    @Override
                    public void onResponse(BannerTheme o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     * 删除想要
     * @param context
     * @param goodsId
     * @param userId
     * @param listener
     * @param errorListener
     */
    public void getDeleteWant(Context context, String goodsId,String userId, final DataResponseListener<CommonData> listener,
                                 Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.DELETE_FIND_WANT_URL,
                CommonData.class, null,
                ApiParams.getDeleteWantParams(context, goodsId, userId),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }

    /**
     *  支付宝回调
     * @param context
     * @param submitOrderId
     * @param listener
     * @param errorListener
     */
    public void subUserMoney(Context context, String submitOrderId, final DataResponseListener<CommonData> listener,
                              Response.ErrorListener errorListener) {
        executeRequest(context, new GsonRequest<CommonData>(Request.Method.POST, URLs.SUB_USER_MONEY_URL,
                CommonData.class, null,
                ApiParams.subUserMoneyParams(context, submitOrderId),
                new Response.Listener<CommonData>() {
                    @Override
                    public void onResponse(CommonData o) {
                        listener.onResponse(o);
                    }
                }, errorListener));
    }
}
