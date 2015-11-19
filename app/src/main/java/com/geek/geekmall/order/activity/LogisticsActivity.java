package com.geek.geekmall.order.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Logistics;
import com.geek.geekmall.order.adapter.LogisticsAdapter;
import com.geek.geekmall.views.ListViewForScrollView;

/**
 * 物流信息界面
 * Created by apple on 4/22/15.
 */
public class LogisticsActivity extends BaseActivity {
    private TextView mNameView;
    private TextView mNumberView;
    private TextView mBackView;
    private ListViewForScrollView mListView;
    private LogisticsAdapter mAdapter;
    private Logistics mLogistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logistics_layout);
        mLogistics = (Logistics) getIntent().getSerializableExtra("logistics");

        init();

    }

    private void init() {
        mListView = (ListViewForScrollView) findViewById(R.id.list_view);
        mNameView = (TextView) findViewById(R.id.name);
        mNumberView = (TextView) findViewById(R.id.number);
        mBackView = (TextView) findViewById(R.id.back);
        mAdapter = new LogisticsAdapter(this);
        mAdapter.setLogisticsDatas(mLogistics.getLogisticsData());
        mListView.setAdapter(mAdapter);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mNameView.setText(mLogistics.getLogisticsName());
        mNumberView.setText(mLogistics.getExpressNo());
    }


}
