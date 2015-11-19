package com.geek.geekmall.register;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.geek.geekmall.Constant;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.activity.MainActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.control.SmsContent;
import com.geek.geekmall.http.ApiParams;
import com.geek.geekmall.http.GsonRequest;
import com.geek.geekmall.model.UserData;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.views.ToastView;

import java.util.regex.Pattern;

/**
 * Created by apple on 4/22/15.
 */
public class BindAccountActivity extends BaseActivity implements View.OnClickListener {
    private Button mCodeBtn;
    private Button mNextBtn;
    private EditText mPhoneET;
    private EditText mCodeET;
    private TextView mTitleView;
    private ImageView mAvatorView;
    private TextView mNameView;
    private User user;
    private int type;
    private TextView mBackView;
    private TimeCount time;
    private ContentResolver mContentResolver;
    private ContentObserver mObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_view);

        type = getIntent().getIntExtra("type", 3);
        user = (User) getIntent().getSerializableExtra("user");
        mContentResolver = this.getContentResolver();
        mObserver = new SmsContent(this,new Handler(),mCodeET,"");
        mContentResolver.registerContentObserver(Uri.parse(SmsContent.SMS_URI_INBOX), true, mObserver);
        initView();
    }

    private void initView() {
        mBackView = (TextView) findViewById(R.id.back);
        mBackView.setOnClickListener(this);
        mTitleView = (TextView) findViewById(R.id.title);
        mCodeBtn = (Button) findViewById(R.id.code_btn);
        mCodeBtn.setOnClickListener(this);
        mNextBtn = (Button) findViewById(R.id.next);
        mNextBtn.setOnClickListener(this);
        mNameView = (TextView) findViewById(R.id.name);
        mAvatorView = (ImageView) findViewById(R.id.avator);

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
                if (pattern.matcher(mPhoneET.getText().toString()).matches()) {
                    mCodeBtn.setEnabled(true);
                } else {
                    mCodeBtn.setEnabled(false);
                }
            }
        });
        if (user != null){
            mNameView.setText(user.getNickname());
            ImageLoader.getInstance(this).getPicasso().load(user.getImgUrl())
                    .transform(ImageLoader.getInstance(this).new RoundedCornersTransformation(120))
                    .placeholder(R.drawable.avatar_default)
                    .into(mAvatorView);
        }

    }

    private void getCode() {
        loadingDialog.show();
        executeRequest(new GsonRequest<User>(Request.Method.POST, URLs.CODE_URL,
                User.class, null, ApiParams.getCodeParams(this,
                mPhoneET.getText().toString(), "3"), code, errorListener("")));
    }

    Response.Listener code = new Response.Listener<User>() {
        @Override
        public void onResponse(User o) {
            loadingDialog.dismiss();
            if (o.getStatus() == 200) {
                if (time == null) {
                    time = new TimeCount(60000, 1000);
                }
                time.start();
            } else {
                new ToastView(BindAccountActivity.this, o.getErrorMsg()).show();
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

            case R.id.code_btn:
                getCode();
                break;
            case R.id.next:
                authRegister();
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private void authRegister() {
        if (mPhoneET.getText().toString().length() == 0) {
            new ToastView(this, "请输入手机号").show();
            return;
        }
        if (mCodeET.getText().toString().length() == 0) {
            new ToastView(this, "请输入验证码").show();
            return;
        }
        if (user != null){
            APIControl.getInstance().authRegister(this, user.getOpenId(),
                    mPhoneET.getText().toString(), mCodeET.getText().toString(),
                    user.getAccessToken(), type, user.getImgUrl(),
                    user.getNickname(), user.getSex(), new DataResponseListener<UserData>() {
                        @Override
                        public void onResponse(UserData user) {
//                sendBroadcast(new Intent("com.geek.geekmall.action.auth"));
                            if (user.getStatus() != 200) {
                                new ToastView(BindAccountActivity.this, user.getErrorMsg()).show();
                            } else {
                                GeekApplication.setUser(user.getData().getUserInfo());
                                SharedPreUtil.getInstance().putUser(user.getData().getUserInfo());
                                GeekApplication.setAgentInfo(user.getData().getAgentInfo());
                                GeekApplication.setOrderInfo(user.getData().getOrderInfo());
                                GeekApplication.setUserMoney(user.getData().getUserMoney());
//                    sendBroadcast(new Intent("com.geek.geekmall.action.auth.sina"));
//                            Intent intent = new Intent(BindAccountActivity.this, MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
                                sendBroadcast(new Intent("com.geek.geekmall.action.user_login"));
                                finish();
                            }

                        }
                    }, errorListener(""));
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
    @Override
    protected void onDestroy() {
        mContentResolver.unregisterContentObserver(mObserver);
        super.onDestroy();
    }

}
