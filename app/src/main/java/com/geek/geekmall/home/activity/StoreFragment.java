package com.geek.geekmall.home.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseFragment;
import com.geek.geekmall.bean.StoreTitle;
import com.geek.geekmall.bean.Theme;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.adapter.StoreNewAdapter;
import com.geek.geekmall.home.adapter.StorePagerAdapter;
import com.geek.geekmall.model.PageData;
import com.geek.geekmall.model.StoresData;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.views.LoadingDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

/**
 * Created by apple on 4/29/15.
 */
public class StoreFragment extends Fragment{
    private PullToRefreshListView mListView;
    private StoreNewAdapter mStoreNewAdapter;
    private StoreTitle title;
    protected LoadingDialog loadingDialog;
    private int mListViewFirstItem = 0;//listView中第一项的索引
    private List<Theme> mThemes;
    private User user;
    private int mCurrentPage = 1;
    private ImageView mProgress;
    AnimationDrawable drawable;
    public StoreTitle getTitle() {
        return title;
    }

    public void setTitle(StoreTitle title) {
        this.title = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = GeekApplication.getUser();
        loadingDialog = new LoadingDialog(getActivity());
        MyLog.debug(StoreFragment.class,this+"********");

//        getStoresBy();

    }
    public void setThemes(List<Theme> themes){
        mThemes = themes;
//        mStoreNewAdapter.setThemes(themes);
//        mStoreNewAdapter.notifyDataSetChanged();
//        drawable.stop();
//        mProgress.setVisibility(View.GONE);
    }
    public static StoreFragment newInstance(StoreTitle argument)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("title", argument);
        StoreFragment contentFragment = new StoreFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }
    public void getStoresBy(StoreTitle title) {
        drawable.start();
        MyLog.debug(StoreFragment.class,title.getDisplayName()+"--------");
        APIControl.getInstance().getStoresBy(getActivity(), title.getId(), "", mCurrentPage, 20, new DataResponseListener<PageData>() {

            @Override
            public void onResponse(PageData storesData) {
                drawable.stop();
                mProgress.setVisibility(View.GONE);
                mListView.onRefreshComplete();
                if (storesData.getStatus() == 200) {
                    if (mCurrentPage == 1){

                        mThemes = storesData.getData().getDataList();
                    } else {
                        mThemes.addAll(storesData.getData().getDataList());
                    }

                    mStoreNewAdapter.setThemes(mThemes);
                    mStoreNewAdapter.notifyDataSetChanged();
                }
            }
        }, ((StoreActivity) getActivity()).errorListener());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_store_list, container, false);
        mListView = (PullToRefreshListView) view.findViewById(R.id.list_view);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mProgress = (ImageView) view.findViewById(R.id.progress);
        drawable = (AnimationDrawable) mProgress.getDrawable();
        drawable.start();
        mStoreNewAdapter = new StoreNewAdapter(getActivity());
        mListView.setAdapter(mStoreNewAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), ThemeActivity.class);
                intent.putExtra("theme",mThemes.get(position-1));
                startActivity(intent);
            }
        });
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mCurrentPage = 1;
                getStoresBy(title);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mCurrentPage++;
                getStoresBy(title);
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("zhong", "firstVisibleItem--" + firstVisibleItem + "--mListViewFirstItem--" + mListViewFirstItem);
                if (firstVisibleItem > mListViewFirstItem) {
                    ((StoreActivity) getActivity()).showReturnView(View.GONE);
                } else if (firstVisibleItem < mListViewFirstItem) {
                    ((StoreActivity) getActivity()).showReturnView(View.VISIBLE);
                } else if (firstVisibleItem == 0) {
                    ((StoreActivity) getActivity()).showReturnView(View.GONE);
                } else {
                    return;
                }
                mListViewFirstItem = firstVisibleItem;
            }

        });
        if (getArguments() != null){
            title = (StoreTitle) getArguments().getSerializable("title");
            if (title != null){
                getStoresBy(title);
            }
        }
        MyLog.debug(StoreFragment.class,this+"--------");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void scrollToTop() {
        mListView.getRefreshableView().smoothScrollToPosition(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
