package com.geek.geekmall.profile.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.UserData;
import com.geek.geekmall.views.ToastView;

/**
 * 我的余额
 * Created by apple on 8/3/15.
 */
public class MoneyActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup mGroup;
    private Button mCommitButton;
    private int money;
    private TextView mBackView;
    private TextView mMoneyView;
    private User user;
    private String userId = "";
    private String token = "";
private TextView mRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.money_bag_layout);

        init();
        registerBroadCast();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
            getHomeMessage();
        }
    }

    private void getHomeMessage() {
        loadingDialog.show();
        APIControl.getInstance().getHomeMessage(this, userId, token, new DataResponseListener<UserData>() {
            @Override
            public void onResponse(UserData userData) {
                loadingDialog.dismiss();
//                GeekApplication.setOrderInfo(userData.getData().getOrderInfo());
//                GeekApplication.setAgentInfo(userData.getData().getAgentInfo());
                GeekApplication.setUserMoney(userData.getData().getUserMoney());
                mMoneyView.setText(userData.getData().getUserMoney().getBalance());
            }
        }, errorListener(URLs.HOME_MESSAGE_URL));
    }

    private void init() {
        mRefresh = (TextView) findViewById(R.id.refresh);
        mMoneyView= (TextView) findViewById(R.id.money);
        mBackView = (TextView) findViewById(R.id.back);
        mGroup = (RadioGroup) findViewById(R.id.group);
        mCommitButton = (Button) findViewById(R.id.charge);
        mCommitButton.setOnClickListener(this);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.one) {
                    money = 100;
                } else if (checkedId == R.id.two) {
                    money = 200;
                } else if (checkedId == R.id.five) {
                    money = 500;
                }
            }
        });
        mBackView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
                getHomeMessage();
                break;
            case R.id.charge:
//                alipaycharge();
//                wexincharge();
                if (money == 0) {
                    new ToastView(this, "请选择金额").show();
                    return;
                }
                Intent intent = new Intent(this, PayTypeActivity.class);
                intent.putExtra("money", money);
                intent.putExtra("type", 1);

                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;

            default:
                break;
        }
    }
    private MyBrooadCast mMyBroadCast;

    private class MyBrooadCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.geekmall.charge".equals(intent.getAction())){
                finish();
            }

        }
    }

    private void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.geektmall.charge");

        mMyBroadCast = new MyBrooadCast();
        registerReceiver(mMyBroadCast, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMyBroadCast);
        super.onDestroy();
    }
}
