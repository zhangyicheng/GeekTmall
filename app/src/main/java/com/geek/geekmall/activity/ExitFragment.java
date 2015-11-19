package com.geek.geekmall.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.geek.geekmall.http.RequestManager;
import com.geek.geekmall.views.LoadingDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 4/24/15.
 */
public class ExitFragment extends BaseFragment {
    private static boolean isExit = false;
    private static boolean hasTask = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isExit == false ) {
                isExit = true;
                Toast.makeText(this, "再按一次后退键退出应用程序", Toast.LENGTH_SHORT).show();
                if(!hasTask) {
                    new Timer().schedule(new ExitTask(), 2000);
                }
            } else {
                MobclickAgent.onKillProcess(this);
                finish();
                System.exit(0);
            }
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    class ExitTask extends TimerTask{

        @Override
        public void run() {
            isExit = false;
            hasTask = true;
        }
    }
}
