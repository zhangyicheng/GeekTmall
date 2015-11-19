package com.geek.geekmall.category.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.category.adapter.CategoryAdapter;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CategoryPageData;
import com.geek.geekmall.utils.MyLog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by apple on 4/29/15.
 */
public class SearchFragment extends Fragment {
    private PullToRefreshListView mListView;
    private User user;
    private ImageView mProgress;
    AnimationDrawable drawable;
    private CategoryAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = GeekApplication.getUser();
        MyLog.debug(SearchFragment.class, this + "********");
        getCatory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_categray_list, container, false);
        mListView = (PullToRefreshListView) view.findViewById(R.id.list_view);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);
        mProgress = (ImageView) view.findViewById(R.id.progress);
        drawable = (AnimationDrawable) mProgress.getDrawable();
        drawable.start();

        return view;
    }

    private void getCatory() {
        APIControl.getInstance().getCategory(getActivity(), new DataResponseListener<CategoryPageData>() {
            @Override
            public void onResponse(CategoryPageData categoryPageData) {
                drawable.stop();
                mProgress.setVisibility(View.GONE);
                mAdapter = new CategoryAdapter(getActivity(), categoryPageData.getData().getDataList());
                mListView.setAdapter(mAdapter);
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
