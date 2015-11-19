package com.geek.geekmall.profile.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.AgentInfo;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.views.ToastView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by apple on 5/25/15.
 */
public class AgencyAdentityFour extends BaseActivity {
    private Button mCommitButton;
    private EditText mIdentityText;
    private EditText mNameText;
    private EditText mAlipayName;
    private TextView mBackView;
    private AgentInfo mAgentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adentity_three);
        mAgentInfo = (AgentInfo) getIntent().getSerializableExtra("agent");
        initView();
    }

    private void initView() {
        mIdentityText = (EditText) findViewById(R.id.identity);
        mNameText = (EditText) findViewById(R.id.name);
        mAlipayName = (EditText) findViewById(R.id.alipayName);
        mCommitButton = (Button) findViewById(R.id.commit);
        mBackView = (TextView) findViewById(R.id.back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                identity();
            }
        });
        if (mAgentInfo.getIsCheck() == 1) {
            mIdentityText.setEnabled(false);
            mNameText.setEnabled(false);
            mAlipayName.setEnabled(false);
            mCommitButton.setVisibility(View.GONE);
            mIdentityText.setText(mAgentInfo.getIdentity().substring(0,4)+"*********"+mAgentInfo.getIdentity().substring(14));
            mNameText.setText(mAgentInfo.getName());
            mAlipayName.setText(mAgentInfo.getAlipayName());
        }

    }

    private void identity() {
        Pattern pattern = Pattern.compile("\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x)");
        Matcher matcher = pattern.matcher(mIdentityText.getText().toString());
        if (matcher.matches()) {
            if (!TextUtils.isEmpty(mNameText.getText().toString())) {
                APIControl.getInstance().identity(this, GeekApplication.getAgentInfo().getId(),
                        mIdentityText.getText().toString(), mNameText.getText().toString(),mAlipayName.getText().toString(),
                        new DataResponseListener<CommonData>() {

                            @Override
                            public void onResponse(CommonData o) {
                                if (o.getStatus() == 200) {
                                    new ToastView(AgencyAdentityFour.this, "认证成功").show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        }, errorListener(""));
            } else {
                new ToastView(AgencyAdentityFour.this, "姓名不能为空").show();
            }

        } else {
            new ToastView(AgencyAdentityFour.this, "身份证号码错误").show();
        }

    }


}
