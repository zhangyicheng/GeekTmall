package com.geek.geekmall.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.geek.geekmall.http.RequestManager;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.NetUtils;
import com.geek.geekmall.views.LoadingDialog;
import com.geek.geekmall.views.ToastView;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by apple on 4/24/15.
 */
public class BaseFragment extends FragmentActivity {
    protected LoadingDialog loadingDialog;
    public static final int SIZE=20;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
    }


    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    public Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                error.printStackTrace();
                if (error.networkResponse == null) {
                    if (NetUtils.isConnected(BaseFragment.this)) {
                        new ToastView(BaseFragment.this, "服务器繁忙").show();
                    } else {
                        new ToastView(BaseFragment.this, "网络不给力，请检查一下").show();
                    }

                }
                error.printStackTrace();
            }

        };
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.getInstance(this).getPicasso().cancelTag(this);
        RequestManager.cancelAll(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
