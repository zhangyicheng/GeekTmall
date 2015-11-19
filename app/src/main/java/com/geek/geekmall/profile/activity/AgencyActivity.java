package com.geek.geekmall.profile.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.AgentInfo;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.AgentInfoData;
import com.geek.geekmall.utils.TimeUtils;
import com.geek.geekmall.views.CountView;

import java.util.Date;

/**
 * Created by apple on 5/21/15.
 */
public class AgencyActivity extends BaseActivity implements View.OnClickListener {
    private Button mIdentityView;
    private ImageView mCommonView;
    private ImageView mGoldenView;
    private ImageView mDiamondView;
    private Button mCommitButton;
    private Button mAgencyIntro;
    public static final int COMMMON = 1;
    public static final int GOLDEN = 2;
    public static final int DIAMOND = 3;
    private LinearLayout mCustomerLayout;
    private LinearLayout mSalesTotalLayout;
    private LinearLayout mIncomeTotalLayout;
    private TextView mCustomerNumView;
    private TextView mSalesNumView;
    private TextView mIncomeNumView;
    private TextView mBack;
    private CountView mCash;
    private TextView mOverTime;
    private TextView mLeverView;
    private AgentInfo mAgentInfo;
    private String userId = "";
    private TextView mRefresh;
    private Button mMoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency);
        initView();
        if (GeekApplication.getUser() != null) {
            userId = GeekApplication.getUser().getId();
        }
        findAgentById();
        registerBroadCast();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        mOverTime = (TextView) findViewById(R.id.date);
        mMoreBtn = (Button) findViewById(R.id.more);
        mRefresh = (TextView) findViewById(R.id.refresh);
        mBack = (TextView) findViewById(R.id.back);
        mCustomerNumView = (TextView) findViewById(R.id.customer_number);
        mSalesNumView = (TextView) findViewById(R.id.sales_total);
        mIncomeNumView = (TextView) findViewById(R.id.income_total);
        mIdentityView = (Button) findViewById(R.id.identity);
        mAgencyIntro = (Button) findViewById(R.id.agency_intro);
        mCash = (CountView) findViewById(R.id.cash);
        mLeverView = (TextView) findViewById(R.id.lever);
        mCommonView = (ImageView) findViewById(R.id.common);
        mGoldenView = (ImageView) findViewById(R.id.golden);
        mDiamondView = (ImageView) findViewById(R.id.diamend);
        mCommitButton = (Button) findViewById(R.id.commit);
        mCustomerLayout = (LinearLayout) findViewById(R.id.customer_layout);
        mSalesTotalLayout = (LinearLayout) findViewById(R.id.sales_total_layout);
        mIncomeTotalLayout = (LinearLayout) findViewById(R.id.income_total_layout);
        mCustomerLayout.setOnClickListener(this);
        mSalesTotalLayout.setOnClickListener(this);
        mIncomeTotalLayout.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mAgencyIntro.setOnClickListener(this);
        mDiamondView.setOnClickListener(this);
        mGoldenView.setOnClickListener(this);
        mCommonView.setOnClickListener(this);
        mIdentityView.setOnClickListener(this);
        mCommitButton.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
        mMoreBtn.setOnClickListener(this);
//        mCustomerNumView.setText(GeekApplication.getAgentInfo().getCustomerNumber() + "人");
//        mSalesNumView.setText(GeekApplication.getAgentInfo().getTotalPrice() + "元");
//        mIncomeNumView.setText(GeekApplication.getAgentInfo().getAllPrice() + "元");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more:
                Intent intent2 = new Intent(this, UpgradeActivity.class);
                startActivity(intent2);
                break;
            case R.id.refresh:
                findAgentById();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.customer_layout:
                startActivity(new Intent(this, CustomerActivity.class));
                break;
            case R.id.sales_total_layout:
                startActivity(new Intent(this, SalesRankActivity.class));
                break;
            case R.id.income_total_layout:
                startActivity(new Intent(this, InComeActivity.class));
                break;
            case R.id.commit:
                startActivity(new Intent(this, CashActivity.class));
                break;
            case R.id.identity:
                Intent intent = new Intent(this, AgencyAdentityThree.class);
                intent.putExtra("agent", mAgentInfo);
                startActivityForResult(intent, 1001);
                break;
            case R.id.agency_intro:
                startActivity(new Intent(this, AgencyIntro.class));
                break;

//            case R.id.common:
//                if (GeekApplication.getAgentInfo().getIsCheck() != 1) {
//                    Intent intent1 = new Intent(this, AgencyAdentityThree.class);
//                    intent1.putExtra("agent", mAgentInfo);
//                    startActivity(intent1);
//                } else {
//                    Intent intent2 = new Intent(this, UpgradeActivity.class);
//                    intent2.putExtra("level", COMMMON);
//                    startActivity(intent2);
//                }
//
//                break;
//            case R.id.golden:
//                if (GeekApplication.getAgentInfo().getIsCheck() != 1) {
//                    Intent intent1 = new Intent(this, AgencyAdentityThree.class);
//                    intent1.putExtra("agent", mAgentInfo);
//                    startActivity(intent1);
//                } else {
//                    Intent intent2 = new Intent(this, UpgradeActivity.class);
//                    intent2.putExtra("level", GOLDEN);
//                    startActivity(intent2);
//                }
//
//                break;
//            case R.id.diamend:
//                if (GeekApplication.getAgentInfo().getIsCheck() != 1) {
//                    Intent intent1 = new Intent(this, AgencyAdentityThree.class);
//                    intent1.putExtra("agent", mAgentInfo);
//                    startActivity(intent1);
//                } else {
//                    Intent intent3 = new Intent(this, UpgradeActivity.class);
//                    intent3.putExtra("level", DIAMOND);
//                    startActivity(intent3);
//                }
//
//                break;
            default:
                break;
        }
    }

    private void findAgentById() {
        loadingDialog.show();
        APIControl.getInstance().findAgentById(this, userId, new DataResponseListener<AgentInfoData>() {
            @Override
            public void onResponse(AgentInfoData commonData) {
                loadingDialog.dismiss();
                if (commonData.getData() != null) {
                    GeekApplication.setAgentInfo(commonData.getData());
                    mAgentInfo = commonData.getData();
                    mCustomerNumView.setText(commonData.getData().getCustomerNumber() + "");
                    mSalesNumView.setText(commonData.getData().getTotalPrice());
                    mIncomeNumView.setText(commonData.getData().getAllPrice());
                    if (!TextUtils.isEmpty(commonData.getData().getAllTruePrice())){
                        mCash.showNumberWithAnimation(Float.valueOf(commonData.getData().getAllTruePrice()));
                    }


                        if (commonData.getData().getLevel() == 1) {
                            mLeverView.setText("甩手小掌柜");
                        } else if (commonData.getData().getLevel() == 2) {
                            mLeverView.setText("签约掌柜");
                        } else if (commonData.getData().getLevel() == 3) {
                            mLeverView.setText("大掌柜");
                        } else if (commonData.getData().getLevel() == 4) {
                            mLeverView.setText("代理商");
                        }

                    mOverTime.setText(TimeUtils.DATE_FORMAT_DATE.format(new Date(commonData.getData().getOverTime())) + "到期");
                }
            }
        }, errorListener(""));
    }

    private void registerBroadCast() {
        mBroadCast = new RegisterBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.geekmall.charge");
        filter.addAction("com.geekmall.upgrade");
        filter.addAction("com.geekmall.auth");
        registerReceiver(mBroadCast, filter);
    }

    private RegisterBroadCast mBroadCast;

    private class RegisterBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.geekmall.charge".equals(intent.getAction())) {


            } else if ("com.geekmall.upgrade".equals(intent.getAction())) {
                findAgentById();
            }
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadCast);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                findAgentById();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
