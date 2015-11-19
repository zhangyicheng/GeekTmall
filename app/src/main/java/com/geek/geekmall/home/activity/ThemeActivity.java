package com.geek.geekmall.home.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.Theme;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.adapter.ProductAdapter;
import com.geek.geekmall.model.ProductPageData;
import com.geek.geekmall.product.activity.ProductInfoActivity;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.ScreenUtils;
import com.geek.geekmall.views.GridViewForScrollView;
import com.geek.geekmall.views.SortTagView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshStikyScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.MemoryPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 5/12/15.
 */
public class ThemeActivity extends BaseActivity implements View.OnClickListener {
    private GridViewForScrollView mGridView;
    private TextView mAllView;
    private SortTagView mPriceView;
    private SortTagView mSaleView;
    private TextView mNameView;
    private TextView mFilterView;
    private ProductAdapter mAdapter;
    private PullToRefreshStikyScrollView mScrollView;
    private LinearLayout mSortLayout;
    private TextView mBackView;
    private int mListViewFirstItem = 0;//listView中第一项的索引
    private ImageView mReturnView;
    private Theme mTheme;
    private int mCurrentPage = 1;
    private int order = 1;
    private List<Product> mProducts;
    private ImageView mProgress;
    AnimationDrawable drawable;
    private ImageView mHeaderView;
    private TextView mRuleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        mTheme = (Theme) getIntent().getSerializableExtra("theme");
        init();
        mProducts = new ArrayList<>();
        getProduct();
        if (!TextUtils.isEmpty(mTheme.getTitle()) && mTheme.getTitle().contains("免费")){
            mRuleView.setVisibility(View.VISIBLE);
            startActivity(new Intent(this, ShouDanActivity.class));
        }

    }

    private void init() {
        mRuleView = (TextView) findViewById(R.id.rule_note);
        mNameView = (TextView)findViewById(R.id.title);
        mNameView.setText(mTheme.getTitle());
        mHeaderView = (ImageView) findViewById(R.id.header_view);
        mHeaderView.setOnClickListener(this);
        if (!TextUtils.isEmpty(mTheme.getImgUrl())) {
            String url = mTheme.getImgUrl();
            if (!url.startsWith("http://")){
                url = URLs.IMAGE_URL+mTheme.getImgUrl();
            }
            ImageLoader.getInstance(this).getPicasso()
                    .load(url)
                    .fit()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.home_banner_sec_default)
                    .into(mHeaderView);
//            ImageLoader.getInstance(this).getBitmapUtils()
//                    .configDefaultLoadingImage(R.drawable.product_list_default)
//                    .configDefaultLoadFailedImage(R.drawable.product_home_default)
//                    .display(mHeaderView, url);
//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.product_home_default)
//                    .showImageOnFail(R.drawable.product_home_default)
//                    .cacheInMemory(false)
//                    .cacheOnDisk(true)
//                    .build();
//            ImageLoader.getInstance(this).getUniversalImageLoader().displayImage(url, mHeaderView, options);
        }

        mScrollView = (PullToRefreshStikyScrollView) findViewById(R.id.scroll_view);
        mGridView = (GridViewForScrollView) findViewById(R.id.grid_view);
        mScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        mSortLayout = (LinearLayout) findViewById(R.id.sort_layout);
        mAllView = (TextView) findViewById(R.id.all_view);
        mPriceView = (SortTagView) findViewById(R.id.price_view);
        mSaleView = (SortTagView) findViewById(R.id.sale_view);
        mFilterView = (TextView) findViewById(R.id.filter_view);
        mSortLayout = (LinearLayout) findViewById(R.id.sort_layout);
        mBackView = (TextView) findViewById(R.id.back);
        mReturnView = (ImageView) findViewById(R.id.return_top);
        mBackView.setOnClickListener(this);
        mReturnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mReturnView.setVisibility(View.GONE);
                mGridView.smoothScrollToPosition(0);
            }
        });
        mProgress = (ImageView) findViewById(R.id.progress);
        drawable = (AnimationDrawable) mProgress.getDrawable();
        drawable.start();
        mAdapter = new ProductAdapter(this);

        mGridView.setAdapter(mAdapter);
        mAllView.setOnClickListener(this);
        mPriceView.setType(2);
        mSaleView.setType(3);
        mPriceView.setListener(new SortTagView.TatOnclickListener() {
            @Override
            public void onClick(int order_) {
                order = order_;
                mSaleView.reset();
                mAllView.setTextColor(getResources().getColor(R.color.content_black));
                getProduct();
            }
        });
        mSaleView.setListener(new SortTagView.TatOnclickListener() {
            @Override
            public void onClick(int order_) {
                order = order_;
                mPriceView.reset();
                mAllView.setTextColor(getResources().getColor(R.color.content_black));
                getProduct();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ThemeActivity.this, ProductInfoActivity.class);
                intent.putExtra("product", mProducts.get(i));
                startActivity(intent);
            }
        });
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<StickyScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<StickyScrollView> refreshView) {
                mCurrentPage = 1;
                getProduct();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<StickyScrollView> refreshView) {
                mCurrentPage++;
                getProduct();
            }
        });

        mRuleView.setOnClickListener(this);

    }

    private void getProduct() {
        APIControl.getInstance().getThemeProduct(this, mTheme.getId(), order + "", mCurrentPage, SIZE, new DataResponseListener<ProductPageData>() {
            @Override
            public void onResponse(ProductPageData productPageData) {
                mScrollView.onRefreshComplete();
                drawable.stop();
                mProgress.setVisibility(View.GONE);
                if (productPageData.getStatus() == 200) {
                    if (mCurrentPage == 1) {
                        mProducts = productPageData.getData().getDataList();
                    } else {
                        mProducts.addAll(productPageData.getData().getDataList());
                    }
                    mAdapter.setmProducts(mProducts);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }, errorListener(URLs.STORES_THEME_BY_URL));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.all_view:
                mAllView.setTextColor(getResources().getColor(R.color.pink));
                order = 1;
                mSaleView.reset();
                mPriceView.reset();
                getProduct();
                break;
            case R.id.filter_view:
                break;
//            case R.id.rule_note:
//                startActivity(new Intent(this,ShouDanActivity.class));
//                break;
            case R.id.header_view:
                if (!TextUtils.isEmpty(mTheme.getTitle()) && mTheme.getTitle().contains("免费")){
                    startActivity(new Intent(this, ShouDanActivity.class));
                }
                break;
            default:break;

        }
    }
}
