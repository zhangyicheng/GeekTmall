package com.geek.geekmall.order.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.LogisticsCompany;
import com.geek.geekmall.bean.ReFund;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.order.adapter.LogisticsNameAdapter;
import com.geek.geekmall.views.CustomListDialog;
import com.geek.geekmall.views.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * 物流信息界面
 * Created by apple on 4/22/15.
 */
public class AddLogisticActivity extends BaseActivity {
    private TextView mBackView;
    private TextView mCompanyView;
    private EditText mNumberEdit;
    private EditText mRemarkEdit;
    private ReFund mReFund;
    private Button mCommitBtn;
    private CustomListDialog mDialog;
    private LogisticsNameAdapter mAdapter;
    private List<LogisticsCompany> mCompanys;
    private LogisticsCompany mSelectCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_logistics);
        mReFund = (ReFund) getIntent().getSerializableExtra("refund");
        init();

    }

    private void init() {
        mNumberEdit = (EditText) findViewById(R.id.number);
        mCompanyView = (TextView) findViewById(R.id.company);
        mBackView = (TextView) findViewById(R.id.back);
        mCommitBtn = (Button) findViewById(R.id.commit);
        mRemarkEdit = (EditText) findViewById(R.id.remark);
        mBackView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCommitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addLogistics();
            }
        });
        mCompanys = new ArrayList<>();
        mCompanys.add(new LogisticsCompany("EMS", "ems"));
        mCompanys.add(new LogisticsCompany("韵达", "yunda"));
        mCompanys.add(new LogisticsCompany("顺丰", "shunfeng"));
        mCompanys.add(new LogisticsCompany("德邦", "debangwuliu"));
        mCompanys.add(new LogisticsCompany("天天", "tiantian"));
        mCompanys.add(new LogisticsCompany("中通", "zhongtong"));
        mCompanys.add(new LogisticsCompany("能达", "ganzhongnengda"));
        mCompanys.add(new LogisticsCompany("申通", "shentong"));
        mCompanys.add(new LogisticsCompany("圆通", "yuantong"));
        mCompanys.add(new LogisticsCompany("汇通", "huitongkuaidi"));
        mCompanys.add(new LogisticsCompany("其他", "qita"));

        mAdapter = new LogisticsNameAdapter(this);
        mAdapter.setmCompanyNames(mCompanys);
        mDialog = new CustomListDialog(this, mAdapter);
        mDialog.setTitle("选择物流");
        mDialog.setListener(new CustomListDialog.ClickListener() {
            @Override
            public void onItemClick(Object obj) {
                mSelectCompany = (LogisticsCompany) obj;
                if (mSelectCompany.getCode().equals("qita")) {
                    mRemarkEdit.setVisibility(View.VISIBLE);
                }
                mCompanyView.setText(mSelectCompany.getName());
                mDialog.dismiss();
            }
        });
        mCompanyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
    }

    private void addLogistics() {
        if (TextUtils.isEmpty(mNumberEdit.getText().toString())) {
            new ToastView(this, "请填写单号").show();
            return;
        }
        if (mSelectCompany == null) {
            new ToastView(this, "请选择物流").show();
            return;
        }
        APIControl.getInstance().addLogistics(this, mReFund.getId(), mNumberEdit.getText().toString(),
                mSelectCompany.getCode(), mRemarkEdit.getText().toString(), new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        if (commonData.getStatus() == 200) {
                            new ToastView(AddLogisticActivity.this, "填写成功").show();
                            finish();
                        } else {
                            new ToastView(AddLogisticActivity.this, commonData.getErrorMsg()).show();
                        }

                    }
                }, errorListener(""));
    }
}
