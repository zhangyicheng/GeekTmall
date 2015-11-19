package com.geek.geekmall.order.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Order;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.OrderData;
import com.geek.geekmall.model.WXResultData;
import com.geek.geekmall.order.adapter.OrderAdapter;
import com.geek.geekmall.utils.wxutil.MD5;
import com.geek.geekmall.views.SureDialog;
import com.geek.geekmall.views.ToastView;
import com.geek.geekmall.wxapi.WeiXinConstants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by apple on 4/22/15.
 */
public class OrderListActivty extends BaseActivity implements OnClickListener {
    private PullToRefreshExpandableListView mListView;
    private OrderAdapter mAdapter;
    private TextView mBack;
    private User user;
    private String userId = "";
    private String token = "";
    private int mCurrentPage = 1;
    private List<Order> mOrders;
    private ImageView mProgress;
    AnimationDrawable drawable;
    private int order = 0;
    private SureDialog mDialog;
    private IWXAPI api;
    PayReq req;
    StringBuffer sb;
    private TextView mTitleView;
    private LinearLayout mNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        //wexin
        api = WXAPIFactory.createWXAPI(this, WeiXinConstants.APP_ID, false);
        api.registerApp(WeiXinConstants.APP_ID);
        order = getIntent().getIntExtra("order", 0);
        req = new PayReq();
        sb = new StringBuffer();
        init();

    }

    @Override
    protected void onResume() {
        getOrderList();
        super.onResume();
    }

    private void init() {
        mTitleView = (TextView) findViewById(R.id.title);
        if (order == 8) {
            mTitleView.setText("售后");
        } else if (order == 1) {
            mTitleView.setText("待付款");
        } else if (order == 3) {
            mTitleView.setText("待收货");
        } else if (order == 2) {
            mTitleView.setText("待发货");
        } else if (order == 7) {
            mTitleView.setText("待评论");
        }
        mNoData = (LinearLayout) findViewById(R.id.no_data);
        mProgress = (ImageView) findViewById(R.id.progress);
        drawable = (AnimationDrawable) mProgress.getDrawable();
        drawable.start();
        mListView = (PullToRefreshExpandableListView) findViewById(R.id.list_view);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mBack = (TextView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mAdapter = new OrderAdapter(this);
        mListView.getRefreshableView().setAdapter(mAdapter);
        mListView.getRefreshableView().setGroupIndicator(null);
        mListView.getRefreshableView().setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                mCurrentPage = 1;
                getOrderList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                mCurrentPage++;
                getOrderList();
            }
        });
        mListView.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (order == 8) {
                    Intent intent = new Intent(OrderListActivty.this, CustomerServiceActivity.class);
                    intent.putExtra("order", mAdapter.getGroup(groupPosition).getGoodsList().get(childPosition));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(OrderListActivty.this, OrderDetailActivity.class);
                    intent.putExtra("order", mAdapter.getGroup(groupPosition));
                    startActivity(intent);
                }

                return true;
            }
        });
//        mListView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(OrderListActivty.this, OrderDetailActivity.class);
//                intent.putExtra("order", mAdapter.getGroup(position));
//                startActivity(intent);
//            }
//        });

    }

    public void payOrder(String orderId, String type) {
        APIControl.getInstance().payOrder(this, userId, orderId, type, new DataResponseListener<WXResultData>() {
            @Override
            public void onResponse(WXResultData wxResultData) {
                if (wxResultData.getStatus() == 200) {
                    genPayReq(wxResultData.getData().getPrepayid());
                } else {
                    new ToastView(OrderListActivty.this, wxResultData.getErrorMsg()).show();
                }
            }
        }, errorListener(""));
    }

    public void deleteOrder(final String orderId) {
        mDialog = new SureDialog(OrderListActivty.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                loadingDialog.show();
                APIControl.getInstance().deleteOrder(OrderListActivty.this, userId, token, orderId, new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        loadingDialog.dismiss();
                        if (commonData.getStatus() == 200) {
                            new ToastView(OrderListActivty.this, "删除成功").show();
                            getOrderList();
                        } else {
                            new ToastView(OrderListActivty.this, "删除失败").show();
                        }
                    }
                }, errorListener(""));
            }
        });
        mDialog.show();
        mDialog.setTitle("你确定要删除订单吗");

    }

    public void sureRecive(final String orderId) {
        mDialog = new SureDialog(OrderListActivty.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                cofirmReceive(orderId);
            }
        });
        mDialog.show();
        mDialog.setTitle("请确认收货以后再点击确认收货");
    }

    public void cancelOrder(final String orderId) {
        mDialog = new SureDialog(OrderListActivty.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                loadingDialog.show();
                APIControl.getInstance().cancelOrder(OrderListActivty.this, userId, token, orderId, new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        loadingDialog.dismiss();
                        if (commonData.getStatus() == 200) {
                            new ToastView(OrderListActivty.this, "取消成功").show();
                            getOrderList();
                        } else {
                            new ToastView(OrderListActivty.this, "取消失败").show();
                        }
                    }
                }, errorListener(""));
            }
        });
        mDialog.show();
        mDialog.setTitle("你确定要取消订单吗");

    }

    private void cofirmReceive(String orderId) {
        loadingDialog.show();
        APIControl.getInstance().confirmReceive(this, orderId, new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {
                loadingDialog.dismiss();
                if (commonData.getStatus() == 200) {
                    getOrderList();
                    new ToastView(OrderListActivty.this, "确认成功").show();
                } else {
                    new ToastView(OrderListActivty.this, commonData.getErrorMsg()).show();
                }
            }
        }, errorListener(""));
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

    private void sendPayReq() {
        api.registerApp(WeiXinConstants.APP_ID);
        api.sendReq(req);
        loadingDialog.dismiss();
    }

    private void getOrderList() {
        loadingDialog.show();
        APIControl.getInstance().getOrdeList(this, userId, token, mCurrentPage, SIZE, order,
                new DataResponseListener<OrderData>() {
                    @Override
                    public void onResponse(OrderData orderData) {
                        loadingDialog.dismiss();

                        mListView.onRefreshComplete();
                        drawable.stop();
                        mProgress.setVisibility(View.GONE);
                        if (orderData.getStatus() == 200) {
                            mListView.setVisibility(View.VISIBLE);
                            if (orderData.getData().getDataList() != null && orderData.getData().getDataList().size() != 0) {
                                if (mCurrentPage == 1) {
                                    mOrders = orderData.getData().getDataList();
                                } else {
                                    mOrders.addAll(orderData.getData().getDataList());
                                }
                                mAdapter.setOrder(order);
                                mAdapter.setmOrders(mOrders);
                                for (int i = 0; i < mOrders.size(); i++) {
                                    mListView.getRefreshableView().expandGroup(i);
                                }
                                mAdapter.notifyDataSetChanged();
                            } else if (mCurrentPage == 1) {
                                mListView.setVisibility(View.GONE);
                                mNoData.setVisibility(View.VISIBLE);
                            }


                        } else if (mCurrentPage == 1) {
                            mListView.setVisibility(View.GONE);
                            mNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }, errorListener(URLs.GET_ORDERLIST_URL));
    }

    @Override
    protected Response.ErrorListener errorListener(String url) {
        drawable.stop();
        mProgress.setVisibility(View.GONE);
        return super.errorListener("");
    }

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
            default:
                break;
        }

    }
}
