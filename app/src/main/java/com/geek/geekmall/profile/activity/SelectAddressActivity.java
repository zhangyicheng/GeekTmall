package com.geek.geekmall.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Address;
import com.geek.geekmall.bean.Area;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.model.AreaData;
import com.geek.geekmall.profile.adapter.AreaAdapter;
import com.geek.geekmall.utils.FileUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/22/15.
 */
public class SelectAddressActivity extends BaseActivity implements View.OnClickListener {
    private ListView mListView;
    private User user;
    private Address address;
    private TextView mBack;
    private AreaAdapter mAdapter;
    private List<Area> mAreas;
    private TextView mTitleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_address_view);
        user = GeekApplication.getUser();
        address = (Address) getIntent().getSerializableExtra("address");
        mAreas = new ArrayList<>();
        initView();
        mAdapter = new AreaAdapter(this);
        AreaData areaData = FileUtils.readJson(this);
        mAdapter.setmAreas(areaData.getData());
        mListView.setAdapter(mAdapter);
        address = new Address();
    }

    private void initView() {
        mTitleView = (TextView) findViewById(R.id.name);
        mBack = (TextView) findViewById(R.id.back);
        mListView = (ListView) findViewById(R.id.list_view);
        mBack.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAreas.add(mAdapter.getItem(position));
                mTitleView.setVisibility(View.VISIBLE);
                mTitleView.setText(mTitleView.getText()+""+mAdapter.getItem(position).getDisplayName());
                if (mAdapter.getItem(position).getChildrenList() == null || mAdapter.getItem(position).getChildrenList().size() == 0){
                    Intent intent = new Intent();
                    intent.putExtra("areas", (Serializable) mAreas);
                    setResult(RESULT_OK,intent);
                    finish();
                } else {
                    mAdapter = new AreaAdapter(SelectAddressActivity.this,mAdapter.getItem(position).getChildrenList());
                    mListView.setAdapter(mAdapter);
                }
            }
        });
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
            default:
                break;
        }
    }
}
