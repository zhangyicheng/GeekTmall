package com.geek.geekmall.order.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Logistics;
import com.geek.geekmall.bean.Order;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.LogisticsData;
import com.geek.geekmall.order.adapter.OrderDetailAdapter;
import com.geek.geekmall.utils.TimeUtils;
import com.geek.geekmall.views.ListViewForScrollView;
import com.geek.geekmall.views.SureDialog;
import com.geek.geekmall.views.ToastView;

/**
 * Created by apple on 4/22/15.
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {
    private OrderDetailAdapter mAdapter;
    private ListViewForScrollView mListView;
    private TextView mWuliuView;
    private TextView mNameView;
    private TextView mPhoneView;
    private TextView mAddressView;
    private TextView mWuliuTimeView;
    private TextView mStoreNameView;
    private TextView mOrderNumberView;
    private TextView mPayNumberView;
    private TextView mOrderTimeView;
    private Button button1;
    private Button button2;
    private Order mOrder;
    private TextView mBackView;
    private Logistics mLogistics;
    private RelativeLayout mLogisticsLayout;
    private LinearLayout mLayout;
    private String userId = "";
    private String token = "";
    private SureDialog mDialog;
private RelativeLayout mBottomLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        mOrder = (Order) getIntent().getSerializableExtra("order");
        if (GeekApplication.isLogin()) {
            userId = GeekApplication.getUser().getId();
            token = GeekApplication.getUser().getToken();
        }
        init();
        getOrderDetail();
        registerBroadCast();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void init() {
        mBottomLayout= (RelativeLayout) findViewById(R.id.bottom);
        mLayout = (LinearLayout) findViewById(R.id.pay_order_number_layout);
        mLogisticsLayout = (RelativeLayout) findViewById(R.id.ogisticsl_layout);
        mBackView = (TextView) findViewById(R.id.back);
        mListView = (ListViewForScrollView) findViewById(R.id.products_list);
        mWuliuView = (TextView) findViewById(R.id.wuliu);
        mWuliuTimeView = (TextView) findViewById(R.id.wuliu_time);
        mNameView = (TextView) findViewById(R.id.name);
        mPhoneView = (TextView) findViewById(R.id.phone);
        mAddressView = (TextView) findViewById(R.id.address);
        mStoreNameView = (TextView) findViewById(R.id.store_name);
        mOrderNumberView = (TextView) findViewById(R.id.order_number);
        mPayNumberView = (TextView) findViewById(R.id.pay_order_number);
        mOrderTimeView = (TextView) findViewById(R.id.order_time);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new OrderDetailAdapter(this);
        mAdapter.setOrderProducts(mOrder.getGoodsList());
        mAdapter.setmOrder(mOrder);
        mListView.setAdapter(mAdapter);
        mLogisticsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this, LogisticsActivity.class);
                intent.putExtra("logistics", mLogistics);
                startActivity(intent);
            }
        });

        mOrderNumberView.setText(mOrder.getOrderNo());
//        mPayNumberView.setText(mOrder.getPayOrderNo());
        mOrderTimeView.setText(TimeUtils.getTime(mOrder.getCreateTime()));
        mStoreNameView.setText(mOrder.getStoreName());
        changeStatus();
    }

    private void getOrderDetail() {
        APIControl.getInstance().orderDetail(this, mOrder.getId(), new DataResponseListener<LogisticsData>() {
            @Override
            public void onResponse(LogisticsData data) {
                if (data.getStatus() == 200) {
                    mLogistics = data.getData();
                    if (mLogistics.getLogisticsData() != null && mLogistics.getLogisticsData().size() > 0) {
                        mWuliuView.setText(mLogistics.getLogisticsData().get(0).getContext());
                        mWuliuTimeView.setText(mLogistics.getLogisticsData().get(0).getTime());
                    }
                    mNameView.setText(mLogistics.getAddress().getContactName());
                    mAddressView.setText(mLogistics.getAddress().getAddress());
                    mPhoneView.setText(mLogistics.getAddress().getContactPhone());

                }
            }
        }, errorListener(""));
    }

    private void changeStatus() {
        if (mOrder.getPayStatus() == 1) {
            button1.setText("取消订单");
            button2.setText("付款");
        } else if (mOrder.getPayStatus() == 2) {
            button1.setText("未发货");
            button2.setText("已付款");
            mBottomLayout.setVisibility(View.GONE);
        } else if (mOrder.getPayStatus() == 3) {
            button1.setText("查看物流");
            button2.setText("确认收货");
        } else if (mOrder.getPayStatus() == 4) {
            button1.setText("删除订单");
            button2.setText("已完成");
            button2.setVisibility(View.GONE);
        } else if (mOrder.getPayStatus() == 5) {
            button1.setVisibility(View.GONE);
            button2.setText("已关闭");
        } else if (mOrder.getPayStatus() == 6) {
            button1.setVisibility(View.GONE);
            button2.setText("已关闭");
        } else if (mOrder.getPayStatus() == 7) {
            button1.setText("删除订单");
            button2.setText("评价订单");
        }
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
            case R.id.button1:
                if (mOrder.getPayStatus() == 1) {
                    cancelOrder(mOrder.getId());
                } else if (mOrder.getPayStatus() == 2) {

                } else if (mOrder.getPayStatus() == 3) {
                    intent.setClass(OrderDetailActivity.this, LogisticsActivity.class);
                    intent.putExtra("logistics", mLogistics);
                    startActivity(intent);
                } else if (mOrder.getPayStatus() == 4) {
                    deleteOrder(mOrder.getId());
                } else if (mOrder.getPayStatus() == 5) {

                } else if (mOrder.getPayStatus() == 7) {
                    deleteOrder(mOrder.getId());
                }
                break;
            case R.id.button2:
                if (mOrder.getPayStatus() == 1) {
                    intent.setClass(this,OrderpayActivity.class);
                    intent.putExtra("order", mOrder);
                    startActivity(intent);
                } else if (mOrder.getPayStatus() == 2) {

                } else if (mOrder.getPayStatus() == 3) {
                    confirmReceive(mOrder.getId());
                } else if (mOrder.getPayStatus() == 4) {
                    deleteOrder(mOrder.getId());
                } else if (mOrder.getPayStatus() == 5) {

                } else if (mOrder.getPayStatus() == 7) {
                    intent.setClass(this, CommentActivity.class);
                    intent.putExtra("order", mOrder);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    public void deleteOrder(final String orderId) {
        mDialog = new SureDialog(OrderDetailActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();

                APIControl.getInstance().deleteOrder(OrderDetailActivity.this, userId, token, orderId, new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        if (commonData.getStatus() ==200){
                            finish();
                            new ToastView(OrderDetailActivity.this,"删除成功").show();

                        } else {
                            new ToastView(OrderDetailActivity.this,"删除失败").show();
                        }
                    }
                },errorListener(""));
            }
        });
        mDialog.show();
        mDialog.setTitle("你确定要删除订单吗");

    }

    public void cancelOrder(final String orderId) {
        mDialog = new SureDialog(OrderDetailActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                APIControl.getInstance().cancelOrder(OrderDetailActivity.this, userId, token, orderId, new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        if (commonData.getStatus() == 200) {
                            finish();
                            new ToastView(OrderDetailActivity.this, "取消成功").show();

                        } else {
                            new ToastView(OrderDetailActivity.this, "取消失败").show();
                        }
                    }
                }, errorListener(""));
            }
        });
        mDialog.show();
        mDialog.setTitle("你确定要取消订单吗");

    }
    public void confirmReceive(String orderId){
        APIControl.getInstance().confirmReceive(this, orderId, new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {
                if (commonData.getStatus() == 200) {
                    getOrderDetail();
                    new ToastView(OrderDetailActivity.this, "确认成功").show();
                } else {
                    new ToastView(OrderDetailActivity.this, commonData.getErrorMsg()).show();
                }
            }
        }, errorListener(""));
    }

    private MyBrooadCast mMyBroadCast;

    private class MyBrooadCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.geektmall.pay.success".equals(intent.getAction())) {
                mOrder.setPayStatus(2);
                changeStatus();
//                mAdapter.setIsPay(true);
            } else if ("com.geektmall.apply.success".equals(intent.getAction())){
                String goodsOrderId = intent.getStringExtra("goodsOrderId");
                mAdapter.setGoodsOrderId(goodsOrderId);
                mAdapter.setIsApply(true);
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    private void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.geektmall.pay.success");
        filter.addAction("com.geektmall.apply.success");

        mMyBroadCast = new MyBrooadCast();
        registerReceiver(mMyBroadCast, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMyBroadCast);
        super.onDestroy();
    }
}
