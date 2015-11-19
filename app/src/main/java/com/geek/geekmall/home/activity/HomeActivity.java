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
import android.widget.ScrollView;

import com.android.volley.Response;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.ExitFragment;
import com.geek.geekmall.activity.MainActivity;
import com.geek.geekmall.bean.Advertisement;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.Theme;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.adapter.DayAdapter;
import com.geek.geekmall.home.adapter.FindWantAdapter;
import com.geek.geekmall.home.adapter.HomePagerAdapter;
import com.geek.geekmall.home.adapter.HomeThemeAdapter;
import com.geek.geekmall.model.HomeData;
import com.geek.geekmall.model.PageData;
import com.geek.geekmall.model.ProductPageData;
import com.geek.geekmall.model.UserData;
import com.geek.geekmall.product.activity.ProductInfoActivity;
import com.geek.geekmall.profile.activity.AgencyIntro;
import com.geek.geekmall.utils.ListViewUtil;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.views.AdvertisementLayout;
import com.geek.geekmall.views.ListViewForScrollView;
import com.geek.geekmall.views.ScrollViewPager;
import com.geek.geekmall.views.ToastView;
import com.geek.geekmall.views.indicator.TabPageIndicator;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshStikyScrollView;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/22/15.
 */
public class HomeActivity extends ExitFragment {
    private AdvertisementLayout mAdvertisementLayout;
    private TabPageIndicator mPageIndicator;
    private ScrollViewPager mViewPager;
    private List<View> mViews;
    private HomePagerAdapter mHomeAdapter;
    private ListView mListView;
    private ListViewForScrollView mListView1;
    private ListViewForScrollView mListView2;
    private ListViewForScrollView mListView3;
    private List<Advertisement> mAdList;
    private PullToRefreshStikyScrollView mScrollView;
    private float lastX, lastY;
    private ImageView mReturnView;
    private User user;
    private DayAdapter mDayAdapter;
    private FindWantAdapter mFindWantAdapter;
    private HomeThemeAdapter mThemeAdapter;
    private String userId = "";
    private String token = "";
    private HomeData mHomeData;
    private int mOneCurrentPage = 1;
    private int mTwoCurrentPage = 1;
    private int mThreeCurrentPage = 1;

    private List<Product> mRecommendList;
    private List<Product> mFindWantList;
    private List<Theme> mThemeList;
    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_fragment_activity_layout);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }

        init();
        if (GeekApplication.getToken() != null) {
            MyLog.debug(HomeActivity.class, "---------------islogin---------");
            autoLogin(GeekApplication.getToken());
        } else {
            getHome();
        }
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
//        startActivity(new Intent(this, AdvertisementActivity.class));
    }

    @Override
    protected void onResume() {
//        if (((MainActivity) getParent()).getNotify() != null && ((MainActivity) getParent()).getNotify().equals("notify")) {
//            ((MainActivity) getParent()).setNotify();
//            startActivity(new Intent(this, NotificationActivity.class));
//        }
//        MyLog.debug(HomeActivity.class, ((MainActivity) getParent()).getNotify() + "rerume------");
        super.onResume();
    }

    private void setData(HomeData homeData) {

        mRecommendList = homeData.getData().getRecommend().getDataList();
        mThemeList = homeData.getData().getTheme().getDataList();
        mFindWantList = homeData.getData().getFindWant().getDataList();

        mAdvertisementLayout.setData(homeData.getData().getScroll());
        mDayAdapter.setmProducts(mRecommendList);
        mDayAdapter.notifyDataSetChanged();
        mThemeAdapter.setmThemes(mThemeList);
        mThemeAdapter.notifyDataSetChanged();
        mFindWantAdapter.setmProducts(mFindWantList);
        mFindWantAdapter.notifyDataSetChanged();
        mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                new ListViewUtil().setListViewHeightBasedOnChildren(mListView2)));
    }

    private void getHome() {
        loadingDialog.show();
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        APIControl.getInstance().getHome(this, userId, token, SIZE,
                new DataResponseListener<HomeData>() {
                    @Override
                    public void onResponse(HomeData homeData) {
                        loadingDialog.dismiss();
                        if (homeData.getStatus() == 200) {
                            mHomeData = homeData;
                            setData(homeData);
                        } else {
                            new ToastView(HomeActivity.this, homeData.getErrorMsg()).show();
                        }

                    }
                }, errorListener());
    }

    @Override
    public Response.ErrorListener errorListener() {
        mScrollView.onRefreshComplete();
        return super.errorListener();
    }

    private void autoLogin(User user) {
        loadingDialog.show();
        String token = user.getToken();
        MyLog.debug(HomeActivity.class, "---------------autologin---------");
        APIControl.getInstance().autoLogin(HomeActivity.this, token, new DataResponseListener<UserData>() {
            @Override
            public void onResponse(UserData user) {
                loadingDialog.dismiss();
                MyLog.debug(HomeActivity.class, user.toString());
                if (user.getStatus() == 200) {
                    SharedPreUtil.getInstance().putUser(user.getData().getUserInfo());
                    GeekApplication.setUser(user.getData().getUserInfo());
                    GeekApplication.setAgentInfo(user.getData().getAgentInfo());
                    GeekApplication.setOrderInfo(user.getData().getOrderInfo());
                }
                getHome();

            }
        }, errorListener());
    }

    private void init() {
        mPageIndicator = (TabPageIndicator) findViewById(R.id.tab_indicator);
        mReturnView = (ImageView) findViewById(R.id.return_top);
        mReturnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_UP);
                mReturnView.setVisibility(View.GONE);
            }
        });

        mScrollView = (PullToRefreshStikyScrollView) findViewById(R.id.scroll_view);
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

                if (type == 0) {
                    mOneCurrentPage = 1;

                    getSuggestion();
                } else if (type == 1) {
                    mTwoCurrentPage = 1;

                    getThemes();
                } else if (type == 2) {
                    mThreeCurrentPage = 1;

                    getFind();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<StickyScrollView> refreshView) {
                if (type == 0) {
                    mOneCurrentPage++;
                    getSuggestion();
                } else if (type == 1) {
                    mTwoCurrentPage++;
                    getThemes();
                } else if (type == 2) {
//                    mThreeCurrentPage++;
//                    getFind();
                    mScrollView.onRefreshComplete();
                }
            }
        });

        mAdvertisementLayout = (AdvertisementLayout) findViewById(R.id.advertisement_layout);

        mViewPager = (ScrollViewPager) findViewById(R.id.home_pager);

        mViews = new ArrayList<View>();
        View view4 = getLayoutInflater().inflate(R.layout.scrollview_list, null);
        mListView1 = (ListViewForScrollView) view4.findViewById(R.id.list_view);
        mDayAdapter = new DayAdapter(this);
        mListView1.setAdapter(mDayAdapter);
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HomeActivity.this, ProductInfoActivity.class);
                intent.putExtra("product", mDayAdapter.getItem(position));
                startActivity(intent);
            }
        });
        mViews.add(view4);
        View view5 = getLayoutInflater().inflate(R.layout.scrollview_list, null);
        mListView2 = (ListViewForScrollView) view5.findViewById(R.id.list_view);
        mThemeAdapter = new HomeThemeAdapter(this);
        mListView2.setAdapter(mThemeAdapter);

        mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                if (mThemeAdapter.getItem(i).getTitle().contains("分销体系")) {
                    intent.setClass(HomeActivity.this, AgencyIntro.class);
                } else {
                    intent.setClass(HomeActivity.this, ThemeActivity.class);
                    intent.putExtra("theme", mThemeAdapter.getItem(i));
                }
                startActivity(intent);
            }
        });
        mViews.add(view5);

        View view6 = getLayoutInflater().inflate(R.layout.scrollview_list, null);
        View footer = getLayoutInflater().inflate(R.layout.home_footer_view, null);
        ImageView footerView = (ImageView) footer.findViewById(R.id.find_more);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FindWantActivity.class));
            }
        });
        mListView3 = (ListViewForScrollView) view6.findViewById(R.id.list_view);
        mListView3.addFooterView(footer);
        mFindWantAdapter = new FindWantAdapter(this);
        mListView3.setAdapter(mFindWantAdapter);
        mListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HomeActivity.this, ProductInfoActivity.class);
                intent.putExtra("product", mFindWantAdapter.getItem(position));
                if (mFindWantAdapter.getItem(position).getIsCheck() == 0) {
                    intent.putExtra("isWant", true);
                }
                startActivity(intent);
            }
        });
        mViews.add(view6);

        mHomeAdapter = new HomePagerAdapter(mViews);
        mViewPager.setAdapter(mHomeAdapter);
        mPageIndicator.setViewPager(mViewPager);
        mViewPager.setCurrentItem(1);
        mPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                type = position;
                if (position == 0) {
                    mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            new ListViewUtil().setListViewHeightBasedOnChildren(mListView1)));
                } else if (position == 1) {
                    mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            new ListViewUtil().setListViewHeightBasedOnChildren(mListView2)));
                } else if (position == 2) {
                    mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            new ListViewUtil().setListViewHeightBasedOnChildren(mListView3)));
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getThemes() {
        loadingDialog.show();
        APIControl.getInstance().getHomeTheme(this, userId, token, mTwoCurrentPage, SIZE, new DataResponseListener<PageData>() {
            @Override
            public void onResponse(PageData productData) {
                mScrollView.onRefreshComplete();
                loadingDialog.dismiss();
                if (productData.getStatus() == 200 && productData.getData().getDataList() != null) {
                    if (mTwoCurrentPage == 1) {
                        mThemeList = productData.getData().getDataList();
                    } else {
                        mThemeList.addAll(productData.getData().getDataList());
                    }
                    mThemeAdapter.setmThemes(mThemeList);
                    mThemeAdapter.notifyDataSetChanged();
                    mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            new ListViewUtil().setListViewHeightBasedOnChildren(mListView2)));
                }
            }
        }, errorListener());

    }

    private void getSuggestion() {
        loadingDialog.show();
        APIControl.getInstance().getHomeRecommend(this, userId, token, mOneCurrentPage, SIZE, new DataResponseListener<ProductPageData>() {
            @Override
            public void onResponse(ProductPageData productData) {
                mScrollView.onRefreshComplete();
                loadingDialog.dismiss();
                if (productData.getStatus() == 200 && productData.getData().getDataList() != null) {
                    if (mOneCurrentPage == 1) {
                        mRecommendList = productData.getData().getDataList();
                    } else {
                        mRecommendList.addAll(productData.getData().getDataList());
                    }
                    mDayAdapter.setmProducts(mRecommendList);
                    mDayAdapter.notifyDataSetChanged();
                    mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            new ListViewUtil().setListViewHeightBasedOnChildren(mListView1)));
                }

            }
        }, errorListener());
    }

    private void getFind() {
        loadingDialog.show();
        APIControl.getInstance().getHomeFind(this, userId, token, mThreeCurrentPage, SIZE, new DataResponseListener<ProductPageData>() {
            @Override
            public void onResponse(ProductPageData productData) {
                mScrollView.onRefreshComplete();
                loadingDialog.dismiss();
                if (productData.getStatus() == 200 && productData.getData().getDataList() != null) {
                    if (mThreeCurrentPage == 1) {
                        mFindWantList = productData.getData().getDataList();
                    } else {
                        mFindWantList.addAll(productData.getData().getDataList());
                    }
                    mFindWantAdapter.setmProducts(mFindWantList);
                    mFindWantAdapter.notifyDataSetChanged();
                    mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            new ListViewUtil().setListViewHeightBasedOnChildren(mListView3)));

                }
            }
        }, errorListener());
    }
}
