package com.geek.geekmall.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.activity.MainActivity;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.http.ApiParams;
import com.geek.geekmall.http.GsonRequest;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.UserData;
import com.geek.geekmall.utils.MD5;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.views.ToastView;

/**
 * Created by apple on 4/22/15.
 */
public class RegisterPwdActivity extends BaseActivity implements OnClickListener {
    private Button mRegisterBtn;
    private EditText mPasswordET;
    private EditText mPasswordTwoET;
    private String phone;
    private TextView mTitleView;
    private TextView mBackView;
    private TextView mProtocal;
    /**
     * 1注册
     * 2忘记密码
     */
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_password_view);
        initView();
        phone = getIntent().getStringExtra("phone");
        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {
            mTitleView.setText("注册");
        } else if (type == 2) {
            mTitleView.setText("忘记密码");
        }

    }

    private void initView() {
        mProtocal = (TextView) findViewById(R.id.protocol);
        mBackView = (TextView) findViewById(R.id.back);
        mPasswordET = (EditText) findViewById(R.id.password);
        mPasswordTwoET = (EditText) findViewById(R.id.passwordtwo);
        mTitleView = (TextView) findViewById(R.id.title);
        mRegisterBtn = (Button) findViewById(R.id.register);
        mRegisterBtn.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mProtocal.setOnClickListener(this);
    }

    private void register() {
        if (mPasswordET.getText().toString().length() == 0) {
            new ToastView(this, "请输入密码").show();
            return;
        }
        if (mPasswordET.getText().toString().length() < 6 || mPasswordET.getText().toString().length() > 16) {
            new ToastView(this, "请输入6-16位密码").show();
            return;
        }
        if (mPasswordTwoET.getText().toString().length() == 0) {
            new ToastView(this, "请输入确认密码").show();
            return;
        }
        if (!mPasswordET.getText().toString().equals(mPasswordTwoET.getText().toString())) {
            new ToastView(this, "密码不一致，请重新输入").show();
            return;
        }
        loadingDialog.show();
        executeRequest(new GsonRequest<UserData>(Request.Method.POST, URLs.REGISTER_URL,
                UserData.class, null,
                ApiParams.getRegisterParams(this, phone,
                        MD5.hexdigest(mPasswordET.getText().toString()),
                        MD5.hexdigest(mPasswordTwoET.getText().toString())),
                register, errorListener("")));
    }

    Response.Listener register = new Response.Listener<UserData>() {
        @Override
        public void onResponse(UserData o) {
            loadingDialog.dismiss();
            MyLog.debug(RegisterPwdActivity.class, o.getStatus() + "---" + o.getErrorMsg());
            if (o.getStatus() == 200) {
                o.getData().getUserInfo().setPhone(phone);
                o.getData().getUserInfo().setPassword(MD5.hexdigest(mPasswordET.getText().toString()));
                GeekApplication.setUser(o.getData().getUserInfo());
                GeekApplication.setAgentInfo(o.getData().getAgentInfo());
                GeekApplication.setOrderInfo(o.getData().getOrderInfo());
                SharedPreUtil.getInstance().putUser(o.getData().getUserInfo());
                sendBroadcast(new Intent("com.geek.geekmall.action.user_login"));
//                sendBroadcast(new Intent("com.geek.geekmall.action.register"));
//                finish();
                Intent intent = new Intent(RegisterPwdActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    private void forgetPwd() {
        if (mPasswordET.getText().toString().length() == 0) {
            new ToastView(this, "请输入密码").show();
            return;
        }
        if (mPasswordTwoET.getText().toString().length() == 0) {
            new ToastView(this, "请输入确认密码").show();
            return;
        }
        loadingDialog.show();
        APIControl.getInstance().forgetPwd(this, phone,
                mPasswordET.getText().toString(),
                mPasswordTwoET.getText().toString(),
                new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        loadingDialog.dismiss();
                        if (commonData.getStatus() == 200) {
                            sendBroadcast(new Intent("com.geek.geekmall.action.forgetpassword"));
                            new ToastView(RegisterPwdActivity.this, "成功找回密码").show();
                            finish();
                        } else {
                            new ToastView(RegisterPwdActivity.this, commonData.getErrorMsg()).show();
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

            case R.id.register:
                if (type == 1) {
                    register();
                } else {
                    forgetPwd();
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.protocol:
                startActivity(new Intent(this, ProtocalActivity.class));
                break;
            default:
                break;
        }
    }
}
