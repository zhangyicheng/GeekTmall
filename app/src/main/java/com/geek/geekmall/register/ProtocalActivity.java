package com.geek.geekmall.register;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;

/**
 * Created by apple on 8/24/15.
 */
public class ProtocalActivity extends BaseActivity {
    private WebView mWebView;
    private TextView mBackView;
    private TextView mTitleView;
private int type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protocal_layout);
        mBackView = (TextView) findViewById(R.id.back);
        mWebView = (WebView) findViewById(R.id.helpview);
        mTitleView = (TextView) findViewById(R.id.title);
        type = getIntent().getIntExtra("type",0);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (type ==1){
            mTitleView.setText("使用条款和协议");
            mWebView.loadUrl("file:///android_asset/register.htm");
        } else if (type ==2){
            mTitleView.setText("关于极客特购");
            mWebView.loadUrl("file:///android_asset/about.htm");
        }else{
            mWebView.loadUrl("file:///android_asset/register.htm");
        }

    }
}
