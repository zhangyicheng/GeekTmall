package com.geek.geekmall.profile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.BindStatusData;

/**
 * Created by apple on 7/21/15.
 */
public class BindingActivity extends BaseActivity implements View.OnClickListener {
    private TextView mBackView;
    private ImageView mQQView;
    private ImageView mSinaView;
    private ImageView mWeXinView;
    private String userId;
    private String token;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_layout);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        init();
        getBindStatus();
    }

    private void init() {
        mQQView = (ImageView) findViewById(R.id.qq_bind);
        mSinaView = (ImageView) findViewById(R.id.sina_bind);
        mWeXinView = (ImageView) findViewById(R.id.weixin_bind);

        mBackView = (TextView) findViewById(R.id.back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getBindStatus() {
        APIControl.getInstance().getBindStatus(this, userId, token, new DataResponseListener<BindStatusData>() {
            @Override
            public void onResponse(BindStatusData bindStatusData) {
                if (bindStatusData.getStatus() == 200) {
                    if (bindStatusData.getData().getQqCount() == 1) {
                        mQQView.setBackgroundResource(R.drawable.open);
                    } else {
                        mQQView.setBackgroundResource(R.drawable.close);
                    }
                    if (bindStatusData.getData().getWeiboCount() == 1) {
                        mSinaView.setBackgroundResource(R.drawable.open);
                    } else {
                        mSinaView.setBackgroundResource(R.drawable.close);
                    }
                    if (bindStatusData.getData().getWeixinCount() == 1) {
                        mWeXinView.setBackgroundResource(R.drawable.open);
                    } else {
                        mWeXinView.setBackgroundResource(R.drawable.close);
                    }

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
            case R.id.qq_bind:
                break;
            case R.id.sina_bind:
                break;
            case R.id.weixin_bind:
                break;

        }
    }
}
