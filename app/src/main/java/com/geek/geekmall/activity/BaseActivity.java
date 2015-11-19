package com.geek.geekmall.activity;

import android.app.Activity;
import android.os.Bundle;

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
import com.umeng.analytics.MobclickAgent;

/**
 * Created by apple on 5/27/15.
 */
public class BaseActivity extends Activity {
    protected final String TAG = this.getClass().getSimpleName();
    protected LoadingDialog loadingDialog;
    public static final int SIZE = 20;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
        mQueue = RequestManager.getRequestQueue();
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener(final String url) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                error.printStackTrace();
                if (error.networkResponse == null) {
                    if (NetUtils.isConnected(BaseActivity.this)) {
                        new ToastView(BaseActivity.this, "服务器繁忙").show();
                    } else {
//                        getFromDiskCache(url);
                        new ToastView(BaseActivity.this, "网络不给力，请检查一下").show();
                    }

                } else {

                }
            }

        };
    }

    private void getFromDiskCache(String url) {
        if (mQueue.getCache().get(url) != null) {
            String str = new String((mQueue.getCache().get(url).data));
            MyLog.debug(BaseActivity.class, str);

        } else {
            MyLog.debug(BaseActivity.class, "没有缓存数据");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.getInstance(this).getPicasso().cancelTag(this);
        RequestManager.cancelAll(this);
    }

    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}
