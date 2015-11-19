package com.geek.geekmall.profile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;

/**
 * Created by apple on 7/15/15.
 */
public class AgencyIntro extends BaseActivity{
    private TextView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agency_intro_layout);

        mBack = (TextView) findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
