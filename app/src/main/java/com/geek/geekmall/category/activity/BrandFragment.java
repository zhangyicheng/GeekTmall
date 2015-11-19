package com.geek.geekmall.category.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.bean.Brand;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.category.adapter.BrandAdapter;
import com.geek.geekmall.category.adapter.HotBrandAdapter;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.BrandData;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.views.GridViewForScrollView;
import com.geek.geekmall.views.ListViewForScrollView;
import com.geek.geekmall.views.cityindex.SectionBar;

import java.util.List;

/**
 * Created by apple on 4/29/15.
 */
public class BrandFragment extends Fragment {
    private ListView mListView;
    private GridViewForScrollView mHotList;
    private User user;
    private ImageView mProgress;
    AnimationDrawable drawable;
    private BrandAdapter mAdapter;
    private HotBrandAdapter mHotAdapter;
    private List<Brand> mBrands;
    private SectionBar mSectionBar;
    private List<Brand> mHotBrands;
private TextView mToastView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = GeekApplication.getUser();
        mAdapter = new BrandAdapter(getActivity());
        getBrands();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_brand, container, false);
        View hot = inflater.inflate(R.layout.brand_hot_grid, null, false);
        mToastView = (TextView) view.findViewById(R.id.toast);
        mListView = (ListView) view.findViewById(R.id.brand_list);
        mListView.addHeaderView(hot);
        mHotList = (GridViewForScrollView) hot.findViewById(R.id.hot_brand);
        mSectionBar = (SectionBar) view.findViewById(R.id.sidebar);
        mSectionBar.setListener(new SectionBar.TouchBarListener() {
            @Override
            public void onTouch(String str) {
                mToastView.setVisibility(View.VISIBLE);
                mToastView.setText(str);
            }

            @Override
            public void onRelease() {
                mToastView.setVisibility(View.GONE);
            }
        });

        mProgress = (ImageView) view.findViewById(R.id.progress);
        drawable = (AnimationDrawable) mProgress.getDrawable();
        drawable.start();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startResult(mBrands.get(position-1).getId(),mBrands.get(position-1).getBrandName());
            }
        });
        mHotList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startResult(mHotBrands.get(position).getId(),mHotBrands.get(position).getBrandName());
            }
        });

        return view;
    }

    private void startResult(String brandId,String brandName) {
        Intent intent = new Intent(getActivity(), SearchGoodsResultActivity.class);
        intent.putExtra("brandId", brandId);
        intent.putExtra("title", brandName);

        startActivity(intent);
    }

    private void getBrands() {
        APIControl.getInstance().getBrand(getActivity(), new DataResponseListener<BrandData>() {
            @Override
            public void onResponse(BrandData data) {
                drawable.stop();
                mListView.setVisibility(View.VISIBLE);
                mSectionBar.setVisibility(View.VISIBLE);
                mHotList.setVisibility(View.VISIBLE);
                mProgress.setVisibility(View.GONE);
                if (data.getStatus() == 200) {
                    mBrands = data.getData().getBrandList();
                    mHotBrands = data.getData().getRecommendBrandList();
                   mAdapter.setmBrands(mBrands);
                    mListView.setAdapter(mAdapter);
                    mSectionBar.setListView(mListView);
                    mHotAdapter = new HotBrandAdapter(getActivity(), mHotBrands);
                    mHotList.setAdapter(mHotAdapter);
                }

            }
        }, ((SearchActivity) getActivity()).errorListener());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
