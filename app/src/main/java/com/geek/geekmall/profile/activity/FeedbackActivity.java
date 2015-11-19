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
 * 意见反馈
 * Created by apple on 4/22/15.
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener {
    private Button mCommit;
    private TextView mBack;
    private EditText mContent;
    private User user;
    private String userId = "";
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_back);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        init();
    }

    private void init() {
        mBack = (TextView) findViewById(R.id.back);
        mCommit = (Button) findViewById(R.id.send);
        mContent = (EditText) findViewById(R.id.content);
        mCommit.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    feedBack();
                }
                return false;
            }
        });
    }

    private void feedBack() {
        if (mContent.getText() != null && !mContent.getText().toString().isEmpty()) {
            loadingDialog.show();
            APIControl.getInstance().feedback(this, userId, token,
                    mContent.getText().toString(), new DataResponseListener<CommonData>() {
                        @Override
                        public void onResponse(CommonData commonData) {
                            loadingDialog.dismiss();
                            if (commonData.getStatus() == 200) {
                                new ToastView(FeedbackActivity.this, "提交成功").show();
                                finish();
                            } else {
                                new ToastView(FeedbackActivity.this, commonData.getErrorMsg()).show();
                            }
                        }
                    }, errorListener(""));
        } else {
            new ToastView(FeedbackActivity.this, "请输入您的宝贵意见").show();
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
            case R.id.back:
                finish();
                break;
            case R.id.send:
                feedBack();
                break;
            default:
                break;
        }
    }
}
