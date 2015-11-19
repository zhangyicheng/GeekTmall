package com.geek.geekmall.profile.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.AgentRankData;
import com.geek.geekmall.model.UserOrder;
import com.geek.geekmall.profile.adapter.CustomerAdapter;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * Created by apple on 5/21/15.
 */
public class CustomerActivity extends BaseActivity {
    private PullToRefreshListView mListView;
    private CustomerAdapter mAdapter;
    private RadioGroup mGroup;
    private TextView mNameView;
    private TextView mRankView;
    private TextView mNumberView;
    private ImageView mImageView;
    private ImageView mLocationView;
    private User user;
    private String userId = "";
    private String token = "";
    private int mCurrentPage = 1;
    private List<User> mUsers;
    private ImageView mProgress;
    AnimationDrawable drawable;
    private TextView mBackView;
    private String agentId;
    private UserOrder mOrder;
    private boolean isLocation = false;
    private boolean isToFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        if (GeekApplication.getAgentInfo() != null) {
            agentId = GeekApplication.getAgentInfo().getId();
        }
        init();
        getRank();

    }

    private void init() {
        mLocationView = (ImageView) findViewById(R.id.location);
        mBackView = (TextView) findViewById(R.id.back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View view = getLayoutInflater().inflate(R.layout.customer_header, null);
        mNameView = (TextView) view.findViewById(R.id.name);
        mNumberView = (TextView) view.findViewById(R.id.number);
        mRankView = (TextView) view.findViewById(R.id.rank);
        mImageView = (ImageView) view.findViewById(R.id.avator);

        mListView = (PullToRefreshListView) findViewById(R.id.customer_list);
        mListView.getRefreshableView().addHeaderView(view);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new CustomerAdapter(this);
        mListView.setAdapter(mAdapter);
        mProgress = (ImageView) findViewById(R.id.progress);
        drawable = (AnimationDrawable) mProgress.getDrawable();
        drawable.start();
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mCurrentPage = 1;

                getRank();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mCurrentPage++;
                getRank();
            }
        });
        mLocationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrder != null) {
                    isLocation = true;
                    int order = Float.valueOf(mOrder.getOrder()).intValue() / SIZE;
                    mCurrentPage = (order == 1 ? order : order + 1);
                    getRank();
                }
            }
        });
    }

    private void getRank() {
        loadingDialog.show();
        APIControl.getInstance().rankCustomer(this, userId, token, agentId, mCurrentPage, SIZE,
                new DataResponseListener<AgentRankData>() {
                    @Override
                    public void onResponse(AgentRankData userPageData) {
                        mListView.onRefreshComplete();
                        drawable.stop();
                        loadingDialog.dismiss();
                        mProgress.setVisibility(View.GONE);
                        if (userPageData.getStatus() == 200 && userPageData.getData() != null) {
                            mOrder = userPageData.getData().getUserOrder();
                            if (mOrder != null) {
                                mNameView.setText(mOrder.getNickname());
                                mNumberView.setText(mOrder.getCustomerNumber() + "人");
                                mRankView.setText(Float.valueOf(mOrder.getOrder()).intValue() + "");
                                String url = mOrder.getUserPhoto();
                                if (!TextUtils.isEmpty(url)) {
                                    if (!url.startsWith("http://")) {
                                        url = URLs.IMAGE_URL + url;
                                    }
                                    ImageLoader.getInstance(CustomerActivity.this).getPicasso()
                                            .load(url)
                                            .resize(DensityUtils.dp2px(CustomerActivity.this, 40), DensityUtils.dp2px(CustomerActivity.this, 40))
                                            .transform(ImageLoader.getInstance(CustomerActivity.this).new RoundedCornersTransformation(120))
                                            .into(mImageView);
                                }
                            }
                            if (mCurrentPage == 1 || isLocation) {
                                mUsers = userPageData.getData().getPager().getDataList();
                            } else {
                                mUsers.addAll(userPageData.getData().getPager().getDataList());
                            }
                            mAdapter.setmUsers(mUsers);
                            mAdapter.notifyDataSetChanged();
                            if (isLocation) {
                                isLocation = false;
                                for (int i = 0; i < mUsers.size(); i++) {
                                    if (Float.valueOf(mOrder.getOrder()).intValue() == mUsers.get(i).getOrder()) {
                                        mListView.getRefreshableView().setSelection(i);
                                    }
                                }
                            }
                        }
                    }
                }, errorListener(""));
    }

    @Override
    protected Response.ErrorListener errorListener(String url) {
        return super.errorListener("");
    }
}
