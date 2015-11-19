package com.geek.geekmall.order.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.geek.geekmall.Constant;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Order;
import com.geek.geekmall.bean.OrderInfos;
import com.geek.geekmall.bean.SubmitOrderInfo;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.BuyData;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.WXResultData;
import com.geek.geekmall.profile.activity.PayPasswordActivity;
import com.geek.geekmall.utils.PayResult;
import com.geek.geekmall.utils.SignUtils;
import com.geek.geekmall.utils.wxutil.MD5;
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
import java.util.Random;

/**
 * Created by apple on 8/13/15.
 */
public class OrderpayActivity extends BaseActivity implements View.OnClickListener {
    private User user;
    private TextView mBack;
    private RadioGroup mPayGroup;
    private RadioButton mAliButton;
    private RadioButton mWeXinButton;
    private CheckBox mMoneyBag;
    private TextView mRemainPay;
    private TextView mNameView;
    private TextView mPriceView;
    private Button mCommitBtn;
    private String userId;
    private String token;
    private IWXAPI api;
    StringBuffer sb;
    PayReq req;
    private SubmitOrderInfo orderInfo;
    private Order mOrder;
    private static final int SDK_PAY_FLAG = 1;
    private String submitOrderId;
    private static final int SDK_CHECK_FLAG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        mOrder = (Order) getIntent().getSerializableExtra("order");
        //wexin
        api = WXAPIFactory.createWXAPI(this, WeiXinConstants.APP_ID, false);
        api.registerApp(WeiXinConstants.APP_ID);
        req = new PayReq();
        sb = new StringBuffer();

        orderInfo = new SubmitOrderInfo();
        orderInfo.setUserId(userId);
        orderInfo.setIsPayBalance(0);
        orderInfo.setAddressId(mOrder.getAddressId());
        List<OrderInfos> orderInfos = new ArrayList<>();
        if (mOrder.getGoodsList() != null) {
            for (int i = 0; i < mOrder.getGoodsList().size(); i++) {
                OrderInfos order = new OrderInfos();
                order.setStoreId(mOrder.getStoreName());
                order.setPostageType("");
//                order.setPostage(mOrder.getPostage());
                order.setOrderId(mOrder.getGoodsList().get(i).getOrderId());
                orderInfos.add(order);
            }
        }
        orderInfo.setOrderIds(mOrder.getId());
        orderInfo.setOrderInfos(orderInfos);
        init();
        mMoneyBag.setText(getResources().getString(R.string.moneybag, "0"));
        calculate();
        registerBroadCast();
    }

    private void init() {
        mBack = (TextView) findViewById(R.id.back);
        mPayGroup = (RadioGroup) findViewById(R.id.pay_type);
        mAliButton = (RadioButton) findViewById(R.id.alipay);
        mWeXinButton = (RadioButton) findViewById(R.id.wechat_pay);
        mNameView = (TextView) findViewById(R.id.name);
        mCommitBtn = (Button) findViewById(R.id.commit);
        mPriceView = (TextView) findViewById(R.id.price);
        mRemainPay = (TextView) findViewById(R.id.remain_pay);
        mMoneyBag = (CheckBox) findViewById(R.id.money_bag);
        mBack.setOnClickListener(this);
        mCommitBtn.setOnClickListener(this);
        mPayGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.alipay) {
                    orderInfo.setPayType(1);
                } else if (checkedId == R.id.wechat_pay) {
                    orderInfo.setPayType(2);
                }
            }
        });
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

    }

    public void calculate() {
        loadingDialog.show();


        Gson gson = new Gson();
        String json = gson.toJson(orderInfo);

        APIControl.getInstance().calculateOrder(this, json, token, new DataResponseListener<BuyData>() {
            @Override
            public void onResponse(BuyData o) {
                loadingDialog.dismiss();
                if (o.getStatus() == 200) {
                    mPriceView.setText(o.getData().getTotalPrice() + "元");
                    mRemainPay.setText(o.getData().getPayMoney()+"元");
                    if ((o.getData().getUserTotalBalance())==0){
                        mMoneyBag.setEnabled(false);
                    } else {
                        mMoneyBag.setText(getResources().getString(R.string.moneybag,o.getData().getUserTotalBalance()));
                    }

//                    if (o.getData().getIsPayBalance() == 1) {
//                        mMoneyBag.setChecked(true);
//                    } else {
//                        mMoneyBag.setChecked(false);
//                    }
//                    mRemainPay.setText("￥" + o.getData().getPayMoney());
                    if (o.getData().getPayMoney() == 0) {
                        orderInfo.setPayType(3);
                        mAliButton.setEnabled(false);
                        mWeXinButton.setEnabled(false);
                        mPayGroup.clearCheck();
                    } else {
                        mWeXinButton.setEnabled(true);
                        mAliButton.setEnabled(true);
                    }
                } else {
                    new ToastView(OrderpayActivity.this,o.getErrorMsg()).show();
                }

            }
        }, errorListener(""));
    }

    private void submitOrder() {

//        if (orderInfo.getPayType() == 0) {
//            new ToastView(this, "请选择支付方式").show();
//            return;
//        }
        Gson gson = new Gson();
        String json = gson.toJson(orderInfo);
        loadingDialog.show();
        APIControl.getInstance().orderSubmitOrder(this, json, userId, token, new DataResponseListener<WXResultData>() {
            @Override
            public void onResponse(WXResultData wxResultData) {
                loadingDialog.dismiss();
                if (wxResultData.getStatus() == 200) {
                    if (wxResultData.getData().getPrepayid() != null && !wxResultData.getData().getPrepayid().isEmpty()) {
                        submitOrderId = wxResultData.getData().getSubmitOrderId();
                        genPayReq(wxResultData.getData().getPrepayid());
                    } else if (wxResultData.getData().getNotify_url() != null) {
                        submitOrderId = wxResultData.getData().getSubmitOrderId();
                        pay(wxResultData.getData().getTitle(),wxResultData.getData().getTotalPrice() + "", wxResultData.getData().getSettlementId(), wxResultData.getData().getNotify_url());
                    } else if (wxResultData.getData().getTotalPrice() ==0){
                        new ToastView(OrderpayActivity.this, "支付成功").show();
                        finish();
                    }
                } else if (wxResultData.getStatus() == 145) {
                    //设置支付密码
                    Intent intent = new Intent(OrderpayActivity.this, PayPasswordActivity.class);
                    startActivity(intent);
                } else if (wxResultData.getStatus() == 144 || wxResultData.getStatus() == 142) {
                    checkPassword();
                } else {
                    new ToastView(OrderpayActivity.this, wxResultData.getErrorMsg()).show();
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
                APIControl.getInstance().checkPayPwd(OrderpayActivity.this, userId, password, new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        if (commonData.getStatus() == 200) {
                            submitOrder();
                        }else {
                            new ToastView(OrderpayActivity.this,commonData.getErrorMsg()).show();
                        }
                    }
                }, errorListener(""));
                dialog.dismiss();
            }
        });
        dialog.show();
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
        loadingDialog.show();
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
        api.registerApp(WeiXinConstants.APP_ID);
        api.sendReq(req);
        loadingDialog.dismiss();
        finish();
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(String name,String money, String orderId, String notify_url) {
        // 订单
        StringBuilder builder = new StringBuilder();
        builder.append(mOrder.getStoreName());

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
                PayTask alipay = new PayTask(OrderpayActivity.this);
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
                PayTask payTask = new PayTask(OrderpayActivity.this);
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
//                        subUserMoney(submitOrderId);
                        Toast.makeText(OrderpayActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OrderpayActivity.this, OrderListActivty.class));
                        finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderpayActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderpayActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(OrderpayActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.commit:
                submitOrder();
                break;
            default:
                break;
        }
    }
    private void subUserMoney(String orderId) {
        APIControl.getInstance().subUserMoney(this, orderId, new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {

                startActivity(new Intent(OrderpayActivity.this, OrderListActivty.class));
                if (commonData.getStatus() != 200) {
                    new ToastView(OrderpayActivity.this, commonData.getErrorMsg()).show();
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
                startActivity(new Intent(OrderpayActivity.this, OrderListActivty.class));

            }
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadCast);
        super.onDestroy();
    }

}
