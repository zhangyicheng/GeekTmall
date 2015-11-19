package com.geek.geekmall.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.AgentInfoData;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.views.ToastView;

/**
 * Created by apple on 5/25/15.
 */
public class CashActivity extends BaseActivity {
    private Button mCommitButton;
    private TextView mRemainView;
    private EditText mMoneyView;
    private TextView mBackView;
    private String userId = "";
    private TextView mMyMoneyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_activity);
        initView();
        if (GeekApplication.getUser() != null) {
            userId = GeekApplication.getUser().getId();
        }
    }

    private void initView() {
        mRemainView = (TextView) findViewById(R.id.remain);
        mMoneyView = (EditText) findViewById(R.id.money);
        mMyMoneyView = (TextView) findViewById(R.id.my_money);
        mCommitButton = (Button) findViewById(R.id.commit);
        mBackView = (TextView) findViewById(R.id.back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GeekApplication.getAgentInfo().getIsCheck() == 1) {
                    withDraw();
                } else {
                    Intent intent = new Intent(CashActivity.this, AgencyAdentityThree.class);
                    intent.putExtra("agent", GeekApplication.getAgentInfo());
                    startActivityForResult(intent, 1001);
                }

            }
        });
        if (GeekApplication.getAgentInfo() != null) {
            if (!TextUtils.isEmpty(GeekApplication.getAgentInfo().getAllTruePrice())) {
                mRemainView.setText(GeekApplication.getAgentInfo().getAllTruePrice() + "元");
            } else {
                mRemainView.setText("0.0元");
            }
        } else {
            mRemainView.setText("0.0元");
        }
        mMyMoneyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CashActivity.this, MoneyActivity.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                findAgentById();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void withDraw() {
        APIControl.getInstance().withDraw(this, userId, mMoneyView.getText().toString(),
                new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        if (commonData.getStatus() == 200) {
                            new ToastView(CashActivity.this, "提现成功").show();
                        } else {
                            new ToastView(CashActivity.this, commonData.getErrorMsg()).show();
                        }

                    }
                }, errorListener(""));
    }

    private void findAgentById() {
        loadingDialog.show();
        APIControl.getInstance().findAgentById(this, userId, new DataResponseListener<AgentInfoData>() {
            @Override
            public void onResponse(AgentInfoData commonData) {
                loadingDialog.dismiss();
                if (commonData.getData() != null) {
                    GeekApplication.setAgentInfo(commonData.getData());
                }
            }
        }, errorListener(""));
    }
}
