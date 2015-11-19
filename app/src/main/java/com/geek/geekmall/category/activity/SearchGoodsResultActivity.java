package com.geek.geekmall.category.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.category.adapter.SearchProductAdapter;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.ProductPageData;
import com.geek.geekmall.product.activity.ProductInfoActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.List;

/**
 * Created by apple on 4/22/15.
 */
public class SearchGoodsResultActivity extends BaseActivity implements View.OnClickListener {
    private SearchProductAdapter mAdapter;
    private PullToRefreshGridView mGridView;
    private TextView mBackView;
    private int mCurrentPage = 1;
    private String keyword;
    private ImageView mProgress;
    private AnimationDrawable mAnimation;
    private String brandId = "";
    private String categoryId = "";
    private User user;
    private String userId = "";
    private List<Product> mProducts;
    private TextView mTitleView;
    private String title = "";
    private ImageView mReturnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        Bundle bundle = getIntent().getExtras();
        keyword = bundle.getString("keyword", "");
        brandId = bundle.getString("brandId", "");
        title = bundle.getString("title", "");
        categoryId = bundle.getString("categoryId", "");
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
        }
        init();
        getResult();
    }

    private void init() {
        mReturnView = (ImageView) findViewById(R.id.return_top);
        mReturnView.setOnClickListener(this);
        mTitleView = (TextView) findViewById(R.id.title);
        mTitleView.setText(title);
        mBackView = (TextView) findViewById(R.id.back);
        mProgress = (ImageView) findViewById(R.id.progress);
        mAnimation = (AnimationDrawable) mProgress.getDrawable();
        mGridView = (PullToRefreshGridView) findViewById(R.id.search_list);
        mBackView.setOnClickListener(this);
        mAdapter = new SearchProductAdapter(this);
        mGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mCurrentPage = 1;
                getResult();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                mCurrentPage++;
                getResult();
            }
        });
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchGoodsResultActivity.this, ProductInfoActivity.class);
                intent.putExtra("product", mAdapter.getItem(position));
                startActivity(intent);
            }
        });
        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > 10) {
                    mReturnView.setVisibility(View.VISIBLE);
                } else {
                    mReturnView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getResult() {
        mAnimation.start();
        APIControl.getInstance().searchList(this, SIZE, mCurrentPage, categoryId, brandId, keyword, userId,
                new DataResponseListener<ProductPageData>() {
                    @Override
                    public void onResponse(ProductPageData data) {
                        mGridView.onRefreshComplete();
                        mAnimation.stop();
                        mProgress.setVisibility(View.GONE);
                        if (data.getStatus() == 200) {
                            if (mCurrentPage == 1) {
                                mProducts = data.getData().getDataList();
                            } else {
                                mProducts.addAll(data.getData().getDataList());
                            }
                            mAdapter.setmProducts(mProducts);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }, errorListener(""));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.return_top:
                mGridView.getRefreshableView().smoothScrollToPosition(0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
