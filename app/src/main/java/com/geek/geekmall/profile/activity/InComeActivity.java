package com.geek.geekmall.profile.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Income;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.IncomePageData;
import com.geek.geekmall.profile.adapter.TimeLineAdapter;
import com.geek.geekmall.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by apple on 5/26/15.
 */
public class InComeActivity extends BaseActivity {
    private StickyListHeadersListView mListView;
    private TimeLineAdapter mAdapter;
    private String userId = "";
    private String token = "";
    private int mCurrentPage = 1;
    private List<Income> mIncomes;
    private ImageView mProgress;
    AnimationDrawable drawable;
    private String agentId = "";
    private TextView mBackView;
    private User user;
    private TextView mDateChoose;
    private LinearLayout mDateChooseLayout;
    private CustomerDatePickerDialog mDialog;
    private TextView mTotalIncome;
    private TextView mTotaloutCome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_activity);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
            agentId = GeekApplication.getAgentInfo().getId();
        }

        initView();
    }

    private void initView() {
        mTotalIncome = (TextView) findViewById(R.id.total_income);
        mTotaloutCome = (TextView) findViewById(R.id.total_outcome);
        mDateChooseLayout = (LinearLayout) findViewById(R.id.time_choose_layout);
        mDateChoose = (TextView) findViewById(R.id.time_choose);
        mBackView = (TextView) findViewById(R.id.back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView = (StickyListHeadersListView) findViewById(R.id.list_view);
        mAdapter = new TimeLineAdapter(this);
        mDateChooseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new CustomerDatePickerDialog(InComeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        mDateChoose.setText(TimeUtils.getTime(calendar.getTimeInMillis(), dateFormat));
                        getIncome(TimeUtils.getTime(calendar.getTimeInMillis(), dateFormat));
                        mDialog.dismiss();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                mDialog.show();
                DatePicker dp = findDatePicker((ViewGroup) mDialog.getWindow().getDecorView());
                if (dp != null) {
                    ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
                }
            }
        });
        mListView.setAdapter(mAdapter);

        mAdapter = new TimeLineAdapter(this);
        mListView.setAdapter(mAdapter);
        mProgress = (ImageView) findViewById(R.id.progress);
        drawable = (AnimationDrawable) mProgress.getDrawable();
        drawable.start();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        mDateChoose.setText(TimeUtils.getTime(System.currentTimeMillis(), dateFormat));
        getIncome(TimeUtils.getTime(System.currentTimeMillis(), dateFormat));

//        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                mCurrentPage = 1;
//                getIncome();
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//                mCurrentPage++;
//                getIncome();
//            }
//        });
    }

    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    private void getIncome(String date) {
        APIControl.getInstance().totalIncomeCustomer(this, userId, token, date, agentId, mCurrentPage, SIZE,
                new DataResponseListener<IncomePageData>() {
                    @Override
                    public void onResponse(IncomePageData userPageData) {
                        drawable.stop();
                        mProgress.setVisibility(View.GONE);
                        if (userPageData.getStatus() == 200) {
                            if (!TextUtils.isEmpty(userPageData.getData().getTotalIncome().getAllTrueCutPrice())) {
                                mTotalIncome.setText(userPageData.getData().getTotalIncome().getAllTrueCutPrice());
                            }
                            if (!TextUtils.isEmpty(userPageData.getData().getTotalIncome().getAllFalseCutPrice())) {
                                mTotaloutCome.setText(userPageData.getData().getTotalIncome().getAllFalseCutPrice());
                            }

                            if (mCurrentPage == 1) {
                                mIncomes = userPageData.getData().getMonthlyIncome();
                            } else {
                                mIncomes.addAll(userPageData.getData().getMonthlyIncome());
                            }
                            mAdapter.setmInComes(mIncomes);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }, errorListener(URLs.FIND_AGENT_URL));
    }

    class CustomerDatePickerDialog extends DatePickerDialog {
        public CustomerDatePickerDialog(Context context,
                                        OnDateSetListener callBack, int year, int monthOfYear,
                                        int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int month, int day) {
            super.onDateChanged(view, year, month, day);
//            mDialog.setTitle(year + "年" + (month + 1) + "月");
        }
    }
}
