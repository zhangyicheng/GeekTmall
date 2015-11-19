package com.geek.geekmall.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.OrderProduct;
import com.geek.geekmall.bean.ReFund;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.ReFundData;
import com.geek.geekmall.profile.activity.ApplyActivity;
import com.geek.geekmall.utils.TimeUtils;
import com.geek.geekmall.views.ToastView;

/**
 * Created by apple on 8/10/15.
 */
public class CustomerServiceActivity extends BaseActivity implements View.OnClickListener {
    private TextView mBackView;
    private OrderProduct mOrder;
    private TextView mTimeView;
    private TextView mContentView;
    private Button button1;
    private Button button2;
    private TextView mStatusView;
    private ReFund mReFund;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_service_layout);
        mOrder = (OrderProduct) getIntent().getSerializableExtra("order");
        init();
        services();
    }

    private void init() {
        mBackView = (TextView) findViewById(R.id.back);
        mBackView.setOnClickListener(this);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        mTimeView = (TextView) findViewById(R.id.time);
        mContentView = (TextView) findViewById(R.id.content);

        mStatusView = (TextView) findViewById(R.id.status);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    private void services() {
        loadingDialog.show();
        APIControl.getInstance().customerServices(this, mOrder.getGoodsOrderId(), new DataResponseListener<ReFundData>() {
            @Override
            public void onResponse(ReFundData commonData) {
                loadingDialog.dismiss();
                if (commonData.getStatus() == 200) {
                    status = commonData.getData().getStatus();
                    mReFund = commonData.getData();
                    mTimeView.setText(TimeUtils.getTime(commonData.getData().getDate()));
                    mContentView.setText(commonData.getData().getMessage());
                    mStatusView.setText(commonData.getData().getRefundStatus());
                    if (commonData.getData().getStatus() == 1) {
                        button2.setVisibility(View.GONE);
                    } else if (commonData.getData().getStatus() == 2) {
                        button2.setText("修改申请");
                    } else if (commonData.getData().getStatus() == 3) {
                        button2.setText("填写订单号");
                    } else if(commonData.getData().getStatus() == 4){
                        button1.setVisibility(View.GONE);
                        button2.setVisibility(View.GONE);
                    } else if(commonData.getData().getStatus() == 5 || commonData.getData().getStatus() == 6){
                        button1.setVisibility(View.GONE);
                        button2.setVisibility(View.GONE);

                    } else {
                        button1.setVisibility(View.GONE);
                        button2.setVisibility(View.GONE);

                    }
                }

            }
        }, errorListener(""));
    }

    private void cancel() {
        APIControl.getInstance().cancelService(this, mReFund.getId(), new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {
                if (commonData.getStatus() == 200) {
                    new ToastView(CustomerServiceActivity.this, "取消成功").show();
                    finish();
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
                finish();
                break;
            case R.id.button1:
                cancel();

                break;
            case R.id.button2:
                if (status == 2) {
                    intent.setClass(this, ApplyActivity.class);
                    intent.putExtra("refund", mReFund);
                    intent.putExtra("orderProduct",mOrder);
                    startActivity(intent);
                } else if (status == 3) {
                    intent.setClass(this, AddLogisticActivity.class);
                    intent.putExtra("refund", mReFund);
                    startActivity(intent);
                }
                break;

            default:
                break;
        }
    }

}
