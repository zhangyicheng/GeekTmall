package com.geek.geekmall.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.ExitFragment;
import com.geek.geekmall.bean.Cart;
import com.geek.geekmall.bean.Store;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.adapter.CartAdapter;
import com.geek.geekmall.home.adapter.LikeAdapter;
import com.geek.geekmall.model.BuyData;
import com.geek.geekmall.model.CartData;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.ProductPageData;
import com.geek.geekmall.product.activity.ProductInfoActivity;
import com.geek.geekmall.views.ExpandListViewForScrollView;
import com.geek.geekmall.views.HorizontalListView;
import com.geek.geekmall.views.SureDialog;
import com.geek.geekmall.views.ToastView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/22/15.
 */
public class ShoppingCartActivity extends ExitFragment implements View.OnClickListener {
    //    private ShoppingCartBaseFragment mFragment;
    private ExpandListViewForScrollView mListView;
    private CartAdapter mAdapter;
    private LikeAdapter mLikeAdapter;
    private HorizontalListView mHorizontalListView;
    private User user;
    private String userId = "";
    private String token = "";
    private CheckBox mCheckBox;
    private Button mPayView;
    private TextView mTotalPrice;
    private SureDialog mDialog;
    private List<Store> mStores;
    private LinearLayout mBottomLayout;
    private LinearLayout mEmptyLayout;
    private Button mToBuy;
    private PullToRefreshScrollView mScrollView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mStores = new ArrayList<>();
        init();
//        getCarts();
//        payOrder();
    }

    private void init() {
        mScrollView = (PullToRefreshScrollView) findViewById(R.id.carts_list);
        mBottomLayout = (LinearLayout) findViewById(R.id.footer_bar);
        mEmptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
        mToBuy = (Button) findViewById(R.id.to_buy);
        mToBuy.setOnClickListener(this);
        mTotalPrice = (TextView) findViewById(R.id.total_price);
        findViewById(R.id.sidebar).setOnClickListener(this);
        mListView = (ExpandListViewForScrollView) findViewById(R.id.cart_listview);
//        View footer = getLayoutInflater().inflate(R.layout.shopping_cart_footer, null);
        mHorizontalListView = (HorizontalListView) findViewById(R.id.like_listview);
        mCheckBox = (CheckBox) findViewById(R.id.all_check_box);
        mPayView = (Button) findViewById(R.id.pay);
        mLikeAdapter = new LikeAdapter(this);
        mPayView.setOnClickListener(this);
        mHorizontalListView.setAdapter(mLikeAdapter);
        mListView.setGroupIndicator(null);
//        mFragment = ((ShoppingCartBaseFragment)getSupportFragmentManager().findFragmentById(R.id.fragment));
//        mListView.addFooterView(footer);
        mAdapter = new CartAdapter(this);
        mListView.setAdapter(mAdapter);
        mHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShoppingCartActivity.this, ProductInfoActivity.class);
                intent.putExtra("product", mLikeAdapter.getItem(position));
                startActivity(intent);
            }
        });
//        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Intent intent = new Intent(ShoppingCartActivity.this, ProductInfoActivity.class);
//                intent.putExtra("product", mAdapter.getChild(groupPosition, childPosition));
//                startActivity(intent);
//                return true;
//            }
//        });

        mAdapter.setmListener(new CartAdapter.CartListener() {
            @Override
            public void onCheck() {
                float total = 0;
                for (int i=0;i<mAdapter.getChecked().size();i++){
                    total += mAdapter.getChecked().get(i).getGoodsNumber()*mAdapter.getChecked().get(i).getMemberPrice();
                }
                mTotalPrice.setText("￥ "+total);
            }
        });
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checks = new SparseBooleanArray();
                for (int i = 0; i < mStores.size(); i++) {
                    checks.put(i, mCheckBox.isChecked());
                }
                mAdapter.setChildIgnoreChange(false);
                mAdapter.setmGroupCheck(checks);
                mAdapter.notifyDataSetChanged();
            }
        });
        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (user != null) {
                    userId = user.getId();
                    token = user.getToken();
                    getCarts();

                } else {
                    mScrollView.onRefreshComplete();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
            getCarts();

        } else {
            userId = "";
            token = "";
            mListView.setVisibility(View.GONE);
            mBottomLayout.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.VISIBLE);
        }
        getGuessLike();
    }

    public void updateCheckAll(boolean isCheck) {
        mCheckBox.setChecked(isCheck);
    }

    private void settlement() {
        loadingDialog.show();
        APIControl.getInstance().settlementCart(this, userId, token, mAdapter.getChecked(),
                new DataResponseListener<BuyData>() {
                    @Override
                    public void onResponse(BuyData buyData) {
                        loadingDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("list", buyData.getData());
                        intent.setClass(ShoppingCartActivity.this, SettlementActivity.class);
                        startActivity(intent);
                    }
                }, errorListener());
    }

    private void getCarts() {
        loadingDialog.show();
        APIControl.getInstance().getCartList(this, userId, token, new DataResponseListener<CartData>() {
            @Override
            public void onResponse(CartData cartData) {
                loadingDialog.dismiss();
                mScrollView.onRefreshComplete();
                //TODO 是否分页
                if (cartData.getStatus() == 200) {
                    if (cartData.getData().getValidInfoList() != null) {
                        mStores = cartData.getData().getValidInfoList();
                        mAdapter.setmStores(mStores);
                        mAdapter.notifyDataSetChanged();
                        for (int i = 0; i < cartData.getData().getValidInfoList().size(); i++) {
                            mListView.expandGroup(i);
                        }
                    }
                } else {
                    new ToastView(ShoppingCartActivity.this, cartData.getErrorMsg()).show();
                }
                if (mStores == null || mStores.isEmpty()) {
                    mListView.setVisibility(View.GONE);
                    mBottomLayout.setVisibility(View.GONE);
                    mEmptyLayout.setVisibility(View.VISIBLE);
                } else {
                    mEmptyLayout.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                    mBottomLayout.setVisibility(View.VISIBLE);
                }
            }
        }, errorListener());
    }

    public void deleteCarts(final Cart cart) {
        mDialog = new SureDialog(ShoppingCartActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                List<Cart> carts = new ArrayList<>();
                carts.add(cart);
                loadingDialog.show();
                APIControl.getInstance().deleteCart(ShoppingCartActivity.this, userId, token, carts, new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData data) {
                        loadingDialog.dismiss();
                        if (data.getStatus() == 200) {
                            getCarts();
                        } else {
                            new ToastView(ShoppingCartActivity.this, "删除失败 " + data.getErrorMsg()).show();
                        }
                    }
                }, errorListener());
            }
        });
        mDialog.show();
        mDialog.setTitle("确定要删除这件商品？");

    }

    public void modifyCarts(String cardId, String number) {
        loadingDialog.show();
        APIControl.getInstance().modifyCart(this, userId, token, number, cardId,
                new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData cartData) {
                        loadingDialog.dismiss();
                        if (cartData.getStatus() == 200) {
//                            getCarts();
                        }
                    }
                }, errorListener());
    }

    private void getGuessLike() {
        loadingDialog.show();
        APIControl.getInstance().guessLike(this, userId, 10 + "", new DataResponseListener<ProductPageData>() {
            @Override
            public void onResponse(ProductPageData productPageData) {
                loadingDialog.dismiss();
                if (productPageData.getStatus() == 200) {
                    mLikeAdapter.setmProducts(productPageData.getData().getDataList());
                    mLikeAdapter.notifyDataSetChanged();
                }
            }
        }, errorListener());
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.pay:
                if (mAdapter.getChecked().size() > 0) {
                    settlement();
                } else {
                    new ToastView(ShoppingCartActivity.this, "请勾选商品").show();
                }

                break;
            case R.id.to_buy:
                sendBroadcast(new Intent("com.geek.geekmall.action.home"));

                break;
            default:
                break;
        }
    }
}
