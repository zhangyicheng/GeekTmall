package com.geek.geekmall.category.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseFragment;
import com.geek.geekmall.category.adapter.SearchPagerAdapter;
import com.geek.geekmall.views.indicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/22/15.
 */
public class SearchActivity extends BaseFragment {
    private FrameLayout mSearchLayout;
    private ViewPager mViewPager;
    private TabPageIndicator indicator;
    private SearchPagerAdapter mPagerAdapter;
    private List<Fragment> mFragments;
    private TextView mBackView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mFragments = new ArrayList<>();
        init();
        setListener();
    }

    private void init() {
        mBackView = (TextView) findViewById(R.id.back);
        mSearchLayout = (FrameLayout) findViewById(R.id.search_parent);
        indicator = (TabPageIndicator) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mFragments.add(new SearchFragment());
        mFragments.add(new BrandFragment());
        mPagerAdapter = new SearchPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mPagerAdapter);
        indicator.setViewPager(mViewPager);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setListener() {

        mSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this, SearchDetailActivity.class));
            }
        });
    }
}
