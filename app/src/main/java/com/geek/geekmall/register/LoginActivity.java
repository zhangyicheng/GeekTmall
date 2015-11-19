package com.geek.geekmall.register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.activity.MainActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.http.ApiParams;
import com.geek.geekmall.http.GsonRequest;
import com.geek.geekmall.model.AgentSideBarData;
import com.geek.geekmall.model.UserData;
import com.geek.geekmall.utils.MD5;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.QQUtil;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.views.ToastView;
import com.geek.geekmall.wxapi.WeiXinConstants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.AccessTokenKeeper;
import com.sina.weibo.sdk.openapi.SinaConstants;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.widget.SinaLoginButton;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by apple on 4/22/15.
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    private Button mLoginBtn;
    private TextView mQQLogin;
    private TextView mWeiXinLogin;

    private EditText mPhone;
    private EditText mPassword;
    private TextView mBackView;
    private SinaLoginButton mSinaLogin;
    private TextView mRegisterView;
    private TextView mForgetView;
    public static Tencent mTencent;
    //    private RegisterBroadCast mBroadCast;
    private UserInfo mInfo;
    private AuthInfo mAuthInfo;
    public static final String mQQAppid = "1104566597";
    private static boolean isServerSideLogin = false;
    /**
     * 登陆认证对应的listener
     */
    private AuthListener mLoginListener = new AuthListener();
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private User user;
    private UsersAPI mUsersAPI;
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        user = new User();
        initLogin();
        initView();
        mBroadCast = new RegisterBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.geek.geekmall.action.user_login");
        registerReceiver(mBroadCast, filter);
        LogUtil.enableLog();
//        registerBroadCast();
    }

    private void initLogin() {
        //qq
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mQQAppid, this.getApplicationContext());
        }
        //wexin
        api = WXAPIFactory.createWXAPI(this, WeiXinConstants.APP_ID, false);
        api.registerApp(WeiXinConstants.APP_ID);
        // 创建授权认证信息 sina
        mAuthInfo = new AuthInfo(this, SinaConstants.APP_KEY, SinaConstants.REDIRECT_URL, SinaConstants.SCOPE);
    }

    private void qqClick() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
        }
//        else {
//            mTencent.logout(this);
//            updateUserInfo();
//        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
//            updateLoginButton();
        }
    };

    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
                user.setAccessToken(token);
                user.setOpenId(openId);

            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "-->onActivityResult " + requestCode + " resultCode=" + resultCode);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.RESULT_LOGIN) {
                Tencent.handleResultData(data, loginListener);
                Log.d(TAG, "-->onActivityResult handle logindata");
            }
        } else if (requestCode == Constants.REQUEST_APPBAR) { //app内应用吧登录
            if (resultCode == Constants.RESULT_LOGIN) {
                updateUserInfo();
//                updateLoginButton();
            }
        }
        (mSinaLogin).onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        mBackView = (TextView) findViewById(R.id.back);
        mPhone = (EditText) findViewById(R.id.phone);
        mPassword = (EditText) findViewById(R.id.password);

        mLoginBtn = (Button) findViewById(R.id.login);
        mQQLogin = (TextView) findViewById(R.id.qq_login);
        mWeiXinLogin = (TextView) findViewById(R.id.weixin_login);
        mSinaLogin = (SinaLoginButton) findViewById(R.id.sina_login);
        mRegisterView = (TextView) findViewById(R.id.register);
        mForgetView = (TextView) findViewById(R.id.forget);
        mLoginBtn.setOnClickListener(this);
        mQQLogin.setOnClickListener(this);
        mWeiXinLogin.setOnClickListener(this);
        mForgetView.setOnClickListener(this);
        mRegisterView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mSinaLogin.setWeiboAuthInfo(mAuthInfo, mLoginListener);

    }

    private void login() {
        if (mPhone.getText().toString().length() == 0) {
            new ToastView(this, "请输入手机号").show();
            return;
        }
        if (mPassword.getText().toString().length() == 0) {
            new ToastView(this, "请输入密码").show();
            return;
        }
        loadingDialog.show();
        executeRequest(new GsonRequest<UserData>(Request.Method.POST, URLs.LOGIN_URL,
                UserData.class, null, ApiParams.getLoginParams(this, mPhone.getText().toString(),
                MD5.hexdigest(mPassword.getText().toString())), login, errorListener("")));
//        executeRequest(new GsonRequest<User>(Request.Method.POST, URLs.LOGIN_URL,
//                User.class, null, ApiParams.getLoginParams(this, mPhone.getText().toString(),mPassword.getText().toString()), login, errorListener()));
    }

    Response.Listener login = new Response.Listener<UserData>() {
        @Override
        public void onResponse(UserData o) {
            if (o.getStatus() == 200) {
                GeekApplication.setUser(o.getData().getUserInfo());
                SharedPreUtil.getInstance().putUser(o.getData().getUserInfo());
                GeekApplication.setAgentInfo(o.getData().getAgentInfo());
                GeekApplication.setOrderInfo(o.getData().getOrderInfo());

                sendBroadcast(new Intent("com.geek.geekmall.action.user_login"));
                setResult(RESULT_OK);
                finish();
            } else {
                new ToastView(LoginActivity.this, o.getErrorMsg()).show();
            }

            loadingDialog.dismiss();
        }
    };

    @Override
    protected Response.ErrorListener errorListener(String url) {
//        loadingDialog.dismiss();
        return super.errorListener("");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.forget:
                intent.putExtra("type", 2);
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login:
                login();
                break;
            case R.id.register:
                intent.putExtra("type", 1);
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.qq_login:

                qqClick();
                break;
            case R.id.weixin_login:
                if (!api.isWXAppInstalled()) {
                    new ToastView(this, "未安装微信").show();
                    return;
                }
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "none";
                api.sendReq(req);
                break;

            default:
                break;
        }
    }

//    private void registerBroadCast() {
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.geek.geekmall.action.register");
//
//        mBroadCast = new RegisterBroadCast();
//        registerReceiver(mBroadCast, filter);
//    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    private RegisterBroadCast mBroadCast;

    private class RegisterBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.geek.geekmall.action.user_login".equals(intent.getAction())) {
                LoginActivity.this.setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadCast);
        super.onDestroy();
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                QQUtil.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                return;
            }
            // 有奖分享处理
//            handlePrizeShare();
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            QQUtil.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
            QQUtil.dismissDialog();
        }

        @Override
        public void onCancel() {
            QQUtil.toastMessage(LoginActivity.this, "onCancel: ");
            QQUtil.dismissDialog();
            if (isServerSideLogin) {
                isServerSideLogin = false;
            }
        }
    }


    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            loadingDialog.show();
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {
                    loadingDialog.dismiss();
                    MyLog.debug(LoginActivity.class, e.errorDetail + "iiii\n" + e.errorMessage);
                    new ToastView(LoginActivity.this, e.errorMessage).show();
                }

                @Override
                public void onComplete(final Object response) {
                    loadingDialog.dismiss();
                    JSONObject json = (JSONObject) response;
                    Log.d("zhong", json.toString());
                    if (json.has("figureurl")) {
                        try {
                            user.setImgUrl(json.getString("figureurl_qq_2"));
                            user.setNickname(json.getString("nickname"));
                            user.setSex(json.getString("gender").contains("男") ? 1 : 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

//                    GeekApplication.setUser(user);
//                    SharedPreUtil.getInstance().putUser(user);
                    authLogin(user.getOpenId(), user.getAccessToken(), 3);
                }

                @Override
                public void onCancel() {

                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        } else {
//            mUserInfo.setText("");
//            mUserInfo.setVisibility(android.view.View.GONE);
//            mUserLogo.setVisibility(android.view.View.GONE);
        }
    }


    /**
     * 登入按钮的监听器，接收授权结果。
     */
    private class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken != null && mAccessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                        new java.util.Date(mAccessToken.getExpiresTime()));
//                String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
//                mTokenView.setText(String.format(format, accessToken.getToken(), date));
                mUsersAPI = new UsersAPI(LoginActivity.this, SinaConstants.APP_KEY, mAccessToken);
                mUsersAPI.show(Long.parseLong(mAccessToken.getUid()), mLister);
                AccessTokenKeeper.writeAccessToken(getApplicationContext(), mAccessToken);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            e.printStackTrace();
        }

        @Override
        public void onCancel() {
        }
    }

    private RequestListener mLister = new RequestListener() {
        @Override
        public void onComplete(String s) {
            com.sina.weibo.sdk.openapi.models.User user = com.sina.weibo.sdk.openapi.models.User.parse(s);
            if (user != null){
                LoginActivity.this.user.setNickname(user.screen_name);
                LoginActivity.this.user.setSex(user.gender.equals("m") ? 1 : 0);
                LoginActivity.this.user.setOpenId(mAccessToken.getUid());
                LoginActivity.this.user.setAccessToken(mAccessToken.getToken());
                LoginActivity.this.user.setImgUrl(user.avatar_large);
//            GeekApplication.setUser(localUser);
//            SharedPreUtil.getInstance().putUser(localUser);
                MyLog.debug(LoginActivity.class, "获取新浪信息成功" + user.toString());
                authLogin(LoginActivity.this.user.getOpenId(), LoginActivity.this.user.getAccessToken(), 5);

            } else {
                new ToastView(LoginActivity.this,"获取新浪信息失败").show();
            }

        }

        @Override
        public void onWeiboException(WeiboException e) {
            ErrorInfo info = ErrorInfo.parse(e.getMessage());
            MyLog.debug(LoginActivity.class, info.toString());
        }
    };

    private void authLogin(String openId, String accessToken, final int type) {
        loadingDialog.show();
        APIControl.getInstance().authLogin(LoginActivity.this, openId, accessToken, type + "",
                new DataResponseListener<UserData>() {
                    @Override
                    public void onResponse(UserData user) {
                        loadingDialog.dismiss();
                        if (user.getStatus() == 200) {
                            SharedPreUtil.getInstance().putUser(user.getData().getUserInfo());
                            GeekApplication.setUser(user.getData().getUserInfo());
                            GeekApplication.setAgentInfo(user.getData().getAgentInfo());
                            GeekApplication.setOrderInfo(user.getData().getOrderInfo());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else if (user.getStatus() == 118) {
                            Intent intent = new Intent(LoginActivity.this, BindAccountActivity.class);
                            intent.putExtra("type", type);
                            intent.putExtra("user", LoginActivity.this.user);
                            startActivity(intent);
                        }

                    }
                }, errorListener(""));
    }
}
