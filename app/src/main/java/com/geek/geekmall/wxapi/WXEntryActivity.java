package com.geek.geekmall.wxapi;


import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.activity.MainActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.bean.WeXinResponse;
import com.geek.geekmall.bean.WeXinUser;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.http.GsonRequest;
import com.geek.geekmall.model.UserData;
import com.geek.geekmall.register.BindAccountActivity;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.views.ToastView;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private IWXAPI api;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.transparent_layout);
        user = new User();
        api = WXAPIFactory.createWXAPI(this, WeiXinConstants.APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        MyLog.debug(WXEntryActivity.class, baseResp.errCode + "---" + baseResp.openId + "===");
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (baseResp instanceof SendAuth.Resp) {
                    String code = ((SendAuth.Resp) baseResp).code;
                    loadingDialog.show();
                    APIControl.getInstance().weixinLogin(this, code, new DataResponseListener<WeXinResponse>() {
                        @Override
                        public void onResponse(WeXinResponse weXinResponse) {
                            user.setAccessToken(weXinResponse.getAccess_token());
                            getWexinUserInfo(weXinResponse.getAccess_token(), weXinResponse.getOpenid());
//                            new ToastView(WXEntryActivity.this,"333333333").show();
                        }
                    }, errorListener(""));
                } else {
                    new ToastView(this, "分享成功").show();
                    finish();
                }

//                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                new ToastView(this, "取消分享").show();
                finish();
                break;
            default:
                break;
        }
//        finish();
    }


    private void getWexinUserInfo(String access_token, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId;
        executeRequest(new GsonRequest<WeXinUser>(url, WeXinUser.class, null, new Response.Listener<WeXinUser>() {
            @Override
            public void onResponse(WeXinUser weXinUser) {
                loadingDialog.dismiss();
                if (weXinUser!= null){
                    try {
                        if (weXinUser.getNickname()!= null){
                            user.setNickname(new String(weXinUser.getNickname().getBytes("ISO-8859-1")));
                        }

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    user.setOpenId(weXinUser.getOpenid());
                    user.setImgUrl(weXinUser.getHeadimgurl());
                    user.setSex(weXinUser.getSex());
//                GeekApplication.setUser(user);
//                SharedPreUtil.getInstance().putUser(user);
                    authLogin(user.getOpenId(), user.getAccessToken(), 4);
                    MyLog.debug(WXEntryActivity.class, weXinUser.toString());
                } else {

                }

            }
        }, errorListener("")));
    }

    private void authLogin(String openId, String accessToken, final int type) {
        loadingDialog.show();
        APIControl.getInstance().authLogin(WXEntryActivity.this, openId, accessToken, type + "",
                new DataResponseListener<UserData>() {
                    @Override
                    public void onResponse(UserData user) {
                        loadingDialog.dismiss();
                        if (user.getStatus() == 200) {
                            SharedPreUtil.getInstance().putUser(user.getData().getUserInfo());
                            GeekApplication.setUser(user.getData().getUserInfo());
//                            Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                            sendBroadcast(new Intent("com.geek.geekmall.action.user_login"));
                            finish();
                        } else if (user.getStatus() == 118) {
                            Intent intent = new Intent(WXEntryActivity.this, BindAccountActivity.class);
                            intent.putExtra("user",WXEntryActivity.this.user);
                            intent.putExtra("type", type);
                            startActivity(intent);
                            finish();
                        }

                    }
                }, errorListener(""));
    }
}