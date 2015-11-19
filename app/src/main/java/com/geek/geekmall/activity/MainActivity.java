package com.geek.geekmall.activity;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.category.activity.SearchActivity;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.activity.AccountActivity;
import com.geek.geekmall.home.activity.HomeActivity;
import com.geek.geekmall.home.activity.ShoppingCartActivity;
import com.geek.geekmall.home.activity.StoreActivity;
import com.geek.geekmall.model.AgentSideBarData;
import com.geek.geekmall.profile.activity.CashActivity;
import com.geek.geekmall.profile.activity.CustomerActivity;
import com.geek.geekmall.profile.activity.InComeActivity;
import com.geek.geekmall.profile.activity.SalesRankActivity;
import com.geek.geekmall.profile.view.ProfileSlideLayout;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import cn.jpush.android.api.JPushInterface;


public class MainActivity extends TabActivity implements View.OnClickListener {
    private DrawerLayout mLayout;
    private ProfileSlideLayout mSlideLayout;
    private ViewGroup mTabBar;
    private Intent homeIntent;
    private Intent storeIntent;
    private Intent cartIntent;
    private Intent accountIntent;
    private View mHomeTab;
    private View mStoreTab;
    private View mCartTab;
    private View mMyTab;
    private ImageView mIconHome;
    private ImageView mIconStore;
    private ImageView mIconCart;
    private ImageView mIconMy;
    private TextView mHomeText;
    private TextView mStoreText;
    private TextView mCartText;
    private TextView mMyText;
    private MyBroadCast mMyBroadCast;
//    private String notify="";
//
//    public String getNotify() {
//        return notify;
//    }
//
//    public void setNotify() {
//        this.notify = "";
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatConfig.setDebugEnable(false);
        StatService.trackCustomEvent(this, "onCreate", "");
        init();
//
//        notify = getIntent().getStringExtra("type");
        this.mLayout = ((DrawerLayout) findViewById(R.id.drawer));
        this.mSlideLayout = ((ProfileSlideLayout) findViewById(R.id.layout_menu));
        int with = (int)(getWindowManager().getDefaultDisplay().getWidth() * 0.8);

        ((DrawerLayout.LayoutParams) findViewById(R.id.layout_menu).getLayoutParams()).width = with;
        this.mLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
//                View localView = MainActivity.n(this.a).getChildAt(0);
//                float f1 = 1.0F - slideOffset;
//                float f2 = 0.9F + 0.1F * f1;
//                float f3 = 1.0F - 0.4F * f1;
//                a.d(drawerView, f3);
//                a.e(drawerView, f3);
//                a.a(drawerView, 0.3F + 0.7F * (1.0F - f1));
//                a.f(drawerView, drawerView.getMeasuredWidth() * (1.0F - f3) / 2.0F);
//                a.f(localView, drawerView.getMeasuredWidth() * (1.0F - f1));
//                a.b(localView, 0.0F);
//                a.c(localView, localView.getMeasuredHeight() / 2);
//                localView.invalidate();
//                a.d(localView, f2);
//                a.e(localView, f2);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    private void init() {
        this.homeIntent = new Intent(this, HomeActivity.class);
        this.storeIntent = new Intent(this, StoreActivity.class);
        this.cartIntent = new Intent(this, ShoppingCartActivity.class);
        this.accountIntent = new Intent(this, AccountActivity.class);
//        a(getTabHost());
        getTabHost().addTab(getTab("首页", R.id.tab_home, R.drawable.home_icon_home_press, this.homeIntent));
        getTabHost().addTab(getTab("商场", R.id.tab_store, R.drawable.home_icon_store, this.storeIntent));
        getTabHost().addTab(getTab("购物车", R.id.tab_cart_layout, R.drawable.home_icon_cart, this.cartIntent));
        getTabHost().addTab(getTab("我的", R.id.tab_my, R.drawable.home_icon_my, this.accountIntent));
//        b(getTabHost());
        this.mTabBar = ((ViewGroup) findViewById(R.id.tab_bars));
        this.mHomeTab = findViewById(R.id.tab_home);
        this.mStoreTab = findViewById(R.id.tab_store);
        this.mCartTab = findViewById(R.id.tab_cart_layout);
        this.mMyTab = findViewById(R.id.tab_my_outer);
        this.mHomeTab.setOnClickListener(this);
        this.mStoreTab.setOnClickListener(this);
        this.mCartTab.setOnClickListener(this);
        this.mMyTab.setOnClickListener(this);
        this.mIconHome = ((ImageView) this.findViewById(R.id.icon_home));
        this.mIconStore = ((ImageView) this.findViewById(R.id.icon_store));
        this.mIconCart = ((ImageView) this.findViewById(R.id.icon_cart));
        this.mIconMy = ((ImageView) this.findViewById(R.id.icon_my));
        this.mHomeText = ((TextView) this.findViewById(R.id.text_home));
        this.mStoreText = ((TextView) this.findViewById(R.id.text_store));
        this.mCartText = ((TextView) this.findViewById(R.id.text_cart));
        this.mMyText = ((TextView) this.findViewById(R.id.text_my));
        getTabHost().setCurrentTab(0);
        registerBroadCast();
    }

    private void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.geek.geekmall.action.open_user_drawerlayout");
        filter.addAction("com.geek.geekmall.action.user_login");
        filter.addAction("com.geek.geekmall.action.update_photo");

        filter.addAction("com.geek.geekmall.action.user_logout");
        filter.addAction("com.geek.geekmall.action.search");

        filter.addAction("com.geek.geekmall.action.customer_layout");
        filter.addAction("com.geek.geekmall.action.sales_layout");
        filter.addAction("com.geek.geekmall.action.income_layout");
        filter.addAction("com.geek.geekmall.action.cash_layout");
        filter.addAction("com.geek.geekmall.action.home");
        filter.addAction("com.geek.geekmall.action.cart");
        filter.addAction("com.geekmall.upgrade");

        mMyBroadCast = new MyBroadCast();
        registerReceiver(mMyBroadCast, filter);
    }

    private void setHome() {
        this.mIconHome.setImageResource(R.drawable.home_icon_home_press);
        this.mIconStore.setImageResource(R.drawable.home_icon_store);
        this.mIconCart.setImageResource(R.drawable.home_icon_cart);
        this.mIconMy.setImageResource(R.drawable.home_icon_my);
        this.mHomeText.setTextColor(getResources().getColor(R.color.pink));
        this.mStoreText.setTextColor(getResources().getColor(R.color.content_gray));
        this.mCartText.setTextColor(getResources().getColor(R.color.content_gray));
        this.mMyText.setTextColor(getResources().getColor(R.color.content_gray));

        getTabHost().setCurrentTab(0);
    }

    private void setCart() {
        this.mIconHome.setImageResource(R.drawable.home_icon_home);
        this.mIconStore.setImageResource(R.drawable.home_icon_store);
        this.mIconCart.setImageResource(R.drawable.home_icon_cart_press);
        this.mIconMy.setImageResource(R.drawable.home_icon_my);
        this.mHomeText.setTextColor(getResources().getColor(R.color.content_gray));
        this.mStoreText.setTextColor(getResources().getColor(R.color.content_gray));
        this.mCartText.setTextColor(getResources().getColor(R.color.pink));
        this.mMyText.setTextColor(getResources().getColor(R.color.content_gray));

        getTabHost().setCurrentTab(2);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home:
                setHome();
                break;
            case R.id.tab_store:
                this.mIconHome.setImageResource(R.drawable.home_icon_home);
                this.mIconStore.setImageResource(R.drawable.home_icon_store_press);
                this.mIconCart.setImageResource(R.drawable.home_icon_cart);
                this.mIconMy.setImageResource(R.drawable.home_icon_my);
                this.mHomeText.setTextColor(getResources().getColor(R.color.content_gray));
                this.mStoreText.setTextColor(getResources().getColor(R.color.pink));
                this.mCartText.setTextColor(getResources().getColor(R.color.content_gray));
                this.mMyText.setTextColor(getResources().getColor(R.color.content_gray));

                getTabHost().setCurrentTab(1);
                break;
            case R.id.tab_cart_layout:
                setCart();
                break;
            case R.id.tab_my_outer:
                this.mIconHome.setImageResource(R.drawable.home_icon_home);
                this.mIconStore.setImageResource(R.drawable.home_icon_store);
                this.mIconCart.setImageResource(R.drawable.home_icon_cart);
                this.mIconMy.setImageResource(R.drawable.home_icon_my_press);
                this.mHomeText.setTextColor(getResources().getColor(R.color.content_gray));
                this.mStoreText.setTextColor(getResources().getColor(R.color.content_gray));
                this.mCartText.setTextColor(getResources().getColor(R.color.content_gray));
                this.mMyText.setTextColor(getResources().getColor(R.color.pink));

                getTabHost().setCurrentTab(3);
                break;
            default:
                break;
        }
    }

    private TabHost.TabSpec getTab(String title, int idRes, int iconRes, Intent intent) {
        return getTabHost().newTabSpec(title).setIndicator(getString(idRes), getResources().getDrawable(iconRes)).setContent(intent);
    }


    private class MyBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.geek.geekmall.action.open_user_drawerlayout".equals(intent.getAction())) {
                if (mLayout.isDrawerOpen(Gravity.LEFT)) {
                    mLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mLayout.openDrawer(Gravity.LEFT);
                }
            } else if ("com.geek.geekmall.action.user_login".equals(intent.getAction())
                    ||"com.geekmall.upgrade".equals(intent.getAction())
                    ||"com.geek.geekmall.action.update_photo".equals(intent.getAction())) {
                mSlideLayout.setData(GeekApplication.getAgentInfo());

            } else if ("com.geek.geekmall.action.user_logout".equals(intent.getAction())) {
                mSlideLayout.setData(null);
            } else if ("com.geek.geekmall.action.search".equals(intent.getAction())) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            } else if ("com.geek.geekmall.action.customer_layout".equals(intent.getAction())) {
//                startActivity(new Intent(MainActivity.this, CustomerActivity.class));
            } else if ("com.geek.geekmall.action.sales_layout".equals(intent.getAction())) {
//                startActivity(new Intent(MainActivity.this, SalesRankActivity.class));
            } else if ("com.geek.geekmall.action.income_layout".equals(intent.getAction())) {
//                startActivity(new Intent(MainActivity.this, InComeActivity.class));
            } else if ("com.geek.geekmall.action.cash_layout".equals(intent.getAction())) {
//                startActivity(new Intent(MainActivity.this, CashActivity.class));
            } else if ("com.geek.geekmall.action.home".equals(intent.getAction())) {
                setHome();
            } else if ("com.geek.geekmall.action.cart".equals(intent.getAction())) {
                setCart();
            }
        }
    }

    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMyBroadCast);
        super.onDestroy();
    }
}
