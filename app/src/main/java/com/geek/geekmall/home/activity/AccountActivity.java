package com.geek.geekmall.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.ExitFragment;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.UserData;
import com.geek.geekmall.order.activity.OrderListActivty;
import com.geek.geekmall.profile.activity.AboutActivity;
import com.geek.geekmall.profile.activity.AgencyActivity;
import com.geek.geekmall.profile.activity.FeedbackActivity;
import com.geek.geekmall.profile.activity.FootPrintActivity;
import com.geek.geekmall.profile.activity.LikeActivity;
import com.geek.geekmall.profile.activity.MoneyActivity;
import com.geek.geekmall.profile.activity.ProfileMainActivity;
import com.geek.geekmall.profile.activity.SettingActivity;
import com.geek.geekmall.register.LoginActivity;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.views.MyShareDialog;
import com.geek.geekmall.views.OptionsItemView;

/**
 * Created by apple on 4/23/15.
 */
public class AccountActivity extends ExitFragment implements View.OnClickListener {
    private LinearLayout mFavorLayout;
    private LinearLayout mOrderLayout;
    private LinearLayout mFootPrintLayout;
    private TextView mLoginLayout;
    private LinearLayout mInfoLayout;
    private TextView mNameView;
    private TextView mMoneyView;
    private TextView mCoinView;
    private TextView mUnPay;
    private TextView mUnCommon;
    private TextView mUnReceive;
    private TextView mAfterSale;
    private TextView mUnSend;


    private TextView mUnPayNumber;
    private TextView mUnCommonNumber;
    private TextView mUnReceiveNumber;
    private TextView mAfterSaleNumber;
    private TextView mUnSendNumber;
    private TextView mFavorNumView;
    private TextView mOrderNumView;
    private TextView mHistoryNumView;
    private OptionsItemView mAgency;
    private OptionsItemView mInfo;
    private OptionsItemView mAbout;
    private OptionsItemView mFeedBack;
    private OptionsItemView mSettingView;
    private OptionsItemView mPurse;
    private ImageView mAvator;
    private RegisterBroadCast mBroadCast;
    private User user;
    private String userId = "";
    private String token = "";
    private MyShareDialog mMyShareDialog;
    private ImageView mShareView;
    private PullToZoomScrollViewEx scrollView;
    private Button mCharge;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_account);

        init();
        registerBroadCast();

    }

    private void init() {

        scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
        scrollView.setParallax(false);
        View headView = LayoutInflater.from(this).inflate(R.layout.account_header_layout, null, false);
        View zoomView = LayoutInflater.from(this).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(this).inflate(R.layout.profile_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);

        mUnPayNumber = (TextView) contentView.findViewById(R.id.un_pay_number);
        mUnCommonNumber = (TextView) contentView.findViewById(R.id.un_common_number);
        mUnReceiveNumber = (TextView) contentView.findViewById(R.id.un_recevie_number);
        mUnSendNumber = (TextView) contentView.findViewById(R.id.un_send_number);
        mAfterSaleNumber = (TextView) contentView.findViewById(R.id.after_sale_number);

        mShareView = (ImageView) findViewById(R.id.share);
        mNameView = (TextView) headView.findViewById(R.id.name);
        mMoneyView = (TextView) headView.findViewById(R.id.money);
        mCoinView = (TextView) headView.findViewById(R.id.coin);
        mCharge = (Button) findViewById(R.id.charge);
        mInfoLayout = (LinearLayout) headView.findViewById(R.id.info);
        mFavorNumView = (TextView) headView.findViewById(R.id.favor_number);
        mOrderNumView = (TextView) headView.findViewById(R.id.order_number);
        mHistoryNumView = (TextView) headView.findViewById(R.id.footprint_number);
        mUnSend = (TextView) contentView.findViewById(R.id.un_send);
        mUnPay = (TextView) contentView.findViewById(R.id.un_pay);
        mUnCommon = (TextView) contentView.findViewById(R.id.un_common);
        mUnReceive = (TextView) contentView.findViewById(R.id.un_recevie);
        mAfterSale = (TextView) contentView.findViewById(R.id.after_sale);
        mPurse = (OptionsItemView) contentView.findViewById(R.id.purse);
        mUnPay.setOnClickListener(this);
        mUnCommon.setOnClickListener(this);
        mUnReceive.setOnClickListener(this);
        mAfterSale.setOnClickListener(this);
        mUnSend.setOnClickListener(this);
        mShareView.setOnClickListener(this);
        mPurse.setOnClickListener(this);
        mCharge.setOnClickListener(this);
        mSettingView = (OptionsItemView) contentView.findViewById(R.id.settings);
        mInfo = (OptionsItemView) contentView.findViewById(R.id.account_info);
        mAbout = (OptionsItemView) contentView.findViewById(R.id.account_about);
        mFeedBack = (OptionsItemView) contentView.findViewById(R.id.account_feedback);

        mAvator = (ImageView) headView.findViewById(R.id.avator);
        mFootPrintLayout = (LinearLayout) headView.findViewById(R.id.my_footprint);
        mFavorLayout = (LinearLayout) headView.findViewById(R.id.my_favor);
        mOrderLayout = (LinearLayout) headView.findViewById(R.id.my_order);
        mLoginLayout = (TextView) headView.findViewById(R.id.login_register);
        mAgency = (OptionsItemView) contentView.findViewById(R.id.agency);
        mFavorLayout.setOnClickListener(this);
        mOrderLayout.setOnClickListener(this);
        mFootPrintLayout.setOnClickListener(this);
        mAgency.setOnClickListener(this);
        mLoginLayout.setOnClickListener(this);
        mAvator.setOnClickListener(this);
        mInfo.setOnClickListener(this);
        mAbout.setOnClickListener(this);
        mFeedBack.setOnClickListener(this);
        mSettingView.setOnClickListener(this);


    }

    private void getHomeMessage() {
        loadingDialog.show();
        APIControl.getInstance().getHomeMessage(this, userId, token, new DataResponseListener<UserData>() {
            @Override
            public void onResponse(UserData userData) {
                loadingDialog.dismiss();
                GeekApplication.setOrderInfo(userData.getData().getOrderInfo());
                GeekApplication.setAgentInfo(userData.getData().getAgentInfo());
                GeekApplication.setUserMoney(userData.getData().getUserMoney());

                setName();
            }
        }, errorListener());
    }

    @Override
    public Response.ErrorListener errorListener() {
        setName();
        return super.errorListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
            getHomeMessage();
        } else {
            mInfoLayout.setVisibility(View.GONE);
            mLoginLayout.setVisibility(View.VISIBLE);
            mFavorNumView.setText("0");
            mOrderNumView.setText("0");
            mHistoryNumView.setText("0");

            mUnSendNumber.setVisibility(View.GONE);
            mUnPayNumber.setVisibility(View.GONE);
            mUnCommonNumber.setVisibility(View.GONE);
            mUnReceiveNumber.setVisibility(View.GONE);
            mAfterSaleNumber.setVisibility(View.GONE);
            ImageLoader.getInstance(this).getPicasso().load(R.drawable.avatar_default)
                    .into(mAvator);
        }
    }

    private void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.geek.geekmall.action.register");
//        filter.addAction("com.geek.geekmall.action.auth.qq");
//        filter.addAction("com.geek.geekmall.action.auth.sina");
//        filter.addAction("com.geek.geekmall.action.auth.weixin");

        mBroadCast = new RegisterBroadCast();
        registerReceiver(mBroadCast, filter);
    }

    private void setName() {
        if (GeekApplication.isLogin()) {
            mLoginLayout.setVisibility(View.GONE);
            mInfoLayout.setVisibility(View.VISIBLE);
            mNameView.setText(GeekApplication.getUser().getNickname());
            if (GeekApplication.getOrderInfo() != null) {
                mFavorNumView.setText(GeekApplication.getOrderInfo().getFavourNumber());
                mOrderNumView.setText(GeekApplication.getOrderInfo().getOrderNumber());
                mHistoryNumView.setText(GeekApplication.getOrderInfo().getHistoryNumber());
                if (!TextUtils.isEmpty(GeekApplication.getOrderInfo().getFalseSend()) && !GeekApplication.getOrderInfo().getFalseSend().equals("0")) {
                    mUnSendNumber.setVisibility(View.VISIBLE);
                    mUnSendNumber.setText(GeekApplication.getOrderInfo().getFalseSend());
                } else {
                    mUnSendNumber.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(GeekApplication.getOrderInfo().getFalsePay()) && !GeekApplication.getOrderInfo().getFalsePay().equals("0")) {
                    mUnPayNumber.setVisibility(View.VISIBLE);
                    mUnPayNumber.setText(GeekApplication.getOrderInfo().getFalsePay());
                } else {
                    mUnPayNumber.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(GeekApplication.getOrderInfo().getFalseComment()) && !GeekApplication.getOrderInfo().getFalseComment().equals("0")) {
                    mUnCommonNumber.setVisibility(View.VISIBLE);
                    mUnCommonNumber.setText(GeekApplication.getOrderInfo().getFalseComment());
                } else {
                    mUnCommonNumber.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(GeekApplication.getOrderInfo().getFalseGoodsReceipt()) && !GeekApplication.getOrderInfo().getFalseGoodsReceipt().equals("0")) {
                    mUnReceiveNumber.setVisibility(View.VISIBLE);
                    mUnReceiveNumber.setText(GeekApplication.getOrderInfo().getFalseGoodsReceipt());
                } else {
                    mUnReceiveNumber.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(GeekApplication.getOrderInfo().getRefundNumber()) && !GeekApplication.getOrderInfo().getRefundNumber().equals("0")) {
                    mAfterSaleNumber.setVisibility(View.VISIBLE);
                    mAfterSaleNumber.setText(GeekApplication.getOrderInfo().getRefundNumber());
                } else {
                    mAfterSaleNumber.setVisibility(View.GONE);
                }
            }
            if (GeekApplication.getUserMoney() != null) {
                mMoneyView.setText("充值余额：" + GeekApplication.getUserMoney().getBalance());
                mCoinView.setText("返还余额：" + GeekApplication.getUserMoney().getTtCoin());
            }
            user = GeekApplication.getUser();
            if (user != null) {
                userId = user.getId();
                token = user.getToken();
                if (GeekApplication.isLogin() && !TextUtils.isEmpty(user.getImgUrl())) {
                    String url = user.getImgUrl();
                    if (!url.startsWith("http://")) {
                        url = URLs.IMAGE_URL + user.getImgUrl();
                    }
                    ImageLoader.getInstance(this).getPicasso().load(url)
                            .placeholder(R.drawable.avatar_default)
                            .resize(DensityUtils.dp2px(this, 80), DensityUtils.dp2px(this, 80))
                            .transform(ImageLoader.getInstance(this).new RoundedCornersTransformation(120))
                            .into(mAvator);
                }
            }

        } else {
//            mInfoLayout.setVisibility(View.GONE);
//            mLoginLayout.setVisibility(View.VISIBLE);
//            mFavorNumView.setText("0");
//            mOrderNumView.setText("0");
//            mHistoryNumView.setText("0");
//            ImageLoader.getInstance(this).getPicasso().load(R.drawable.avatar_default)
//                    .into(mAvator);
        }
    }

    private void login() {
        loadingDialog.show();
        APIControl.getInstance().login(AccountActivity.this, GeekApplication.getUser().getPhone(),
                GeekApplication.getUser().getPassword(), new DataResponseListener<UserData>() {
                    @Override
                    public void onResponse(UserData user) {
                        loadingDialog.dismiss();
                        if (user.getStatus() == 200) {
                            SharedPreUtil.getInstance().putUser(user.getData().getUserInfo());
                            GeekApplication.setUser(user.getData().getUserInfo());
                            setName();
                        }

                    }
                }, errorListener());
    }

    private void autoLogin(String token) {
        loadingDialog.show();
        APIControl.getInstance().autoLogin(AccountActivity.this, token, new DataResponseListener<UserData>() {
            @Override
            public void onResponse(UserData user) {
                loadingDialog.dismiss();
                if (user.getStatus() == 200) {
                    SharedPreUtil.getInstance().putUser(user.getData().getUserInfo());
                    GeekApplication.setUser(user.getData().getUserInfo());
                    setName();
                }

            }
        }, errorListener());
    }

    private void authLogin(String openId, String accessToken, String type) {
        loadingDialog.show();
        APIControl.getInstance().authLogin(AccountActivity.this, openId, accessToken, type,
                new DataResponseListener<UserData>() {
                    @Override
                    public void onResponse(UserData user) {
                        loadingDialog.dismiss();
                        if (user.getStatus() == 200) {
                            SharedPreUtil.getInstance().putUser(user.getData().getUserInfo());
                            GeekApplication.setUser(user.getData().getUserInfo());
                            setName();
                        }

                    }
                }, errorListener());
    }

    private class RegisterBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            if ("com.geek.geekmall.action.register".equals(intent.getAction())) {
//
//                login();
//            } else if ("com.geek.geekmall.action.auth.qq".equals(intent.getAction())) {
//                authLogin( GeekApplication.getUser().getOpenId(), GeekApplication.getUser().getAccessToken(),"3");
//            }else if ("com.geek.geekmall.action.auth.sina".equals(intent.getAction())) {
//                authLogin( GeekApplication.getUser().getOpenId(), GeekApplication.getUser().getAccessToken(),"5");
//            }else if ("com.geek.geekmall.action.auth.weixin".equals(intent.getAction())) {
//                authLogin( GeekApplication.getUser().getOpenId(), GeekApplication.getUser().getAccessToken(),"4");
//            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.share:
                if (GeekApplication.isLogin()) {
//                    if (mMyShareDialog == null) {
                    String url = "";
                    user = GeekApplication.getUser();
                    if (user != null) {
                        url = user.getImgUrl();
                    }
                    mMyShareDialog = new MyShareDialog(this, this, URLs.HOST + "/geektmall-api/api/agent/binding/page?bindingToken=" + GeekApplication.getAgentInfo().getBindingToken(), url);
//                    }
                    mMyShareDialog.show();
                } else {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                }

                break;
            case R.id.settings:
                intent.setClass(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.charge:
                intent.setClass(this, MoneyActivity.class);
                startActivity(intent);
                break;
            case R.id.login_register:
//                intent.setClass(this, LoginActivity.class);
//                startActivityForResult(intent, 1001);
//                break;
            case R.id.avator:
                if (!GeekApplication.isLogin()) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.setClass(this, ProfileMainActivity.class);
                    startActivityForResult(intent, 1006);
                }
                break;
            case R.id.agency:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.setClass(this, AgencyActivity.class);
                    startActivityForResult(intent, 1002);
                }

                break;
            case R.id.my_favor:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.setClass(this, LikeActivity.class);
                    startActivityForResult(intent, 1003);
                }

                break;
            case R.id.my_order:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.setClass(this, OrderListActivty.class);
                    startActivity(intent);
                }

                break;
            case R.id.my_footprint:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.setClass(this, FootPrintActivity.class);
                    startActivityForResult(intent, 1005);
                }

                break;
            case R.id.account_info:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.setClass(this, ProfileMainActivity.class);
                    startActivityForResult(intent, 1006);
                }

                break;
            case R.id.account_about:
                intent.setClass(this, AboutActivity.class);
                startActivityForResult(intent, 1007);
                break;
            case R.id.account_feedback:
                intent.setClass(this, FeedbackActivity.class);
                startActivityForResult(intent, 1008);
                break;
            case R.id.un_pay:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.setClass(this, OrderListActivty.class);
                    intent.putExtra("order", 1);
                    startActivity(intent);
                }

                break;
            case R.id.un_common:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.setClass(this, OrderListActivty.class);
                    intent.putExtra("order", 7);
                    startActivity(intent);
                }

                break;
            case R.id.un_recevie:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.putExtra("order", 3);
                    intent.setClass(this, OrderListActivty.class);
                    startActivity(intent);
                }

                break;
            case R.id.un_send:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.putExtra("order", 2);
                    intent.setClass(this, OrderListActivty.class);
                    startActivity(intent);
                }

                break;

            case R.id.after_sale:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.putExtra("order", 8);
                    intent.setClass(this, OrderListActivty.class);
                    startActivity(intent);
                }

                break;
            case R.id.purse:
                if (user == null) {
                    intent.setClass(this, LoginActivity.class);
                    startActivityForResult(intent, 1001);
                } else {
                    intent.setClass(this, MoneyActivity.class);
                    startActivity(intent);
                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                setName();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadCast);
        super.onDestroy();
    }
}
