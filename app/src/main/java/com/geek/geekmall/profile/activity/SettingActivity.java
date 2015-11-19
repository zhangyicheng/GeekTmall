package com.geek.geekmall.profile.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import com.android.volley.Response;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.UpdateData;
import com.geek.geekmall.register.LoginActivity;
import com.geek.geekmall.utils.AppUtils;
import com.geek.geekmall.utils.FileUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.SDCardUtils;
import com.geek.geekmall.views.OptionsItemView;
import com.geek.geekmall.views.ToastView;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by apple on 4/22/15.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private TextView mBack;
    private OptionsItemView mCheckVersion;
    private OptionsItemView mClearCache;
    private OptionsItemView mBanding;
    private OptionsItemView mPasswordLayout;
    private OptionsItemView mPayPasswordLayout;
    private SharedPreferences prefs;
    private DownloadManager downloadManager;
    private static final String DL_ID = "downloadId";
    private String mimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        init();
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Intent activityIntent = new Intent(Intent.ACTION_VIEW);
            Uri path = downloadManager.getUriForDownloadedFile(completeDownloadId);
            activityIntent.setDataAndType(path, mimeString);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(activityIntent);
            } catch (ActivityNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    };

    private void init() {
        mPayPasswordLayout = (OptionsItemView) findViewById(R.id.password);
        mPasswordLayout = (OptionsItemView) findViewById(R.id.pay_password);
        mBack = (TextView) findViewById(R.id.back);
        mClearCache = (OptionsItemView) findViewById(R.id.clear_cache);
        mCheckVersion = (OptionsItemView) findViewById(R.id.version);
        mPayPasswordLayout.setOnClickListener(this);
        mPasswordLayout.setOnClickListener(this);

//        mBanding.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mClearCache.setOnClickListener(this);
        mCheckVersion.setOnClickListener(this);
        mCheckVersion.setContent("当前版本："+AppUtils.getVersionName(this));
        new Calculate().execute();
    }
    class Calculate extends AsyncTask<Integer, String, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mClearCache.setNum("计算中...");
        }

        @Override
        protected Long doInBackground(Integer... params) {
            File cacheDir = new File(SDCardUtils.getSDCardPath(), "geekmall");
            try {
                return FileUtils.getFileSize(cacheDir);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long s) {
            super.onPostExecute(s);
            mClearCache.setNum(FileUtils.FormetFileSize(s));

        }

    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.clear_cache:
                clearCache();
                break;
            case R.id.banding:
                break;
            case R.id.version:
                checkUpdate();
                break;
            case R.id.password:
                if (GeekApplication.isLogin()){
                    startActivity(new Intent(this,ChangePasswordActivity.class));
                } else {
                    startActivity(new Intent(this,LoginActivity.class));
                }
                break;
            case R.id.pay_password:
                if (GeekApplication.isLogin()){
                    startActivity(new Intent(this,PayPasswordActivity.class));
                } else {
                    startActivity(new Intent(this,LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    private void clearCache() {
        File cacheDir = new File(SDCardUtils.getSDCardPath(), "geekmall");
        if (cacheDir != null && cacheDir.exists() && cacheDir.isDirectory()) {
            for (File item : cacheDir.listFiles()) {
                item.delete();
            }
        }
        try {
            mClearCache.setNum(FileUtils.FormetFileSize(FileUtils.getFileSize(cacheDir)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        new ToastView(this,"清除成功").show();
    }

    private void checkUpdate() {
        loadingDialog.show();
        APIControl.getInstance().updateVersion(this, 1 + "", new DataResponseListener<UpdateData>() {
            @Override
            public void onResponse(UpdateData updateData) {
                loadingDialog.dismiss();
                if (updateData.getStatus() == 200 && updateData.getData() != null) {
                    MyLog.debug(SettingActivity.class, AppUtils.getVersionName(SettingActivity.this) + "-------");

                    if (updateData.getData().getVersion() != null && !updateData.getData().getVersion().equals(AppUtils.getVersionName(SettingActivity.this))) {
//                        new DownloadManagerPro(downloadManager)
//                                .download(SettingActivity.this, updateData.getData().getDownUrl());
                        download(updateData.getData().getDownUrl());
                    } else {
                        new ToastView(SettingActivity.this,"暂无更新").show();
                    }
                } else {
                    new ToastView(SettingActivity.this, updateData.getErrorMsg()).show();
                }

            }
        }, errorListener(""));
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected Response.ErrorListener errorListener(String url) {
        loadingDialog.dismiss();
        return super.errorListener(url);
    }

    private void download(String url) {
        //开始下载
        Uri resource = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(resource);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);
        //设置文件类型
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);
        //在通知栏中显示
        request.setShowRunningNotification(true);
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //sdcard的目录下的download文件夹
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "GeekTmall.apk");
        request.setTitle("极客特购");
        downloadManager.enqueue(request);

    }


}
