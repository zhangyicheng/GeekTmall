package com.geek.geekmall.profile.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.http.ApiParams;
import com.geek.geekmall.http.GsonRequest;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.views.ToastView;

/**
 * Created by apple on 7/30/15.
 */
public class PayPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button mCommitBtn;
    private Button mCodeBtn;
    private TextView mPhoneET;
    private EditText mCodeET;

    private EditText mPasswordET;
    private EditText mPasswordTwoET;
    private TimeCount time;
    private User user;
    private String userId;
    private TextView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_password_layout);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
        }
        init();
    }

    private void init() {
        mBackView = (TextView) findViewById(R.id.back);
        mPhoneET = (TextView) findViewById(R.id.phone);

        mCodeET = (EditText) findViewById(R.id.code);
        mCodeBtn = (Button) findViewById(R.id.code_btn);
        mCodeBtn.setOnClickListener(this);
        if (user != null) {
            mPhoneET.setText(user.getPhone());
            mCodeBtn.setEnabled(true);
        }
        mPasswordET = (EditText) findViewById(R.id.pay_password);
        mPasswordTwoET = (EditText) findViewById(R.id.pay_password_two);
        mCommitBtn = (Button) findViewById(R.id.commit);
        mCommitBtn.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mPasswordTwoET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    changePayPassword();
                }
                return true;
            }
        });

    }

    private void changePayPassword() {

        if (mPhoneET.getText().toString().length() == 0) {
            new ToastView(this, "请输入手机号").show();
            return;
        }
        if (mCodeET.getText().toString().length() == 0) {
            new ToastView(this, "请输入新验证码").show();
            return;
        }
        if (mPasswordET.getText().toString().length() == 0 || mPasswordET.getText().toString().length() <6) {
            new ToastView(this, "请输入6位支付密码").show();
            return;
        }

        if (!mPasswordTwoET.getText().toString().equals(mPasswordTwoET.getText().toString())) {
            new ToastView(this, "密码不一致，请重新输入").show();
            return;
        }
        loadingDialog.show();
        APIControl.getInstance().changePayPwd(this, userId, mPhoneET.getText().toString(),
                mCodeET.getText().toString(),
                mPasswordET.getText().toString(),
                mPasswordTwoET.getText().toString(),
                new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        if (commonData.getStatus() == 200) {
                            finish();
                        } else {
                            new ToastView(PayPasswordActivity.this, commonData.getErrorMsg()).show();
                        }
                    }
                }, errorListener(""));
    }

    private void getCode() {
        if (mPhoneET.getText().toString().length() == 0) {
            new ToastView(this, "请输入手机号").show();
            return;
        }

        loadingDialog.show();
        executeRequest(new GsonRequest<CommonData>(Request.Method.POST, URLs.CODE_URL,
                CommonData.class, null, ApiParams.getCodeParams(this,
                mPhoneET.getText().toString(), "3"), code, errorListener("")));
    }

    @Override
    protected Response.ErrorListener errorListener(String url) {
        loadingDialog.dismiss();
        return super.errorListener("");
    }

    Response.Listener code = new Response.Listener<CommonData>() {
        @Override
        public void onResponse(CommonData o) {
            loadingDialog.dismiss();
            if (o.getStatus() == 200) {

            } else {
                new ToastView(PayPasswordActivity.this, o.getErrorMsg()).show();
            }
        }
    };

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
            case R.id.commit:
                changePayPassword();
                break;
            case R.id.code_btn:
                if (time == null) {
                    time = new TimeCount(60000, 1000);
                }
                time.start();
                getCode();
                break;
            default:
                break;
        }
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mCodeBtn.setText("重新获取");
            mCodeBtn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            mCodeBtn.setClickable(false);
            mCodeBtn.setText(millisUntilFinished / 1000 + "s");
        }
    }
}
