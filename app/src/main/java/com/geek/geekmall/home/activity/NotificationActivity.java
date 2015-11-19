package com.geek.geekmall.home.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;

/**
 * Created by apple on 9/30/15.
 */
public class NotificationActivity extends BaseActivity {
    private WebView mWebView;
    private TextView mBackView;
    private TextView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        mBackView = (TextView) findViewById(R.id.back);
        mWebView = (WebView) findViewById(R.id.notifyview);
        mTitleView = (TextView) findViewById(R.id.title);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.loadUrl("file:///android_asset/notification.htm");
    }
}
