package com.geek.geekmall.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.geek.geekmall.R;
import com.geek.geekmall.utils.AppUtils;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.views.RecyclerImageView;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by apple on 4/22/15.
 */
public class WelcomeActivity extends BaseActivity {
    private RecyclerImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.updateOnlineConfig(this);
        AnalyticsConfig.enableEncrypt(false);
        mImageView = new RecyclerImageView(this);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setImageResource(R.drawable.boot_bg);
        setContentView(mImageView);
        startAnimHomeActivity();

    }

    @Override
    protected void onDestroy() {
        mImageView.setImageDrawable(null);
        mImageView = null;
        super.onDestroy();
    }

    private void startHomeActivity() {
        SharedPreferences preferences = SharedPreUtil.getInstance().getSharedPref();
        if (!preferences.getBoolean("isFirst", true)) {
            if (isShowLauncher()) {
                startActivity(new Intent(this, NavigationActivity.class));
                finish();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        } else {

            startActivity(new Intent(this, NavigationActivity.class));
            finish();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst", false);
            String version = AppUtils.getVersionName(this);
            editor.putString("version", version);
            editor.commit();
        }

    }

    /**
     * 检查当前版本和之前版本是否一致，不一致就显示启动界面
     *
     * @return
     */
    public boolean isShowLauncher() {
        SharedPreferences preferences = SharedPreUtil.getInstance().getSharedPref();
        String versionName = AppUtils.getVersionName(this);
        if (versionName != null) {
            if (versionName.equals(preferences.getString("version", "0"))) {
                return false;
            } else {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("version", versionName);
                editor.commit();
                return true;
            }
        }
        return false;
    }

//    private void startHomeActivity() {
//        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }

    private void startAnimHomeActivity() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.boot_alpha_animation);
        mImageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                startHomeActivity();
            }
        });
    }

}
