package com.geek.geekmall.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.FootPrintProduct;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.FootPrintPageData;
import com.geek.geekmall.product.activity.ProductInfoActivity;
import com.geek.geekmall.profile.adapter.FootPrintAdapter;
import com.geek.geekmall.utils.MyLog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 足迹
 * Created by apple on 4/22/15.
 */
public class FootPrintActivity extends BaseActivity implements View.OnClickListener {
    private FootPrintAdapter mAdapter;
    private PullToRefreshGridView mGridView;
    private TextView mBack;
    private TextView mEdit;
    private Button mDelete;
    private User user;
    private int mCurrentPage = 1;
    private List<FootPrintProduct> products;
    private CheckBox mCheckBox;
    private RelativeLayout mBottomLayout;
    private String userId = "";
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint_layout);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        products = new ArrayList<>();
        init();
        getFootPrint();
    }

    private void init() {
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom);
        mCheckBox = (CheckBox) findViewById(R.id.check_all);
        mDelete = (Button) findViewById(R.id.delete_btn);
        mDelete.setOnClickListener(this);
        mBack = (TextView) findViewById(R.id.back);
        mEdit = (TextView) findViewById(R.id.edit);
        mBack.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mGridView = (PullToRefreshGridView) findViewById(R.id.footprint_list);
        mAdapter = new FootPrintAdapter(this, mGridView);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                mCurrentPage = 1;
                getFootPrint();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                mCurrentPage++;
                getFootPrint();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mGridView.getRefreshableView().getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(FootPrintActivity.this, ProductInfoActivity.class);
                    Product product = new Product();
                    product.setId(mAdapter.getItem(position).getGoodsId());
                    product.setDisplayName(mAdapter.getItem(position).getDisplayName());
                    product.setMarketPrice(mAdapter.getItem(position).getMarketPrice());
                    product.setMemberPrice(mAdapter.getItem(position).getMemberPrice());
                    intent.putExtra("product", product);
                    startActivity(intent);
                }
            }
        });
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheckAll(mCheckBox.isChecked());
            }
        });
    }

    public void updateButton() {
        if (mGridView.getRefreshableView().getCheckedItemCount() == 0) {
            mCheckBox.setChecked(false);
            mDelete.setEnabled(false);
        } else {
            mDelete.setEnabled(true);
            if(mGridView.getRefreshableView().getCheckedItemCount() < mAdapter.getCount()){
                mCheckBox.setChecked(false);
            } else {
                mCheckBox.setChecked(true);
            }
        }
    }

    private void setCheckAll(boolean isCheck) {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            mGridView.getRefreshableView().setItemChecked(i, isCheck);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void getFootPrint() {
        loadingDialog.show();
        APIControl.getInstance().footPrint(this, userId, token, mCurrentPage, SIZE,
                new DataResponseListener<FootPrintPageData>() {
                    @Override
                    public void onResponse(FootPrintPageData productPageData) {
                        loadingDialog.dismiss();
                        if (productPageData.getStatus() == 200) {
                            if (mCurrentPage == 1) {
                                products = productPageData.getData().getDataList();
                            } else {
                                products.addAll(productPageData.getData().getDataList());
                            }
                        }
                        if (products.size() == 0) {
                            mDelete.setEnabled(false);
                            mCheckBox.setChecked(false);
                            mCheckBox.setEnabled(false);
                        }
                        mGridView.onRefreshComplete();
                        mGridView.getRefreshableView().clearChoices();
                        mAdapter.setmProducts(products);
                        mAdapter.notifyDataSetChanged();
                    }
                }, errorListener(URLs.FOOTPRINT_URL));
    }

    private void deleteFootPrint() {
//    APIControl.getInstance().deleteFootPrint(this,);
        if (products.size() == 0) {
            return;
        }
        List<FootPrintProduct> products = new ArrayList<>();
        for (int i = 0; i < mGridView.getRefreshableView().getCheckedItemPositions().size(); i++) {
            if (mGridView.getRefreshableView().getCheckedItemPositions().valueAt(i)) {

                products.add(mAdapter.getItem(mGridView.getRefreshableView().getCheckedItemPositions().keyAt(i)));
            }
        }
        APIControl.getInstance().deleteFootPrint(this, products, userId, token, new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {
                if (commonData.getStatus() == 200) {
                    getFootPrint();
                }
            }
        }, errorListener(""));
        MyLog.debug(FootPrintActivity.class, mGridView.getRefreshableView().getCheckedItemPositions() + "");
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
            case R.id.edit:
                if (mEdit.getText().toString().equals("编辑")) {
                    mBottomLayout.setVisibility(View.VISIBLE);
                    mEdit.setText("取消");
                    mGridView.getRefreshableView().setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                } else {
                    mBottomLayout.setVisibility(View.GONE);
                    mEdit.setText("编辑");
                    mCheckBox.setChecked(false);
                    mGridView.getRefreshableView().clearChoices();
                    mGridView.getRefreshableView().setChoiceMode(AbsListView.CHOICE_MODE_NONE);
                    mAdapter.notifyDataSetChanged();
                }

                break;
            case R.id.delete_btn:
                deleteFootPrint();
                break;
            default:
                break;
        }
    }
}
