package com.geek.geekmall.profile.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.AgentInfo;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.AgentSideBarData;
import com.geek.geekmall.profile.activity.CashActivity;
import com.geek.geekmall.profile.activity.CustomerActivity;
import com.geek.geekmall.profile.activity.InComeActivity;
import com.geek.geekmall.profile.activity.ProfileMainActivity;
import com.geek.geekmall.profile.activity.SalesRankActivity;
import com.geek.geekmall.register.LoginActivity;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;

/**
 * Created by apple on 4/22/15.
 */
public class ProfileSlideLayout extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private Intent mIntent;
    private TextView mCustomerView;
    private TextView mSalesTotalView;
    private TextView mInComeView;
    private TextView mCashView;

    private TextView mRaiseCustomerView;
    private TextView mRaiseSalesTotalView;
    private TextView mRaiseInComeView;
    private TextView mRaiseCashView;

    public ProfileSlideLayout(Context context) {
        this(context, null);
    }

    public ProfileSlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileSlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_profile_slide, this);
        init(view);
    }

    private ImageView mAvatarImage;
    private RelativeLayout mCustomerLayout;
    private RelativeLayout mSalesLayout;
    private RelativeLayout mIncomeLayout;
    private RelativeLayout mCashLayout;
    private TextView mLeverView;

    private void init(View view) {

        mRaiseCustomerView = (TextView) view.findViewById(R.id.raise_number);
        mRaiseSalesTotalView = (TextView) view.findViewById(R.id.raise_total);
        mRaiseInComeView = (TextView) view.findViewById(R.id.raise_income);
        mRaiseCashView = (TextView) view.findViewById(R.id.raise_cash);

        mLeverView = (TextView) view.findViewById(R.id.lever);
        this.mAvatarImage = ((ImageView) view.findViewById(R.id.round_pic));
        this.mCustomerLayout = ((RelativeLayout) view.findViewById(R.id.customer_layout));
        this.mSalesLayout = ((RelativeLayout) view.findViewById(R.id.sales_layout));
        this.mIncomeLayout = ((RelativeLayout) view.findViewById(R.id.income_layout));
        this.mCashLayout = ((RelativeLayout) view.findViewById(R.id.cash_layout));
        mCustomerView = (TextView) view.findViewById(R.id.customer_number);
        mSalesTotalView = (TextView) view.findViewById(R.id.sales_total);
        mInComeView = (TextView) view.findViewById(R.id.income_total);
        mCashView = (TextView) view.findViewById(R.id.cash_can);
        mAvatarImage.setOnClickListener(this);
        this.mCustomerLayout.setOnClickListener(this);
        this.mSalesLayout.setOnClickListener(this);
        this.mIncomeLayout.setOnClickListener(this);
        this.mCashLayout.setOnClickListener(this);
        mCustomerView.setText(String.format(getResources().getString(R.string.left_customer), 0));
        mSalesTotalView.setText(String.format(getResources().getString(R.string.left_sales_total), 0));
        mInComeView.setText(String.format(getResources().getString(R.string.left_income), 0));
        mCashView.setText(String.format(getResources().getString(R.string.left_cash), 0));
//        setData(0, 0, 0, 0);
    }

    public void setData(AgentInfo agentInfo) {
        if (agentInfo != null) {
            mCustomerView.setText(String.format(getResources().getString(R.string.left_customer), agentInfo.getCustomerNumber()));
            mSalesTotalView.setText(String.format(getResources().getString(R.string.left_sales_total), agentInfo.getTotalPrice()));
            mInComeView.setText(String.format(getResources().getString(R.string.left_income), agentInfo.getAllPrice()));
            mCashView.setText(String.format(getResources().getString(R.string.left_cash), agentInfo.getAllTruePrice()));
            if (agentInfo.getLevel() == 1) {
                mLeverView.setText("甩手小掌柜");
            } else if (agentInfo.getLevel() == 2) {
                mLeverView.setText("签约掌柜");
            } else if (agentInfo.getLevel() == 3) {
                mLeverView.setText("大掌柜");
            } else if (agentInfo.getLevel() == 4) {
                mLeverView.setText("代理商");
            }

            if (!TextUtils.isEmpty(GeekApplication.getUser().getImgUrl())) {
                String url = agentInfo.getImgUrl();
                if (!url.startsWith("http://")) {
                    url = URLs.IMAGE_URL + GeekApplication.getUser().getImgUrl();
                }
                ImageLoader.getInstance(mContext).getPicasso().load(url)
                        .placeholder(R.drawable.avatar_default)
                        .resize(DensityUtils.dp2px(mContext, 95), DensityUtils.dp2px(mContext, 95))
                        .transform(ImageLoader.getInstance(mContext).new RoundedCornersTransformation(120))
                        .into(mAvatarImage);
            }
            getSideBarData(GeekApplication.getUser().getId());

        } else {
            mAvatarImage.setImageResource(R.drawable.avatar_default);
            mCustomerView.setText(String.format(getResources().getString(R.string.left_customer), 0));
            mSalesTotalView.setText(String.format(getResources().getString(R.string.left_sales_total), 0));
            mInComeView.setText(String.format(getResources().getString(R.string.left_income), 0));
            mCashView.setText(String.format(getResources().getString(R.string.left_cash), 0));
        }

    }

    private void getSideBarData(String userId) {
        APIControl.getInstance().agentSidebar(mContext, userId, new DataResponseListener<AgentSideBarData>() {
            @Override
            public void onResponse(AgentSideBarData agentSideBarData) {
                mRaiseCustomerView.setText("+" + (agentSideBarData.getData().getCustomerNumber() - APIControl.getInstance().getSideBar(mContext).getCustomerNumber()));
                mRaiseSalesTotalView.setText("+" + (float)(Math.round((agentSideBarData.getData().getTotalPrice() - APIControl.getInstance().getSideBar(mContext).getTotalPrice())*100))/100);

                mRaiseInComeView.setText("+" + (float)(Math.round((agentSideBarData.getData().getAllPrice() - APIControl.getInstance().getSideBar(mContext).getAllPrice())*100))/100);

                mRaiseCashView.setText("+" + (float)(Math.round((agentSideBarData.getData().getAllTruePrice() - APIControl.getInstance().getSideBar(mContext).getAllTruePrice())*100))/100);
                if (agentSideBarData.getData().getLevel() == 1) {
                    mLeverView.setText("甩手小掌柜");
                } else if (agentSideBarData.getData().getLevel() == 2) {
                    mLeverView.setText("签约掌柜");
                } else if (agentSideBarData.getData().getLevel() == 3) {
                    mLeverView.setText("大掌柜");
                } else if (agentSideBarData.getData().getLevel() == 4) {
                    mLeverView.setText("代理商");
                }
                APIControl.getInstance().saveSideBar(mContext, agentSideBarData.getData());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    public void setmIntent(Intent mIntent) {
        this.mIntent = mIntent;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.customer_layout:
                if (GeekApplication.isLogin()) {
                    mContext.startActivity(new Intent(mContext, CustomerActivity.class));
                } else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
//                mContext.sendBroadcast(new Intent("com.geek.geekmall.action.customer_layout"));
                break;
            case R.id.sales_layout:
                if (GeekApplication.isLogin()) {

                    mContext.startActivity(new Intent(mContext, SalesRankActivity.class));
                } else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
//                mContext.sendBroadcast(new Intent("com.geek.geekmall.action.sales_layout"));
                break;
            case R.id.income_layout:
                if (GeekApplication.isLogin()) {
                    mContext.startActivity(new Intent(mContext, InComeActivity.class));
                } else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }

//                mContext.sendBroadcast(new Intent("com.geek.geekmall.action.income_layout"));
                break;
            case R.id.cash_layout:
                if (GeekApplication.isLogin()) {
                    mContext.startActivity(new Intent(mContext, CashActivity.class));
                } else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
//                mContext.sendBroadcast(new Intent("com.geek.geekmall.action.cash_layout"));
                break;
            case R.id.round_pic:
                if (GeekApplication.isLogin()) {
                    mContext.startActivity(new Intent(mContext, ProfileMainActivity.class));
                } else {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }
}
