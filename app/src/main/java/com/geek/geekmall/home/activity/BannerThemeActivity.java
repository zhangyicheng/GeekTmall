package com.geek.geekmall.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.adapter.HomeThemeAdapter;
import com.geek.geekmall.model.BannerTheme;
import com.geek.geekmall.views.ToastView;

/**
 * Created by apple on 9/14/15.
 */
public class BannerThemeActivity extends BaseActivity {
    private HomeThemeAdapter mAdapter;
    private String linkTypeId;
    private String type;
    private TextView mBackView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_theme);
        linkTypeId = getIntent().getStringExtra("linkTypeId");
        type = getIntent().getStringExtra("type");
        mAdapter = new HomeThemeAdapter(this);
        getBannerByType();
        initView();

    }

    private void initView() {
        mBackView = (TextView) findViewById(R.id.back);
        mListView = (ListView) findViewById(R.id.list_view);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BannerThemeActivity.this, ThemeActivity.class);
                intent.putExtra("theme",mAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    private void getBannerByType() {
        APIControl.getInstance().getBannerByType(this, linkTypeId, type, new DataResponseListener<BannerTheme>() {
            @Override
            public void onResponse(BannerTheme commonData) {
                if (commonData.getStatus() == 200) {
                    mAdapter.setmThemes(commonData.getData());
                    mListView.setAdapter(mAdapter);
                } else {
                    new ToastView(BannerThemeActivity.this,commonData.getErrorMsg()).show();
                }
            }
        }, errorListener(""));
    }
}
