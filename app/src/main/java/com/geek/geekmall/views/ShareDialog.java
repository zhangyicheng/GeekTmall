package com.geek.geekmall.views;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.geek.geekmall.Constant;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.wxapi.WeiXinConstants;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.openapi.SinaConstants;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by apple on 7/15/15.
 */
public class ShareDialog extends Dialog implements View.OnClickListener {
    private IWXAPI api;
    private Context mContext;
    private Activity mActivity;
    public static Tencent mTencent;
    public static final String mQQAppid = Constant.QQAPPID;
    /**
     * 微博微博分享接口实例
     */
    private IWeiboShareAPI mWeiboShareAPI = null;
    private String url;
    private String mName;
    private String mImageUrl;

    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public ShareDialog(Context context, String url, String name, String product_url) {
        this(context, R.style.Theme_animation);
        mContext = context;
        mActivity = (Activity) context;
        this.url = url;
        mName = name;
        mImageUrl = product_url;
        api = WXAPIFactory.createWXAPI(mActivity, WeiXinConstants.APP_ID);
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mQQAppid, mContext.getApplicationContext());
        }
        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity, SinaConstants.APP_KEY);

        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
//        if (savedInstanceState != null) {
//            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
//        }

    }


    /**
     * Create a Dialog window that uses a custom dialog style.
     *
     * @param context The Context in which the Dialog should run. In particular, it
     *                uses the window manager and theme from this context to
     *                present its UI.
     * @param theme   A style resource describing the theme to use for the
     *                window. See <a href="{@docRoot}guide/topics/resources/available-resources.html#stylesandthemes">Style
     *                and Theme Resources</a> for more information about defining and using
     *                styles.  This theme is applied on top of the current theme in
     */
    public ShareDialog(Context context, int theme) {
        super(context, theme);
    }

    private TextView mCancelView;
    private TextView mSinaView;
    private TextView mWeiXinFriendlView;
    private TextView mWeiXinCircleView;
    private TextView mQzoneView;
    private TextView mQqFriendView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_dialog_layout);
        Window window = getWindow();
        window.setWindowAnimations(R.style.Theme_animation);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = 80;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DensityUtils.dp2px(getContext(), 300);
        window.setAttributes(params);
        this.mCancelView = (TextView) findViewById(R.id.cancel);
        this.mSinaView = ((TextView) findViewById(R.id.sina_weibo));
        this.mWeiXinFriendlView = ((TextView) findViewById(R.id.weixin_friend));
        this.mWeiXinCircleView = ((TextView) findViewById(R.id.weixin_friend_circle));
        this.mQzoneView = ((TextView) findViewById(R.id.qzone));
        this.mQqFriendView = ((TextView) findViewById(R.id.qq_friends));
        this.mCancelView.setOnClickListener(this);
        this.mSinaView.setOnClickListener(this);
        this.mWeiXinFriendlView.setOnClickListener(this);
        this.mWeiXinCircleView.setOnClickListener(this);
        this.mQzoneView.setOnClickListener(this);
        this.mQqFriendView.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.sina_weibo:
                if (mWeiboShareAPI.isWeiboAppInstalled()) {
                    sinaShare();
                } else {
                    new ToastView(mContext, "未安装新浪微博客户端").show();
                }
                dismiss();
                break;
            case R.id.weixin_friend:
                weiXinShare(2);
                dismiss();
                break;
            case R.id.weixin_friend_circle:
                weiXinShare(1);
                dismiss();
                break;
            case R.id.qzone:
                qqzoneShare();
                dismiss();
                break;
            case R.id.qq_friends:
                qqShare();
                dismiss();
                break;
            default:
                break;
        }
    }

    class SendWxShare extends AsyncTask<Integer, String, Bitmap> {
        private int type;
        private ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            mDialog = ProgressDialog.show(mContext, "", "加载中...");
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            Bitmap bmp = null;
            type = params[0];
            try {
                bmp = ImageLoader.getInstance(mContext).getPicasso().load(URLs.IMAGE_URL + mImageUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            mDialog.dismiss();
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            msg.title = mName;
            msg.description = "极客特购";
            msg.setThumbImage(s);
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("img");
            req.message = msg;
            if (type == 1) {
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
            } else {
                req.scene = SendMessageToWX.Req.WXSceneSession;
            }

            api.sendReq(req);

        }

    }

    private void weiXinShare(final int type) {
        if (api.isWXAppInstalled()) {
            new SendWxShare().execute(type);
        } else {
            new ToastView(mContext, "未安装微信客户端").show();
        }

    }

    private void sinaShare() {
        // 2. 初始化从第三方到微博的消息请求

        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj();
        weiboMessage.imageObject = getImageObj();
        weiboMessage.mediaObject = getWebpageObj();
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(mActivity, request);
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = mName;
        return textObject;
    }


    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mContext.getDrawable(R.drawable.ic_launcher);
        Bitmap thumb = null;
        try {
            thumb = BitmapFactory.decodeStream(new URL(URLs.IMAGE_URL + mImageUrl).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageObject.setImageObject(thumb);
        return imageObject;
    }


    private void qqShare() {

        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "极客特购");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, mName);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, URLs.IMAGE_URL + mImageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "极客特购");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        mTencent.shareToQQ(mActivity, params, new BaseUiListener());
    }

    private void qqzoneShare() {
        final Bundle params = new Bundle();
//分享类型
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "极客特购");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, mName);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, URLs.IMAGE_URL + mImageUrl);
        ArrayList<String> urls = new ArrayList<>();
        urls.add(URLs.IMAGE_URL + mImageUrl);
//        urls.add(URLs.IMAGE_URL + mImageUrl);
//        urls.add(URLs.IMAGE_URL + mImageUrl);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, urls);

        mTencent.shareToQzone(mActivity, params, new BaseUiListener());
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = "极客特购";
        mediaObject.description = mName;

        // 设置 Bitmap 类型的图片到视频对象里

        mediaObject.setThumbImage(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
        mediaObject.actionUrl = url;
        mediaObject.defaultText = "极客特购";
        return mediaObject;
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            //V2.0版本，参数类型由JSONObject 改成了Object,具体类型参考api文档
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onCancel() {
        }

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
