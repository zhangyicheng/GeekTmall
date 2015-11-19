package com.geek.geekmall.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.utils.ImageUtils;

/**
 * 活动说明
 * Created by apple on 9/21/15.
 */
public class AdvertisementActivity extends BaseActivity {
    private ImageButton cancelBtn;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huodong);
        cancelBtn = (ImageButton) findViewById(R.id.cancel);
        mImageView =(ImageView) findViewById(R.id.image);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        mImageView.setImageDrawable(null);
        super.onDestroy();
    }
}
