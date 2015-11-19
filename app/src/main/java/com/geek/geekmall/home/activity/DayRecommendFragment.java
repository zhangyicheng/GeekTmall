package com.geek.geekmall.home.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.geek.geekmall.R;
import com.geek.geekmall.home.adapter.DayAdapter;

/**
 * Created by apple on 4/28/15.
 */
public class DayRecommendFragment extends Fragment {
    private ListView mListView;
    private DayAdapter mDayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    static DayRecommendFragment newInstance(String title) {
        DayRecommendFragment fragment = new DayRecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        View view = inflater.inflate(R.layout.home_day_list, container, false);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mDayAdapter = new DayAdapter(getActivity());
        mListView.setAdapter(mDayAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        System.out.println("onActivityCreated = ");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
