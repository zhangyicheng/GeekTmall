package com.geek.geekmall.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;

/**
 * 首单免费说明
 * Created by apple on 9/21/15.
 */
public class ShouDanActivity extends BaseActivity {
    private ImageButton cancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoudan);
        cancelBtn = (ImageButton) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
