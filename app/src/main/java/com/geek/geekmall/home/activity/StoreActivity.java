package com.geek.geekmall.home.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseFragment;
import com.geek.geekmall.activity.ExitFragment;
import com.geek.geekmall.bean.StoreTitle;
import com.geek.geekmall.bean.Theme;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.adapter.StoreNewAdapter;
import com.geek.geekmall.home.adapter.StorePagerAdapter;
import com.geek.geekmall.model.StoresData;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.views.indicator.TabPageIndicator;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/23/15.
 */
public class StoreActivity extends ExitFragment {
    private ViewPager mViewPager;
    private TabPageIndicator indicator;
    private StorePagerAdapter mPagerAdapter;
    private ImageView mReturnView;
    private List<StoreTitle> titles;
    private List<Theme> mThemes;
    private ImageView mProgress;
    private List<StoreFragment> mFragments;
    private PullToRefreshListView mListView;
    private StoreNewAdapter mStoreNewAdapter;
    AnimationDrawable drawable;
private boolean isRun = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        titles = new ArrayList<>();
        mThemes = new ArrayList<>();
        mFragments = new ArrayList<StoreFragment>();
        init();
        getStores();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isRun && titles.isEmpty()){
            getStores();
        }

    }

    private void getStoresBy() {
//        loadingDialog.show();
//        APIControl.getInstance().getStoresBy(this, title.getId(), "", mCurrentPage, 20, new DataResponseListener<PageData>() {
//            @Override
//            public void onResponse(PageData storesData) {
//                loadingDialog.dismiss();
//                if (storesData.getStatus() == 200) {
//                    mThemes = storesData.getData().getDataList();
//
//                    mStoreNewAdapter.setThemes(mThemes);
//                    mStoreNewAdapter.notifyDataSetChanged();
//                }
//            }
//        }, errorListener());
    }

    private void getStores() {
        isRun =true;
        APIControl.getInstance().getStores(this, SIZE, new DataResponseListener<StoresData>() {
            @Override
            public void onResponse(StoresData storesData) {
                isRun = false;
                indicator.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
//                drawable.stop();
//                mProgress.setVisibility(View.GONE);
                if (storesData.getStatus() == 200) {
                    titles = storesData.getData().getMallTheme();
//                    mThemes = storesData.getData().getData().getDataList();
                    if (titles != null) {
                        for (StoreTitle title : titles) {

                            mFragments.add(StoreFragment.newInstance(title));
                        }
                        mPagerAdapter = new StorePagerAdapter(getSupportFragmentManager(), titles, mFragments);
                        mViewPager.setAdapter(mPagerAdapter);
//                        mPagerAdapter.notifyDataSetChanged();
//                        indicator.notifyDataSetChanged();

                        indicator.setViewPager(mViewPager);
                        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageSelected(int position) {
                                MyLog.debug(StoreActivity.class,position+"-----------");
//                                mFragments.get(position).getStoresBy(titles.get(position));
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
//
                    }
                    mViewPager.setCurrentItem(0);
                }
            }
        }, errorListener());
    }

    @Override
    public Response.ErrorListener errorListener() {
        isRun = false;
        return super.errorListener();
    }

    private void init() {
//        mProgress = (ImageView) findViewById(R.id.progress);
//        mAllFragment = new StoreFragment();
//        mClothFragment = new StoreFragment();
//        mMakeUpFragment = new StoreFragment();
//        mLiveFragment = new StoreFragment();
//        drawable = (AnimationDrawable) mProgress.getDrawable();
//        drawable.start();
        indicator = (TabPageIndicator) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        mViewPager.setAdapter(mPagerAdapter);
//        indicator.setViewPager(mViewPager);
        indicator.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        mReturnView = (ImageView) findViewById(R.id.return_top);
        mReturnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReturnView.setVisibility(View.GONE);
            }
        });


    }

    public void showReturnView(int visible) {
        mReturnView.setVisibility(visible);
    }
}
