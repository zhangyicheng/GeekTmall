package com.geek.geekmall.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.geekmall.Constant;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Address;
import com.geek.geekmall.bean.Area;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.AddressData;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.views.ToastView;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by apple on 4/22/15.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private EditText mAddress;
    private EditText mCode;
    private EditText mPhone;
    private EditText mName;
    private TextView mArea;
    private TextView mBack;
    private Button mCommit;
    private User user;
    private String userId;
    private String token;
    private Address address;
    private List<Area> mAreas;
    private CheckBox isDefault;
    private int defaultAddress = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address_view);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        address = (Address) getIntent().getSerializableExtra("address");
        initView();
    }

    private void initView() {
        isDefault = (CheckBox) findViewById(R.id.isDefault);
        mAddress = (EditText) findViewById(R.id.address);
        mCode = (EditText) findViewById(R.id.code);
        mPhone = (EditText) findViewById(R.id.phone);
        mName = (EditText) findViewById(R.id.name);
        mArea = (TextView) findViewById(R.id.area);
        mBack = (TextView) findViewById(R.id.back);
        mCommit = (Button) findViewById(R.id.save);
        mCommit.setOnClickListener(this);
        mArea.setOnClickListener(this);
        mBack.setOnClickListener(this);
//        mPhone.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER){
//                    saveAddress();
//                }
//                return false;
//            }
//        });
        mAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    saveAddress();
                }
                return false;
            }
        });
        if (address != null) {
            mAddress.setText(address.getAddress());
            mPhone.setText(address.getContactPhone());
            mName.setText(address.getContactName());
            mArea.setText(address.getProvinceName() + address.getCityName() + address.getZoneName());
        }
    }

    private boolean isContinue() {
        if (mName.getText().toString().isEmpty()) {
            new ToastView(this, "姓名不能为空").show();
            return false;
        }
        if (mPhone.getText().toString().isEmpty()) {
            new ToastView(this, "手机号码不能为空").show();
            return false;
        }
        Pattern pattern = Pattern.compile(Constant.MOBILE_REG);
        if (!pattern.matcher(mPhone.getText().toString()).matches()) {
            new ToastView(this, "手机号码格式不正确").show();
            return false;
        }


        if (mAreas == null && address == null) {
            new ToastView(this, "请选择城市").show();
            return false;
        }
        if (mAddress.getText().toString().isEmpty()) {
            new ToastView(this, "地址不能为空").show();
            return false;
        }
        return true;
//        if (mCode.getText().toString().isEmpty()) {
//            new ToastView(this, "邮编不能为空").show();
//            return;
//        }

    }

    private void saveAddress() {
        if (isContinue()) {
            if (mAreas != null) {
                if (isDefault.isChecked()) {
                    defaultAddress = 1;
                } else {
                    defaultAddress = 0;
                }
                APIControl.getInstance().saveAddress(this, userId, token,
                        mAreas.get(0).getId(), mAreas.get(1).getId(), mAreas.get(2).getId(), mAddress.getText().toString(),
                        mName.getText().toString(), mPhone.getText().toString(), defaultAddress,
                        new DataResponseListener<AddressData>() {
                            @Override
                            public void onResponse(AddressData o) {
                                if (o.getStatus() == 200) {
                                    Intent intent = new Intent();
//                                    Address address = new Address();
//                                    address.setContactName(mName.getText().toString());
//                                    address.setContactPhone(mPhone.getText().toString());
//                                    address.setAddress(mAddress.getText().toString());
                                    intent.putExtra("address", o.getData());
                                    setResult(RESULT_OK, intent);
                                    new ToastView(AddAddressActivity.this, "修改成功").show();
                                    finish();

                                }
                            }
                        }, errorListener(""));
            }
        }
    }

    private void updateAddress() {
        if (isContinue()) {
            if (mAreas == null) {
                APIControl.getInstance().updateAddress(this, address.getId(),userId, token,
                        address.getProvinceId(), address.getCityId(), address.getZoneId(), mAddress.getText().toString(),
                        mName.getText().toString(), mPhone.getText().toString(), defaultAddress,
                        new DataResponseListener<AddressData>() {
                            @Override
                            public void onResponse(AddressData o) {
                                if (o.getStatus() == 200) {
                                    Intent intent = new Intent();
//                                    Address address = new Address();
//                                    address.setContactName(mName.getText().toString());
//                                    address.setContactPhone(mPhone.getText().toString());
//                                    address.setAddress(mAddress.getText().toString());
                                    intent.putExtra("address", o.getData());
                                    setResult(RESULT_OK, intent);
                                    new ToastView(AddAddressActivity.this, "修改成功").show();
                                    finish();

                                }
                            }
                        }, errorListener(""));
            } else {
                APIControl.getInstance().updateAddress(this, address.getId(), user.getId(), user.getToken(),
                        mAreas.get(0).getId(), mAreas.get(1).getId(), mAreas.get(2).getId(), mAddress.getText().toString(),
                        mName.getText().toString(), mPhone.getText().toString(), 1,
                        new DataResponseListener<AddressData>() {
                            @Override
                            public void onResponse(AddressData o) {
                                if (o.getStatus() == 200) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        }, errorListener(""));
            }


        }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (address != null) {
                    updateAddress();
                } else {
                    saveAddress();
                }
                break;
            case R.id.area:
                startActivityForResult(new Intent(this, SelectAddressActivity.class), 10022);

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
        if (RESULT_OK == resultCode) {
            if (requestCode == 10022) {
                mAreas = (List<Area>) data.getSerializableExtra("areas");
                String address = "";
                for (Area area : mAreas) {
                    address += area.getDisplayName();
                }
                mArea.setText(address);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
