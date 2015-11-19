package com.geek.geekmall.profile.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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
import com.geek.geekmall.profile.adapter.RankAdapter;
import com.geek.geekmall.utils.MyLog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * 销售总额排名
 * Created by apple on 5/21/15.
 */
public class SalesRankActivity extends BaseActivity {
    private PullToRefreshListView mListView;
    private RankAdapter mAdapter;
    private RadioGroup mGroup;
    private User user;
    private String userId = "";
    private String token = "";
    private int mCurrentPage = 1;
    private List<User> mUsers;
    private ImageView mProgress;
    AnimationDrawable drawable;
    private TextView mBackView;
    private String agentId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_rank);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        if (GeekApplication.getAgentInfo() != null) {
            agentId = GeekApplication.getAgentInfo().getId();
        }
        mBackView = (TextView) findViewById(R.id.back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView = (PullToRefreshListView) findViewById(R.id.sales_list);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mAdapter = new RankAdapter(this);
        mListView.setAdapter(mAdapter);
        mProgress = (ImageView) findViewById(R.id.progress);
        drawable = (AnimationDrawable) mProgress.getDrawable();
        drawable.start();
        getRank();
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
    }

    private void getRank() {
        loadingDialog.show();
        APIControl.getInstance().rankSales(this, userId, token, agentId, mCurrentPage, SIZE,
                new DataResponseListener<AgentRankData>() {
                    @Override
                    public void onResponse(AgentRankData userPageData) {
                        mListView.onRefreshComplete();
                        MyLog.debug(SalesRankActivity.class, "++++++++++++++");
                        loadingDialog.dismiss();
                        drawable.stop();
                        mProgress.setVisibility(View.GONE);
                        if (userPageData.getStatus() == 200) {
                            if (mCurrentPage == 1) {
                                mUsers = userPageData.getData().getPager().getDataList();
                            } else if (userPageData.getData().getPager().getDataList() != null) {
                                mUsers.addAll(userPageData.getData().getPager().getDataList());
                            }
                            mAdapter.setmUsers(mUsers);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }, errorListener(URLs.FIND_SALES_URL));
    }

    @Override
    protected Response.ErrorListener errorListener(String url) {
//        mListView.onRefreshComplete();
        return super.errorListener(url);
    }
}
