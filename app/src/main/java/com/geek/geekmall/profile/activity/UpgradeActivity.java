package com.geek.geekmall.profile.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.views.ToastView;

/**
 * Created by apple on 5/25/15.
 */
public class UpgradeActivity extends BaseActivity implements View.OnClickListener {
    private TextView mBackView;
    private Button mCommit1Button;
    private Button mCommit2Button;
    private Button mCommit3Button;
    private Button mCommit4Button;
    //    private int level;
    private User user;
    private String userId;

    private TextView status_lever1;
    private TextView status_lever2;
    private TextView status_lever3;
    private TextView status_lever4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
//        level = getIntent().getIntExtra("level", AgencyActivity.COMMMON);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
        }
        initView();
        registerBroadCast();
    }

    private void initView() {
        mBackView = (TextView) findViewById(R.id.back);
        mCommit2Button = (Button) findViewById(R.id.commit2);
        mCommit3Button = (Button) findViewById(R.id.commit3);
        mCommit4Button = (Button) findViewById(R.id.commit4);
        mCommit2Button.setOnClickListener(this);
        mCommit3Button.setOnClickListener(this);
        mCommit4Button.setOnClickListener(this);
        mBackView.setOnClickListener(this);

        status_lever1 = (TextView) findViewById(R.id.status_lever1);
        status_lever2 = (TextView) findViewById(R.id.status_lever2);
        status_lever3 = (TextView) findViewById(R.id.status_lever3);
        status_lever4 = (TextView) findViewById(R.id.status_lever4);

//        mNameView = (TextView) findViewById(R.id.name);
//        mCancelButton = (ImageButton) findViewById(R.id.cancel);
//        mCommitButton.setOnClickListener(this);
//        mCancelButton.setOnClickListener(this);
//        if (level == AgencyActivity.COMMMON) {
//            mAvatorView.setBackgroundResource(R.drawable.common_upgrade_avator);
//        } else if (level == AgencyActivity.GOLDEN) {
//            mAvatorView.setBackgroundResource(R.drawable.golden_upgrade_avator);
//        } else {
//            mAvatorView.setBackgroundResource(R.drawable.diament_upgrade_avator);
//        }
        if (GeekApplication.getAgentInfo().getLevel() == 1){
            status_lever1.setVisibility(View.VISIBLE);
        } else
        if (GeekApplication.getAgentInfo().getLevel() == 2){
            status_lever2.setVisibility(View.VISIBLE);
            mCommit2Button.setVisibility(View.GONE);
        } else if(GeekApplication.getAgentInfo().getLevel() == 3){
            status_lever3.setVisibility(View.VISIBLE);
            mCommit2Button.setVisibility(View.GONE);
            mCommit3Button.setVisibility(View.GONE);
        }else if(GeekApplication.getAgentInfo().getLevel() == 4){
            status_lever4.setVisibility(View.VISIBLE);
            mCommit2Button.setVisibility(View.GONE);
            mCommit3Button.setVisibility(View.GONE);
            mCommit4Button.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.cancel:
                finish();
                break;
            case R.id.commit2:
                if (GeekApplication.getAgentInfo().getLevel() >= 2) {
                    if (GeekApplication.getAgentInfo().getLevel() == 2) {
                        new ToastView(this, "您已经是签约掌柜").show();
                    } else if (GeekApplication.getAgentInfo().getLevel() == 3) {
                        new ToastView(this, "您已经是大掌柜").show();
                    } else if (GeekApplication.getAgentInfo().getLevel() == 4) {
                        new ToastView(this, "您已经是代理商").show();
                    }

                } else {
                    Intent intent = new Intent(this, PayTypeActivity.class);
                    intent.putExtra("money", 2);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                }

                break;
            case R.id.commit3:
                if (GeekApplication.getAgentInfo().getLevel() >= 3) {
                    if (GeekApplication.getAgentInfo().getLevel() == 3) {
                        new ToastView(this, "您已经是大掌柜").show();
                    } else if (GeekApplication.getAgentInfo().getLevel() == 4) {
                        new ToastView(this, "您已经是代理商").show();
                    }

                } else {
                    Intent intent = new Intent(this, PayTypeActivity.class);
                    intent.putExtra("money", 3);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                }

                break;
            case R.id.commit4:
                if (GeekApplication.getAgentInfo().getLevel() >= 4) {
                    new ToastView(this, "您已经是代理商").show();

                } else {
                    Intent intent = new Intent(this, PayTypeActivity.class);
                    intent.putExtra("money", 4);
                    intent.putExtra("type", 2);
                    startActivity(intent);
                }

                break;

            default:
                break;
        }
    }

    private MyBrooadCast mMyBroadCast;

    private class MyBrooadCast extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.geekmall.upgrade".equals(intent.getAction())) {
                finish();
            }

        }

    }

    private void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.geektmall.upgrade");

        mMyBroadCast = new MyBrooadCast();
        registerReceiver(mMyBroadCast, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMyBroadCast);
        super.onDestroy();
    }
}
