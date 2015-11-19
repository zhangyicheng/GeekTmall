package com.geek.geekmall.profile.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.views.ToastView;

/**
 * Created by apple on 4/22/15.
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button mCommitBtn;
    private EditText mOldPasswordTwoET;
    private EditText mPasswordET;
    private EditText mPasswordTwoET;
    private User user;
    private String userId;
    private TextView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password);
        initView();
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
        }
    }

    private void initView() {
        mBackView = (TextView) findViewById(R.id.back);
        mOldPasswordTwoET = (EditText) findViewById(R.id.oldpassword);
        mPasswordET = (EditText) findViewById(R.id.newpassword);
        mPasswordTwoET = (EditText) findViewById(R.id.newpasswordtwo);
        mCommitBtn = (Button) findViewById(R.id.commit);
        mCommitBtn.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mPasswordTwoET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    changePassword();
                }
                return true;
            }
        });
    }

    private void changePassword() {

        if (mOldPasswordTwoET.getText().toString().length() == 0) {
            new ToastView(this, "请输入原密码").show();
            return;
        }
        if (mPasswordET.getText().toString().length() < 6 || mPasswordET.getText().toString().length()>16) {
            new ToastView(this, "密码长度不合规范").show();
            return;
        }
        if (mPasswordET.getText().toString().length() < 6 || mPasswordET.getText().toString().length()>16) {
            new ToastView(this, "密码长度不合规范").show();
            return;
        }
        if (!mPasswordET.getText().toString().equals(mPasswordTwoET.getText().toString())) {
            new ToastView(this, "密码不一致，请重新输入").show();
            return;
        }
        loadingDialog.show();
        APIControl.getInstance().changePwd(this, userId, mOldPasswordTwoET.getText().toString(),
                mPasswordET.getText().toString(),
                mPasswordTwoET.getText().toString(),
                new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        loadingDialog.dismiss();
                        if (commonData.getStatus() == 200) {
                            new ToastView(ChangePasswordActivity.this, "修改成功").show();
                            finish();
                        } else{
                            new ToastView(ChangePasswordActivity.this, commonData.getErrorMsg()).show();
                        }

                    }
                }, errorListener(""));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit:
                changePassword();
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
}
