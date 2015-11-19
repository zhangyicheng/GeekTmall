package com.geek.geekmall.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Advertisement;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.adapter.FindWantAdapter;
import com.geek.geekmall.home.adapter.FindWantPagerAdapter;
import com.geek.geekmall.home.adapter.HomePagerAdapter;
import com.geek.geekmall.model.ProductData;
import com.geek.geekmall.model.ProductPageData;
import com.geek.geekmall.product.activity.ProductInfoActivity;
import com.geek.geekmall.utils.ListViewUtil;
import com.geek.geekmall.views.ListViewForScrollView;
import com.geek.geekmall.views.ScrollViewPager;
import com.geek.geekmall.views.indicator.TabPageIndicator;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshStikyScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 6/15/15.
 */
public class FindWantActivity extends BaseActivity {
    private ImageView mCover;
    private ScrollViewPager mViewPager;
    private List<View> mViews;
    private FindWantPagerAdapter mHomeAdapter;
    private ListView mListView;
    private ListViewForScrollView mListView1;
    private ListViewForScrollView mListView2;
    private ListViewForScrollView mListView3;
private TextView mNoData1;
    private TextView mNoData2;
    private TextView mNoData3;

    private List<Advertisement> mAdList;
    private PullToRefreshStikyScrollView mScrollView;
    private float lastX, lastY;
    private ImageView mReturnView;
    private User user;
    private FindWantAdapter mSaleAdapter;
    private FindWantAdapter mFindAdapter;
    private FindWantAdapter mWantAdapter;
    private TabPageIndicator mIndicator;
    private String userId = "";
    private String token = "";
    private int mCurrentpage = 1;
    private TextView mBackView;
    private int type=0;
    private int mOneCurrentPage = 1;
    private int mTwoCurrentPage = 1;
    private int mThreeCurrentPage = 1;
    private List<Product> mFindList;
    private List<Product> mSaleList;
    private List<Product> mWantList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_want);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        mFindList = new ArrayList<>();
        mSaleList = new ArrayList<>();
        mWantList = new ArrayList<>();

        init();
        getFind();
    }

    private void getWant() {
        APIControl.getInstance().getMyFindWantList(this, userId, token, mThreeCurrentPage, SIZE,
                new DataResponseListener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData data) {
                        mScrollView.onRefreshComplete();
                        if (data.getData().getDataList() != null) {
                            if (mThreeCurrentPage == 1) {
                                mWantList = data.getData().getDataList();
                            } else {
                                mWantList.addAll(data.getData().getDataList());
                            }
                            if (mWantList == null || mWantList.isEmpty()) {
                                mListView3.setVisibility(View.GONE);
                                mNoData3.setVisibility(View.VISIBLE);
                            } else {
                                mListView3.setVisibility(View.VISIBLE);
                                mNoData3.setVisibility(View.GONE);
                                mWantAdapter.setmProducts(mWantList);
                                mWantAdapter.notifyDataSetChanged();
                                mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        new ListViewUtil().setListViewHeightBasedOnChildren(mListView3)));
                            }
                        }

                    }
                }, errorListener(URLs.MY_WANT_URL));
    }

    @Override
    protected Response.ErrorListener errorListener(String url) {
        mScrollView.onRefreshComplete();
        return super.errorListener(url);
    }

    private void getFind() {
        APIControl.getInstance().getFindWantList(this, userId, token, mOneCurrentPage, SIZE,
                new DataResponseListener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData data) {
                        mScrollView.onRefreshComplete();
                        if (data.getData().getDataList() != null) {
                            if (mOneCurrentPage == 1) {
                                mFindList = data.getData().getDataList();
                            } else {
                                mFindList.addAll(data.getData().getDataList());
                            }
                            if (mFindList == null || mFindList.isEmpty()) {
                                mListView1.setVisibility(View.GONE);
                                mNoData1.setVisibility(View.VISIBLE);
                            } else {
                                mListView1.setVisibility(View.VISIBLE);
                                mNoData1.setVisibility(View.GONE);
                                mFindAdapter.setmProducts(mFindList);
                                mFindAdapter.notifyDataSetChanged();
                                mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        new ListViewUtil().setListViewHeightBasedOnChildren(mListView1)));
                            }
                        }


                    }
                }, errorListener(URLs.FIND_WANT_URL));
    }

    private void getSale() {
        APIControl.getInstance().getFindWantSaleList(this, userId, token, mTwoCurrentPage, SIZE,
                new DataResponseListener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData data) {
                        mScrollView.onRefreshComplete();
                        if (data.getData().getDataList()!=null){
                            if (mTwoCurrentPage == 1){
                                mSaleList = data.getData().getDataList();
                            } else {
                                mSaleList.addAll(data.getData().getDataList());
                            }
                            if (mSaleList ==null ||mSaleList.isEmpty()){
                                mListView2.setVisibility(View.GONE);
                                mNoData2.setVisibility(View.VISIBLE);
                            } else {
                                mListView2.setVisibility(View.VISIBLE);
                                mNoData2.setVisibility(View.GONE);
                                mSaleAdapter.setmProducts(mSaleList);
                                mSaleAdapter.notifyDataSetChanged();
                                mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        new ListViewUtil().setListViewHeightBasedOnChildren(mListView2)));
                            }
                        }
                    }
                }, errorListener(URLs.SALE_URL));
    }

    private void init() {
        mBackView = (TextView) findViewById(R.id.back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIndicator = (TabPageIndicator) findViewById(R.id.find_indicator);
        mReturnView = (ImageView) findViewById(R.id.return_top);
        mReturnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_UP);
                mReturnView.setVisibility(View.GONE);
            }
        });
        mScrollView = (PullToRefreshStikyScrollView) findViewById(R.id.find_scrollview);
        mScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mScrollView.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = motionEvent.getX();
                        lastY = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        final float curX = motionEvent.getX();
                        final float curY = motionEvent.getY();
                        if (curY > lastY) {
                            mReturnView.setVisibility(View.VISIBLE);
                        } else {
                            mReturnView.setVisibility(View.GONE);
                        }
                        lastX = curX;
                        lastY = curY;
                        Log.d("zhong", "x=" + curX + "y=" + curY);
                        break;
                }
                return false;
            }
        });
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<StickyScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<StickyScrollView> refreshView) {
                if (mViewPager.getCurrentItem() == 0){
                    mOneCurrentPage=1;
                    getFind();
                } else if(mViewPager.getCurrentItem() == 1){
                    mTwoCurrentPage=1;
                    getSale();
                }else if(mViewPager.getCurrentItem() == 2){
                    mThreeCurrentPage=1;
                    getWant();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<StickyScrollView> refreshView) {
                if (mViewPager.getCurrentItem() == 0){
                    mOneCurrentPage++;
                    getFind();
                } else if(mViewPager.getCurrentItem() == 1){
                    mTwoCurrentPage++;
                    getSale();
                }else if(mViewPager.getCurrentItem() == 2){
                    mThreeCurrentPage++;
                    getWant();
                }

            }
        });
        mCover = (ImageView) findViewById(R.id.cover);


        mViewPager = (ScrollViewPager) findViewById(R.id.home_pager);

        mViews = new ArrayList<View>();
        View view4 = getLayoutInflater().inflate(R.layout.scrollview_list, null);
        mListView1 = (ListViewForScrollView) view4.findViewById(R.id.list_view);
        mNoData1 = (TextView) view4.findViewById(R.id.no_data);

        mFindAdapter = new FindWantAdapter(this);
        mListView1.setAdapter(mFindAdapter);
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FindWantActivity.this, ProductInfoActivity.class);
                intent.putExtra("product",mFindAdapter.getItem(i));
                intent.putExtra("isWant",true);
                startActivity(intent);
            }
        });
        mViews.add(view4);
        View view5 = getLayoutInflater().inflate(R.layout.scrollview_list, null);
        mListView2 = (ListViewForScrollView) view5.findViewById(R.id.list_view);
        mNoData2 = (TextView) view5.findViewById(R.id.no_data);
        mSaleAdapter = new FindWantAdapter(this);
        mListView2.setAdapter(mSaleAdapter);

        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FindWantActivity.this, ProductInfoActivity.class);
                intent.putExtra("product",mSaleAdapter.getItem(i));
                startActivity(intent);
            }
        });
        mViews.add(view5);

        View view6 = getLayoutInflater().inflate(R.layout.scrollview_list, null);

        mListView3 = (ListViewForScrollView) view6.findViewById(R.id.list_view);
        mNoData3 = (TextView) view6.findViewById(R.id.no_data);
        mWantAdapter = new FindWantAdapter(this);
        mListView3.setAdapter(mWantAdapter);
        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FindWantActivity.this, ProductInfoActivity.class);
                intent.putExtra("product",mWantAdapter.getItem(i));
                intent.putExtra("isWant",true);
                startActivity(intent);
            }
        });
        mViews.add(view6);

        mHomeAdapter = new FindWantPagerAdapter(mViews);
        mViewPager.setAdapter(mHomeAdapter);
        mIndicator.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
//        mViewPager.setPageTransformer(false, new DepthPageTransformer());
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                type = position;
                switch (position) {
                    case 0:
//                        mViewPager.setCurrentItem(0);
                        getFind();
//                        mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                new ListViewUtil().setListViewHeightBasedOnChildren(mListView1)));
                        break;
                    case 1:
                        getSale();
//                        mViewPager.setCurrentItem(1);
//                        mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                new ListViewUtil().setListViewHeightBasedOnChildren(mListView2)));
                        break;
                    case 2:
                        getWant();
//                        mViewPager.setCurrentItem(2);
//                        mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                                new ListViewUtil().setListViewHeightBasedOnChildren(mListView3)));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
