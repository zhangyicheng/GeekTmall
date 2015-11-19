package com.geek.geekmall.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Address;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.AddressListData;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.profile.adapter.AddressAdapter;
import com.geek.geekmall.views.SureDialog;

/**
 * Created by apple on 4/22/15.
 */
public class AddressActivty extends BaseActivity implements View.OnClickListener {
    private User user;
    private ListView mListView;
    private LinearLayout mEmptyView;
    private TextView mAddView;
    private AddressAdapter mAdapter;
    private TextView mBack;
    private SureDialog mDialog;
    private String userId = "";
    private String token = "";
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_list_view);
        type = getIntent().getIntExtra("type", 0);
        initView();
//        getAddressList();
    }

    @Override
    protected void onResume() {
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        getAddressList();
        super.onResume();
    }

    private void initView() {
        mBack = (TextView) findViewById(R.id.back);
        mAddView = (TextView) findViewById(R.id.button_add_new);
        mListView = (ListView) findViewById(R.id.listview_address);
        mEmptyView = (LinearLayout) findViewById(R.id.empty_address);
        mAddView.setOnClickListener(this);
        mAdapter = new AddressAdapter(this);
        mBack.setOnClickListener(this);
        mListView.setAdapter(mAdapter);
        mAdapter.setListener(new AddressAdapter.LoadListener() {
            @Override
            public void onEdit(Address address) {
                Intent intent = new Intent(AddressActivty.this, AddAddressActivity.class);
                intent.putExtra("address", address);
                startActivityForResult(intent, 10011);
            }

            @Override
            public void onDelete(final Address address) {
                mDialog = new SureDialog(AddressActivty.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                        deleteAddress(address);
                    }
                });
                mDialog.show();

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("address", mAdapter.getItem(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void deleteAddress(final Address address) {
        loadingDialog.show();
        APIControl.getInstance().deleteAddress(this, address.getId(), user.getId(),
                user.getToken(), new DataResponseListener<CommonData>() {

                    @Override
                    public void onResponse(CommonData o) {
                        loadingDialog.dismiss();
                        if (o.getStatus() == 200) {
                            getAddressList();
                        } else {

                        }

                    }
                }, errorListener(""));

    }

    private void getAddressList() {
        loadingDialog.show();
        APIControl.getInstance().getAddressList(this, userId, token,
                new DataResponseListener<AddressListData>() {
                    @Override
                    public void onResponse(AddressListData o) {
                        loadingDialog.dismiss();
                        if (o.getStatus() == 200 && o.getData() != null && !o.getData().getDataList().isEmpty()) {
                            mEmptyView.setVisibility(View.GONE);
                            mAdapter.setmAddresses(o.getData().getDataList());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mEmptyView.setVisibility(View.VISIBLE);
                        }

                    }
                }, errorListener(URLs.GET_ADDRESSLIST_URL));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_new:
                startActivityForResult(new Intent(this, AddAddressActivity.class), 10011);
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 10011) {
//                getAddressList();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
