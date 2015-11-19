package com.geek.geekmall.profile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.register.ProtocalActivity;
import com.geek.geekmall.utils.AppUtils;

/**
 * Created by apple on 4/22/15.
 */
public class AboutActivity extends BaseActivity {
    private TextView mBackView;
    private TextView mAboutView;
    private TextView mProtocalView;
    private TextView mVersionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_view);
        mProtocalView = (TextView) findViewById(R.id.protocol);
        mAboutView = (TextView) findViewById(R.id.about);
        mBackView = (TextView) findViewById(R.id.back);
        mVersionView = (TextView) findViewById(R.id.version);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AboutActivity.this, ProtocalActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
        mProtocalView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(AboutActivity.this, ProtocalActivity.class);
                        intent.putExtra("type",1);
                        startActivity(intent);
                    }
                }
        );
        mVersionView.setText("版本："+AppUtils.getVersionName(this));
    }
}
