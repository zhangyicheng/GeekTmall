package com.geek.geekmall.home.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Response;
import com.geek.geekmall.Constant;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Address;
import com.geek.geekmall.bean.OrderInfos;
import com.geek.geekmall.bean.SubmitOrderInfo;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.adapter.FreightAdapter;
import com.geek.geekmall.home.adapter.SettlementAdapter;
import com.geek.geekmall.model.AddressData;
import com.geek.geekmall.model.BuyData;
import com.geek.geekmall.model.BuyInfo;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.DistributionWay;
import com.geek.geekmall.model.WXResultData;
import com.geek.geekmall.order.activity.OrderListActivty;
import com.geek.geekmall.profile.activity.AddAddressActivity;
import com.geek.geekmall.profile.activity.AddressActivty;
import com.geek.geekmall.profile.activity.PayPasswordActivity;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.PayResult;
import com.geek.geekmall.utils.SignUtils;
import com.geek.geekmall.utils.wxutil.MD5;
import com.geek.geekmall.views.ExpandListViewForScrollView;
import com.geek.geekmall.views.SureDialog;
import com.geek.geekmall.views.SureListDialog;
import com.geek.geekmall.views.ToastView;
import com.geek.geekmall.wxapi.WeiXinConstants;
import com.google.gson.Gson;
import com.gridpasswordview.PasswordDialog;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by apple on 6/24/15.
 */
public class SettlementActivity extends BaseActivity implements View.OnClickListener {
    private TextView mBack;
    private TextView mAddAddress;
    private RelativeLayout mAddressLayout;
    private User user;
    private String userId;
    private String token;
    private TextView mNameView;
    private TextView mPhoneView;
    private TextView mAddressView;
    private TextView mTotalView;
    private CheckBox mMoneyBag;
    private SettlementAdapter mAdapter;
    private Button mCommit;
    private ExpandListViewForScrollView mListView;
    private BuyInfo mCarts;
    private IWXAPI api;
    StringBuffer sb;
    PayReq req;
    Map<String, String> resultunifiedorder;
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;
    private SubmitOrderInfo orderInfo;
    private RadioGroup mPayGroup;
    private RadioButton mAliButton;
    private RadioButton mWeXinButton;

    private SureDialog mSureDialog;
    private int isPayBalance = 0;
    private TextView mRemainPay;
    private ProgressDialog progressDialog;
    private String submitOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settlement_layout);
        //wexin
        api = WXAPIFactory.createWXAPI(this, WeiXinConstants.APP_ID, false);
        api.registerApp(WeiXinConstants.APP_ID);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        req = new PayReq();
        sb = new StringBuffer();
        mCarts = (BuyInfo) getIntent().getSerializableExtra("list");
        orderInfo = new SubmitOrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setIsPayBalance(0);
        List<OrderInfos> orderInfos = new ArrayList<>();
        if (mCarts.getSettlementShoppCartList() != null) {
            for (int i = 0; i < mCarts.getSettlementShoppCartList().size(); i++) {
                OrderInfos order = new OrderInfos();
                order.setStoreId(mCarts.getSettlementShoppCartList().get(i).getStoreId());
                if (mCarts.getSettlementShoppCartList().get(i).getDistributionWay() != null
                        && mCarts.getSettlementShoppCartList().get(i).getDistributionWay().size() > 0) {
                    order.setPostageType(mCarts.getSettlementShoppCartList().get(i).getDistributionWay().get(0).getType() + "");
                    order.setPostage(mCarts.getSettlementShoppCartList().get(i).getDistributionWay().get(0).getPostage());
                }

                StringBuilder builder = new StringBuilder();
                StringBuilder builder1 = new StringBuilder();
                for (int k = 0; k < mCarts.getSettlementShoppCartList().get(i).getGoodsInfoMap().size(); k++) {
                    builder.append(mCarts.getSettlementShoppCartList().get(i).getGoodsInfoMap().get(k).getShopCartId()).append(",");
                    if (mCarts.getSettlementShoppCartList().get(i).getGoodsInfoMap().get(k).getCoinSelect() == 1){
                        builder1.append(mCarts.getSettlementShoppCartList().get(i).getGoodsInfoMap().get(k).getShopCartId()).append(",");
                    }
                }
                if (builder1.length()>1){
                    builder1.deleteCharAt(builder1.length() - 1);
                }
                if (builder.length()>1){
                    builder.deleteCharAt(builder.length() - 1);
                }

                order.setShopCartIds(builder.toString());
                if (mCarts.getAllreadyUseCoin()>0){
                    order.setUseCoinShopCartIds(builder1.toString());
                }
                orderInfos.add(order);
            }
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("");
        progressDialog.setMessage("请稍等...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setOnCancelListener(null);
        orderInfo.setOrderInfos(orderInfos);
        init();
        mTotalView.setText("￥" + mCarts.getTotalPrice());
        mRemainPay.setText("￥" + mCarts.getPayMoney());
        getDefaultAdress();
        registerBroadCast();
        if (!api.isWXAppInstalled()) {
            mWeXinButton.setEnabled(false);
        }



        if (mCarts.getPayMoney() == 0) {
            if (mCarts.getAllreadyUseCoin()>0){
                orderInfo.setPayType(4);
            }
            if (mCarts.getAllreadyUseBalance() >0){
                orderInfo.setPayType(3);
            }
            mAliButton.setEnabled(false);
            mWeXinButton.setEnabled(false);
            mPayGroup.clearCheck();

        } else {
            if (api.isWXAppInstalled()) {
                mWeXinButton.setEnabled(true);
            }
            mAliButton.setEnabled(true);
            mPayGroup.check(R.id.alipay);
            orderInfo.setPayType(1);
        }


    }

    public void calculate() {
//        if (TextUtils.isEmpty(orderInfo.getAddressId())) {
//            new ToastView(this, "请选择收货地址").show();
//            return;
//        }
        progressDialog.show();
        for (int i = 0; i < mAdapter.getGroupCount(); i++) {
            StringBuilder builder = new StringBuilder();
            for (int k = 0; k < mAdapter.getChildrenCount(i); k++) {
                if (mAdapter.getChild(i, k).getCoinSelect() == 1) {
                    builder.append(mAdapter.getChild(i, k).getShopCartId()).append(",");
                }
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            orderInfo.getOrderInfos().get(i).setUseCoinShopCartIds(builder.toString());
        }
        Gson gson = new Gson();
        String json = gson.toJson(orderInfo);

        APIControl.getInstance().calculateCart(this, json, token, new DataResponseListener<BuyData>() {
            @Override
            public void onResponse(BuyData o) {
                progressDialog.dismiss();
                if (o.getStatus() == 200) {
                    if (o.getData().getIsPayBalance() == 1) {
                        mMoneyBag.setChecked(true);
                    } else {
                        mMoneyBag.setChecked(false);
                    }
                    mCarts = o.getData();
                    mMoneyBag.setText(getResources().getString(R.string.moneybag, o.getData().getUserTotalBalance() + ""));
                    mTotalView.setText("￥" + o.getData().getTotalPrice());
                    mRemainPay.setText("￥" + o.getData().getPayMoney());
                    if (o.getData().getPayMoney() == 0) {
                            if (o.getData().getAllreadyUseCoin()>0){
                                orderInfo.setPayType(4);
                            }
                            if (o.getData().getAllreadyUseBalance() >0){
                                orderInfo.setPayType(3);
                            }

                        mAliButton.setEnabled(false);
                        mWeXinButton.setEnabled(false);
                        mPayGroup.clearCheck();
                    } else {
                        if (api.isWXAppInstalled()) {
                            mWeXinButton.setEnabled(true);
                        }
                        mAliButton.setEnabled(true);
                        mPayGroup.check(R.id.alipay);
                    }
                }

            }
        }, errorListener(""));
    }

    private SureListDialog mDialog;
    private int lastCheckedPosition = 0;

    public void setFreight(final TextView view, final String storeId, final List<DistributionWay> ways) {
        mDialog = new SureListDialog(this, new SureListDialog.FreightListener() {
            @Override
            public void setFreightPosition(int position) {
                lastCheckedPosition = position;
                float total = 0f;
                for (int i = 0; i < orderInfo.getOrderInfos().size(); i++) {
                    if (orderInfo.getOrderInfos().get(i).getStoreId().equals(storeId)) {
                        MyLog.debug(SettlementActivity.class, storeId + "----------------");
                        orderInfo.getOrderInfos().get(i).setPostageType(ways.get(mDialog.getListView().getCheckedItemPosition()).getType() + "");
                        orderInfo.getOrderInfos().get(i).setPostage(ways.get(mDialog.getListView().getCheckedItemPosition()).getPostage());
                        calculate();
                        view.setText(ways.get(mDialog.getListView().getCheckedItemPosition()).getPostage() + "元");
                    }
                    total += orderInfo.getOrderInfos().get(i).getPostage();
                }
//                float price = 0f;
//                for (int j = 0; j < mCarts.getSettlementShoppCartList().size(); j++) {
//                    price = price + Float.valueOf(mCarts.getSettlementShoppCartList().get(j).getTotalPrice());
//
//                }
//                BigDecimal b1 = new BigDecimal(Float.toString(total));
//                BigDecimal b2 = new BigDecimal(Float.toString(price));
//
//                mTotalView.setText("￥" + b1.add(b2).floatValue());

                mDialog.dismiss();
            }
        });
        mDialog.show();
        FreightAdapter adapter = new FreightAdapter(this, mDialog.getListView());
        adapter.setmFreightWay(ways);
        mDialog.setAdapter(lastCheckedPosition, adapter);
    }

    private void submitOrder() {
        if (TextUtils.isEmpty(orderInfo.getAddressId())) {
            new ToastView(this, "请选择收货地址").show();
            return;
        }
        if (orderInfo.getPayType() == 0) {
            new ToastView(this, "请选择支付方式").show();
            return;
        }
        Gson gson = new Gson();
        String json = gson.toJson(orderInfo);
        progressDialog.show();
        APIControl.getInstance().submitOrder(this, json, userId, token, new DataResponseListener<WXResultData>() {
            @Override
            public void onResponse(WXResultData wxResultData) {
                progressDialog.dismiss();
                if (wxResultData.getStatus() == 200) {
                    if (wxResultData.getData().getPrepayid() != null && !wxResultData.getData().getPrepayid().isEmpty()&& orderInfo.getPayType() ==2) {
                        progressDialog.show();
//                        submitOrderId = wxResultData.getData().getSubmitOrderId();
                        genPayReq(wxResultData.getData().getPrepayid());
                    } else if (wxResultData.getData().getNotify_url() != null&& orderInfo.getPayType() ==1) {
//                        submitOrderId = wxResultData.getData().getSettlementId();
                        pay(wxResultData.getData().getTitle(), wxResultData.getData().getTotalPrice() + "", wxResultData.getData().getSettlementId(), wxResultData.getData().getNotify_url());
                    } else{
                        startActivity(new Intent(SettlementActivity.this, OrderListActivty.class));
                        finish();
                        new ToastView(SettlementActivity.this, "支付成功").show();
                    }
                } else if (wxResultData.getStatus() == 145) {
                    //设置支付密码
                    Intent intent = new Intent(SettlementActivity.this, PayPasswordActivity.class);
                    startActivity(intent);
                } else if (wxResultData.getStatus() == 144 || wxResultData.getStatus() == 142) {
                    checkPassword();
                } else {
                    new ToastView(SettlementActivity.this, wxResultData.getErrorMsg()).show();
                }
            }
        }, errorListener(""));
    }

    private PasswordDialog dialog;
    private String password;

    private void checkPassword() {

        dialog = new PasswordDialog(this, "请输入支付密码", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = dialog.getPasswodViewString();
                progressDialog.show();
                APIControl.getInstance().checkPayPwd(SettlementActivity.this, userId, password, new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        progressDialog.dismiss();
                        if (commonData.getStatus() == 200) {
                            submitOrder();
                            dialog.dismiss();
                        } else {
                            dialog.clear();
                            new ToastView(SettlementActivity.this, commonData.getErrorMsg()).show();
                        }
                    }
                }, errorListener(""));

            }
        });
        dialog.show();
    }

    @Override
    protected Response.ErrorListener errorListener(String url) {
//        progressDialog.dismiss();
        return super.errorListener(url);
    }

    private void init() {
        mRemainPay = (TextView) findViewById(R.id.remain_pay);
        mMoneyBag = (CheckBox) findViewById(R.id.money_bag);
        mTotalView = (TextView) findViewById(R.id.total);
        mBack = (TextView) findViewById(R.id.back);
        mCommit = (Button) findViewById(R.id.commit);
        mNameView = (TextView) findViewById(R.id.name);
        mPhoneView = (TextView) findViewById(R.id.phone);
        mAddressView = (TextView) findViewById(R.id.address);
        mListView = (ExpandListViewForScrollView) findViewById(R.id.list_view);
        mAddAddress = (TextView) findViewById(R.id.add_address);
        mAddressLayout = (RelativeLayout) findViewById(R.id.address_layout);
        mPayGroup = (RadioGroup) findViewById(R.id.pay_type);
        mBack.setOnClickListener(this);
        mAddAddress.setOnClickListener(this);
        mAddressLayout.setOnClickListener(this);
        mAdapter = new SettlementAdapter(this);
        mCommit.setOnClickListener(this);
        mListView.setGroupIndicator(null);
        mMoneyBag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    orderInfo.setIsPayBalance(1);
                } else {
                    orderInfo.setIsPayBalance(0);
                }
                calculate();
            }
        });
        if (mCarts != null) {
            mAdapter.setmCarts(mCarts.getSettlementShoppCartList());
            mListView.setAdapter(mAdapter);
            for (int i = 0; i < mCarts.getSettlementShoppCartList().size(); i++) {
                mListView.expandGroup(i);
            }
        }
        if (mCarts != null) {
            mMoneyBag.setText(getResources().getString(R.string.moneybag, mCarts.getUserTotalBalance() + ""));
        }
        mAliButton = (RadioButton) findViewById(R.id.alipay);
        mWeXinButton = (RadioButton) findViewById(R.id.wechat_pay);
        mPayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.alipay) {
                    orderInfo.setPayType(1);
                } else if (checkedId == R.id.wechat_pay) {
                    orderInfo.setPayType(2);
                }
//                else {
//                    orderInfo.setPayType(3);
//                }
            }
        });
        //TODO
//        if("钱包余额大约付款金额"){
//            mPayGroup.check(R.id.money_bag);
//        } else {
//            mPayGroup.check(R.id.alipay);
//        }
    }

    @Override
    public void onBackPressed() {
        giveUp();
    }

    private void giveUp() {
        mSureDialog = new SureDialog(SettlementActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSureDialog.dismiss();
                finish();
            }
        });
        mSureDialog.show();
        mSureDialog.setTitle("确定放弃支付吗？");

    }

    private void getDefaultAdress() {
        progressDialog.show();
        APIControl.getInstance().getDefaultAddress(this, userId, token, new DataResponseListener<AddressData>() {
            @Override
            public void onResponse(AddressData addressData) {
                progressDialog.dismiss();
                if (addressData.getStatus() == 200
                        && addressData.getData().getAddress() != null
                        && addressData.getData().getContactName() != null
                        && addressData.getData().getContactPhone() != null) {
                    orderInfo.setAddressId(addressData.getData().getId());
                    mAddressLayout.setVisibility(View.VISIBLE);
                    mAddAddress.setVisibility(View.GONE);
                    mPhoneView.setText(addressData.getData().getContactPhone());
                    mNameView.setText(addressData.getData().getContactName());
                    mAddressView.setText("收货地址：" + addressData.getData().getProvinceName()
                            + addressData.getData().getCityName()
                            + addressData.getData().getZoneName()
                            + addressData.getData().getAddress());

                } else {
                    orderInfo.setAddressId("");
                    mAddressLayout.setVisibility(View.GONE);
                    mAddAddress.setVisibility(View.VISIBLE);
                }
            }
        }, errorListener(""));
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
                giveUp();
                break;
            case R.id.add_address:
                intent.setClass(this, AddAddressActivity.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.address_layout:
                intent.setClass(this, AddressActivty.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1000);
                break;
            case R.id.commit:
//                GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
//                getPrepayId.execute();
//                genPayReq();
//                pay(null);
//                if (orderInfo.getPayType() == 2) {
                submitOrder();
//                } else if (orderInfo.getPayType() == 1) {
//                    pay(null);
//                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Address address = (Address) data.getSerializableExtra("address");
                orderInfo.setAddressId(address.getId());
                mAddressLayout.setVisibility(View.VISIBLE);
                mAddAddress.setVisibility(View.GONE);
                mPhoneView.setText(address.getContactPhone());
                mNameView.setText(address.getContactName());
                mAddressView.setText("收货地址：" + address.getProvinceName() + address.getCityName() + address.getZoneName() + address.getAddress());
            }
        } else {
            getDefaultAdress();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(WeiXinConstants.APP_KEY);

        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }


    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    private void genPayReq(String prepayId) {
        progressDialog.show();
        req.appId = WeiXinConstants.APP_ID;
        req.partnerId = WeiXinConstants.MCH_ID;
        req.prepayId = prepayId;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");

        sendPayReq();
        Log.e("orion", signParams.toString());

    }

    private void sendPayReq() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                api.registerApp(WeiXinConstants.APP_ID);
                api.sendReq(req);
                progressDialog.dismiss();
                finish();
            }
        }).start();


    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(String name, String money, String orderId, String notify_url) {
        // 订单
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mCarts.getSettlementShoppCartList().size(); i++) {
            for (int j = 0; j < mCarts.getSettlementShoppCartList().get(i).getGoodsInfoMap().size(); j++) {
                builder.append(mCarts.getSettlementShoppCartList().get(i).getGoodsInfoMap().get(j).getGoodsName() + ",");
            }

        }
        String orderInfo = getOrderInfo(name, builder.toString(), money, orderId, notify_url);

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(SettlementActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     */
    public void check(View v) {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(SettlementActivity.this);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price, String orderId, String notify_url) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Constant.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Constant.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notify_url
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, Constant.RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(SettlementActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SettlementActivity.this, OrderListActivty.class));
                        finish();
//                        subUserMoney(submitOrderId);

                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(SettlementActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            startActivity(new Intent(SettlementActivity.this, OrderListActivty.class));
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(SettlementActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(SettlementActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private void subUserMoney(String orderId) {
        APIControl.getInstance().subUserMoney(this, orderId, new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {

                startActivity(new Intent(SettlementActivity.this, OrderListActivty.class));
                if (commonData.getStatus() != 200) {
                    new ToastView(SettlementActivity.this, commonData.getErrorMsg()).show();
                }
                finish();

            }
        }, errorListener(""));
    }

    private void registerBroadCast() {
        mBroadCast = new RegisterBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.geekmall.orderpay");

        registerReceiver(mBroadCast, filter);
    }

    private RegisterBroadCast mBroadCast;

    private class RegisterBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.geekmall.orderpay".equals(intent.getAction())) {
//                if (intent.getStringExtra("wexin").equals("orderpay")){
//                    subUserMoney(submitOrderId);
//                }
                finish();
                startActivity(new Intent(SettlementActivity.this, OrderListActivty.class));

            }
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadCast);
        super.onDestroy();
    }

}
