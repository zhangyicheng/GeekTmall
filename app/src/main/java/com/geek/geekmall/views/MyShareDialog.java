package com.geek.geekmall.views;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.Constant;
import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.SDCardUtils;
import com.geek.geekmall.wxapi.WeiXinConstants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
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
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by apple on 7/15/15.
 */
public class MyShareDialog extends Dialog implements View.OnClickListener {
    private IWXAPI api;
    private Context mContext;
    private Activity mActivity;
    public static Tencent mTencent;
    public static final String mQQAppid = Constant.QQAPPID;
    private int QR_WIDTH = 450;
    private int QR_HEIGHT = 450;
    private String url;
    private String avatorUrl;
    /**
     * 微博微博分享接口实例
     */
    private IWeiboShareAPI mWeiboShareAPI = null;

    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public MyShareDialog(Context context, Activity activity, String url, String avatorUrl) {
        this(context, R.style.Theme_animation);
        mContext = context;
        mActivity = activity;
        this.url = url;
        this.avatorUrl = avatorUrl;
        api = WXAPIFactory.createWXAPI(activity, WeiXinConstants.APP_ID);

        if (mTencent == null) {
            mTencent = Tencent.createInstance(mQQAppid, mContext.getApplicationContext());
        }
        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(activity, SinaConstants.APP_KEY);

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void createQRImage(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            mQrCodeView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private Bitmap bitmap;

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
    public MyShareDialog(Context context, int theme) {
        super(context, theme);
    }

    private TextView mSinaView;
    private TextView mWeiXinFriendlView;
    private TextView mWeiXinCircleView;
    private TextView mQzoneView;
    private TextView mQqFriendView;
    private ImageView mQrCodeView;
    private ImageView mCloseView;
    private ImageView mAvatorView;
    private TextView mNameView;
    private TextView mSaveView;
    private TextView mCopyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myshare_dialog_layout);
        mQrCodeView = (ImageView) findViewById(R.id.qrcode);
        mCloseView = (ImageView) findViewById(R.id.close);
        mAvatorView = (ImageView) findViewById(R.id.avator);
        mNameView = (TextView) findViewById(R.id.name);
        this.mSaveView = ((TextView) findViewById(R.id.save));
        this.mCopyView = ((TextView) findViewById(R.id.copy));
        this.mSinaView = ((TextView) findViewById(R.id.sina_weibo));
        this.mWeiXinFriendlView = ((TextView) findViewById(R.id.weixin_friend));
        this.mWeiXinCircleView = ((TextView) findViewById(R.id.weixin_friend_circle));
        this.mQzoneView = ((TextView) findViewById(R.id.qzone));
        this.mQqFriendView = ((TextView) findViewById(R.id.qq_friends));
        this.mCloseView.setOnClickListener(this);
        this.mSinaView.setOnClickListener(this);
        this.mWeiXinFriendlView.setOnClickListener(this);
        this.mWeiXinCircleView.setOnClickListener(this);
        this.mQzoneView.setOnClickListener(this);
        this.mQqFriendView.setOnClickListener(this);
        mSaveView.setOnClickListener(this);
        mCopyView.setOnClickListener(this);
        if (GeekApplication.isLogin() && !TextUtils.isEmpty(avatorUrl)) {

            if (!avatorUrl.startsWith("http://")) {
                avatorUrl = URLs.IMAGE_URL + avatorUrl;
            }
            ImageLoader.getInstance(mContext).getPicasso().load(avatorUrl)
                    .resize(DensityUtils.dp2px(mContext, 50), DensityUtils.dp2px(mContext, 50))
                    .placeholder(R.drawable.avatar_default)
                    .transform(ImageLoader.getInstance(mContext).new RoundedCornersTransformation(120))
                    .into(mAvatorView);
        }
        if (GeekApplication.isLogin()) {
            mNameView.setText(GeekApplication.getUser().getNickname());
        }
        createQRImage(url);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:

                dismiss();
                break;
            case R.id.sina_weibo:
                sinaShare();
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

            case R.id.save:
                if (bitmap != null) {
                    SDCardUtils.saveBitmap(bitmap);
                    new ToastView(mContext, "保存成功").show();
                }

                break;
            case R.id.copy:
                SDCardUtils.copyText(mContext, this.url);
                new ToastView(mContext, "已复制到剪切板").show();
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
                if (TextUtils.isEmpty(avatorUrl)) {
                    bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
                } else {
                    bmp = ImageLoader.getInstance(mContext).getPicasso().load(avatorUrl).resize(80, 80).get();
                }

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
            msg.title = "让碎片时间产生伟大价值的正品商城，首单免费领取页面！";
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

    class SendSinaShare extends AsyncTask<Integer, String, Bitmap> {
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
            try {
                if (TextUtils.isEmpty(avatorUrl)) {
                    bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
                } else {
                    bmp = ImageLoader.getInstance(mContext).getPicasso().load(avatorUrl).resize(80, 80).get();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            mDialog.dismiss();
            ImageObject imageObject = new ImageObject();
            imageObject.setImageObject(s);
// 2. 初始化从第三方到微博的消息请求
            SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
            // 用transaction唯一标识一个请求
            request.transaction = String.valueOf(System.currentTimeMillis());

            WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
            weiboMessage.textObject = getTextObj();
            weiboMessage.imageObject = imageObject;
            weiboMessage.mediaObject = getWebpageObj();
            request.multiMessage = weiboMessage;

            // 3. 发送请求消息到微博，唤起微博分享界面
            mWeiboShareAPI.sendRequest(mActivity, request);
        }

    }

    private void sinaShare() {
        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
            new ToastView(mContext, "未安装新浪微博").show();
            return;
        }

        new SendSinaShare().execute();
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = "让碎片时间产生伟大价值的正品商城，首单免费领取页面！";
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
            if (TextUtils.isEmpty(avatorUrl)) {
                thumb = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
            } else {
                thumb = ImageLoader.getInstance(mContext).getPicasso().load(avatorUrl).get();
            }

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
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "让碎片时间产生伟大价值的正品商城，首单免费领取页面！");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        if (TextUtils.isEmpty(avatorUrl)) {
            avatorUrl = "http://www.geektmall.net/images/logo.png";
        }
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, avatorUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "极客特购");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
//这条分享消息被好友点击后的跳转URL。
//        bundle.putString(Constants.PARAM_TARGET_URL, "http://connect.qq.com/");
////分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_	SUMMARY不能全为空，最少必须有一个是有值的。
//        bundle.putString(Constants.PARAM_TITLE, "我在测试");
////分享的图片URL
//        bundle.putString(Constants.PARAM_IMAGE_URL,
//                "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
////分享的消息摘要，最长50个字
//        bundle.putString(Constants.PARAM_SUMMARY, "测试");
////手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//        bundle.putString(Constants.PARAM_APPNAME, "??我在测试");
////标识该消息的来源应用，值为应用名称+AppId。
//        bundle.putString(Constants.PARAM_APP_SOURCE, "星期几" + AppId);

        mTencent.shareToQQ(mActivity, params, new BaseUiListener());
    }

    private void qqzoneShare() {
        final Bundle params = new Bundle();
//分享类型
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "极客特购");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "让碎片时间产生伟大价值的正品商城，首单免费领取页面！");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        ArrayList<String> urls = new ArrayList<>();
        if (TextUtils.isEmpty(avatorUrl)) {
            avatorUrl = "http://www.geektmall.net/images/logo.png";
        }
        urls.add(avatorUrl);


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
        mediaObject.description = "让碎片时间产生伟大价值的正品商城，首单免费领取页面！";


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
