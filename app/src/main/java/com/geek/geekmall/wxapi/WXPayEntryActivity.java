package com.geek.geekmall.wxapi;


import android.content.Intent;
import android.os.Bundle;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.order.activity.OrderListActivty;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.views.ToastView;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;
    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        loadingDialog.show();
        api = WXAPIFactory.createWXAPI(this, WeiXinConstants.APP_ID);

        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        loadingDialog.show();
    }


    @Override
    public void onResp(BaseResp resp) {
        MyLog.debug(WXPayEntryActivity.class, ((PayResp) resp).returnKey + "----" + ((PayResp) resp).extData);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                new ToastView(this, "取消支付").show();
                if (GeekApplication.getType() == 1) {//充值
                    sendBroadcast(new Intent("com.geekmall.charge"));
                } else if (GeekApplication.getType() == 2) {//升级
                    sendBroadcast(new Intent("com.geekmall.upgrade"));
                } else {
                    sendBroadcast(new Intent("com.geekmall.orderpay"));
//                    startActivity(new Intent(this, OrderListActivty.class));
                }
            } else if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                new ToastView(this, "付款成功").show();
                if (GeekApplication.getType() == 2) {

                } else {
                    Intent intent = new Intent("com.geekmall.orderpay");
                    intent.putExtra("wexin","orderpay");
                    sendBroadcast(intent);
                }

            }
            loadingDialog.dismiss();
            finish();
        }
    }


}