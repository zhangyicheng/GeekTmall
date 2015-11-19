package com.geek.geekmall.register;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.geek.geekmall.Constant;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.SmsContent;
import com.geek.geekmall.http.ApiParams;
import com.geek.geekmall.http.GsonRequest;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.views.ToastView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by apple on 4/22/15.
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {
    private Button mCodeBtn;
    private Button mNextBtn;
    private EditText mPhoneET;
    private EditText mCodeET;
    private TextView mTitleView;
    private RegisterBroadCast mBroadCast;
    /**
     * 1注册
     * 2忘记密码
     */
    private int type;
    private TimeCount time;
    private ContentResolver mContentResolver;
    private ContentObserver mObserver;
    private TextView mBackView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);
        initView();
        registerBroadCast();
        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {
            mTitleView.setText("注册");
        } else if (type == 2) {
            mTitleView.setText("忘记密码");
        }
        mContentResolver = this.getContentResolver();
        mObserver = new SmsContent(this,new Handler(),mCodeET,"");
        mContentResolver.registerContentObserver(Uri.parse(SmsContent.SMS_URI_INBOX),true,mObserver);
    }

    private void initView() {
        mBackView = (TextView) findViewById(R.id.back);
        mTitleView = (TextView) findViewById(R.id.title);
        mCodeBtn = (Button) findViewById(R.id.code_btn);
        mCodeBtn.setOnClickListener(this);
        mNextBtn = (Button) findViewById(R.id.next);
        mNextBtn.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mPhoneET = (EditText) findViewById(R.id.phone);
        mCodeET = (EditText) findViewById(R.id.code);
        mPhoneET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Pattern pattern = Pattern.compile(Constant.MOBILE_REG);
                if (pattern.matcher(mPhoneET.getText().toString()).matches()){
                    mCodeBtn.setEnabled(true);
                } else {
                    mCodeBtn.setEnabled(false);
                }
            }
        });
    }

    private void getCode() {
        if (mPhoneET.getText().toString().length() == 0) {
            new ToastView(this,"请输入手机号").show();
            return;
        }

        loadingDialog.show();
        executeRequest(new GsonRequest<CommonData>(Request.Method.POST, URLs.CODE_URL,
                CommonData.class, null, ApiParams.getCodeParams(this,
                mPhoneET.getText().toString(), type + ""), code, errorListener("")));
    }

    Response.Listener code = new Response.Listener<CommonData>() {
        @Override
        public void onResponse(CommonData o) {
            loadingDialog.dismiss();
            if (o.getStatus() == 200){
                if (time == null) {
                    time = new TimeCount(60000, 1000);
                }
                time.start();
            } else {
                new ToastView(RegisterActivity.this, o.getErrorMsg()).show();
            }
        }
    };
    private void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.geek.geekmall.action.user_login");
        filter.addAction("com.geek.geekmall.action.forgetpassword");

        mBroadCast = new RegisterBroadCast();
        registerReceiver(mBroadCast, filter);
    }
    private class RegisterBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.geek.geekmall.action.user_login".equals(intent.getAction())) {
                finish();
            } else if ("com.geek.geekmall.action.forgetpassword".equals(intent.getAction())){
                finish();
            }
        }
    }

    private void checkCode() {
        if (mPhoneET.getText().toString().length() == 0) {
            new ToastView(this,"请输入手机号").show();
            return;
        }
        if (mCodeET.getText().toString().length() == 0) {
            new ToastView(this,"请输入验证码").show();
            return;
        }
        loadingDialog.show();
        executeRequest(new GsonRequest<CommonData>(Request.Method.POST, URLs.CHECKCODE_URL,
                CommonData.class, null, ApiParams.checkCodeParams(this,
                mPhoneET.getText().toString(), mCodeET.getText().toString()), checkCode, errorListener("")));
    }

    Response.Listener checkCode = new Response.Listener<CommonData>() {
        @Override
        public void onResponse(CommonData o) {
            loadingDialog.dismiss();

            if (o.getStatus() == 200) {
                Intent intent = new Intent(RegisterActivity.this, RegisterPwdActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("phone", mPhoneET.getText().toString());
                startActivity(intent);
            } else {
                new ToastView(RegisterActivity.this, o.getErrorMsg()).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadCast);
        mContentResolver.unregisterContentObserver(mObserver);
        super.onDestroy();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.code_btn:
                getCode();
                break;
            case R.id.next:
                checkCode();
                break;
            case R.id.back:
                finish();
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
