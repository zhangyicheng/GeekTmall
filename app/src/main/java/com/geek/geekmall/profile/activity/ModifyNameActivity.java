package com.geek.geekmall.profile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.views.ToastView;

import java.io.UnsupportedEncodingException;

/**
 * Created by apple on 6/9/15.
 */
public class ModifyNameActivity extends BaseActivity implements View.OnClickListener {
    private EditText mNickName;
    private TextView mSaveView;
    private User user;
    private TextView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_name);
        user = GeekApplication.getUser();
        mBack = (TextView) findViewById(R.id.back);
        mNickName = (EditText) findViewById(R.id.nickname);
        mSaveView = (TextView) findViewById(R.id.save);
        mSaveView.setOnClickListener(this);
        mNickName.setText(user.getNickname());
        mBack.setOnClickListener(this);
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
                updateName();
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    private void updateName() {
        try {
            String name=new String(mNickName.getText().toString().getBytes("gbk"),"iso-8859-1");
            MyLog.debug(ModifyNameActivity.class,name.length()+"-------");
            if (name.length()<=20){
                APIControl.getInstance().modifyUserInfo(this, user.getId(), "",
                        mNickName.getText().toString(), user.getSex(),
                        new DataResponseListener<User>() {
                            @Override
                            public void onResponse(User o) {
                                if (o.getStatus() == 200) {
                                    new ToastView(ModifyNameActivity.this, "修改成功").show();
                                    GeekApplication.setUser(o.getData());
                                    SharedPreUtil.getInstance().putUser(o.getData());
                                    setResult(RESULT_OK);
                                    finish();
                                } else {
                                    new ToastView(ModifyNameActivity.this, o.getErrorMsg()).show();
                                }


                            }
                        }, errorListener(""));
            } else {
                new ToastView(this,"长度不能超过20个字符").show();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
