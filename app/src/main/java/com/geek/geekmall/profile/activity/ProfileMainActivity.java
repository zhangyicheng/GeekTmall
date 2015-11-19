package com.geek.geekmall.profile.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.AddressData;
import com.geek.geekmall.model.PhotoPathData;
import com.geek.geekmall.utils.CameraUtil;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.ImageUtils;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.SharedPreUtil;
import com.geek.geekmall.views.CameraDialog;
import com.geek.geekmall.views.SexDialog;
import com.geek.geekmall.views.ToastView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 4/22/15.
 */
public class ProfileMainActivity extends BaseActivity implements View.OnClickListener {
    private TextView mNickName;
    private TextView mAddress;
    private TextView mPhone;
    private TextView mSex;
    private ImageView mAvator;
    private TextView mBackView;
    private Button mLoginOut;
    private User user;
    private Uri photoUri;
    private String picturePath = "file:///sdcard/temp.jpg";
    private String imageFile;
    private CameraDialog mDialog;
    private SexDialog mSexDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);

        photoUri = Uri.parse(picturePath);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = GeekApplication.getUser();
        if(user != null){
            mNickName.setText(user.getNickname());
            mPhone.setText(user.getPhone());
            mSex.setText(user.getSex() == 1 ? "男" : "女");
            getDefaultAdress();
            if (!TextUtils.isEmpty(user.getImgUrl())) {
                String url = user.getImgUrl();
                if (!url.startsWith("http://")) {
                    url = URLs.IMAGE_URL + user.getImgUrl();
                }
                ImageLoader.getInstance(this).getPicasso().load(url)
                        .placeholder(R.drawable.avatar_default)
                        .resize(DensityUtils.dp2px(ProfileMainActivity.this, 80), DensityUtils.dp2px(ProfileMainActivity.this, 80))
                        .transform(ImageLoader.getInstance(this).new RoundedCornersTransformation(120))
                        .into(mAvator);
            }
        }


    }

    private void initView() {
        mBackView = (TextView) findViewById(R.id.back);
        mAvator = (ImageView) findViewById(R.id.avator);
        mNickName = (TextView) findViewById(R.id.nickname);
        mAddress = (TextView) findViewById(R.id.address);
        mPhone = (TextView) findViewById(R.id.phone);
        mSex = (TextView) findViewById(R.id.sex);
        mLoginOut = (Button) findViewById(R.id.login_out);
        mBackView.setOnClickListener(this);
        mLoginOut.setOnClickListener(this);
        mAddress.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        mNickName.setOnClickListener(this);
        mAvator.setOnClickListener(this);
        mSex.setOnClickListener(this);
    }

    private void getDefaultAdress() {
        loadingDialog.show();
        APIControl.getInstance().getDefaultAddress(this, user.getId(), user.getToken(), new DataResponseListener<AddressData>() {
            @Override
            public void onResponse(AddressData addressData) {
                loadingDialog.dismiss();
                if (addressData.getStatus() == 200 && addressData.getData() != null
                        && addressData.getData().getProvinceName() != null
                        && addressData.getData().getCityName() != null) {

                    mAddress.setText(addressData.getData().getProvinceName()
                            + addressData.getData().getCityName()
                            + addressData.getData().getZoneName()
                            + addressData.getData().getAddress());
                } else {
                    mAddress.setText("添加地址");
                }
            }
        }, errorListener(URLs.GET_DEFAULT_ADDRESS_URL));
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.login_out:
                GeekApplication.setUser(null);
                SharedPreUtil.getInstance().DeleteUser();
                sendBroadcast(new Intent("com.geek.geekmall.action.user_logout"));
                finish();
                break;
            case R.id.avator:
                mDialog = new CameraDialog(this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);
                        mDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CameraUtil.takePhoto(ProfileMainActivity.this, photoUri);
                        mDialog.dismiss();
                    }
                });
                mDialog.show();

                break;
            case R.id.phone:
                break;
            case R.id.nickname:
                intent.setClass(this, ModifyNameActivity.class);
                startActivityForResult(intent, 10012);
                break;
            case R.id.address:
                intent.setClass(this, AddressActivty.class);
                startActivity(intent);
                break;
            case R.id.sex:
                mSexDialog = new SexDialog(this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSex.setText("男");
                        updateSex(1);
                        mSexDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSex.setText("女");
                        updateSex(0);
                        mSexDialog.dismiss();
                    }
                });
                mSexDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    CameraUtil.startPhotoZoom(ProfileMainActivity.this, data.getData(), photoUri);
                    break;
                case 2:
                    CameraUtil.startPhotoZoom(ProfileMainActivity.this, photoUri, photoUri);
                    break;
                case 3:
                    if (photoUri != null) {
                        Bitmap photo = ImageUtils.decodeUriAsBitmap(ProfileMainActivity.this, photoUri);
//                        mAvator.setImageBitmap(photo);
                        ImageLoader.getInstance(this).getPicasso().load(photoUri)
                                .placeholder(R.drawable.avatar_default)
                                .resize(DensityUtils.dp2px(ProfileMainActivity.this, 80), DensityUtils.dp2px(ProfileMainActivity.this, 80))
                                .transform(ImageLoader.getInstance(this).new RoundedCornersTransformation(120))
                                .into(mAvator);
                        imageFile = ImageUtils.bitmapToBase64(ImageUtils.comp1(photo, 480, 480));

//                        updatePhoto();
                        uploadPhoto();
                    }


                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadPhoto() {
        List<String> imgSource = new ArrayList<>();
        imgSource.add(imageFile);
        APIControl.getInstance().uploadPhoto(this, imgSource, "app/user/photo", new DataResponseListener<PhotoPathData>() {
            @Override
            public void onResponse(PhotoPathData commonData) {
                if (commonData.getStatus() == 200 && !commonData.getData().isEmpty()) {
                    updatePhoto(commonData.getData().get(0));
                } else {
                    new ToastView(ProfileMainActivity.this, commonData.getErrorMsg()).show();
                }

            }
        }, errorListener(""));
    }

    private void updatePhoto(String path) {
        APIControl.getInstance().modifyUserInfo(this, user.getId(), path,
                "", user.getSex(),
                new DataResponseListener<User>() {
                    @Override
                    public void onResponse(User o) {
                        if (o.getStatus() == 200) {
                            String url = o.getData().getImgUrl();
                            if (!url.startsWith("http://")) {
                                url = URLs.IMAGE_URL + o.getData().getImgUrl();
                            }
                            ImageLoader.getInstance(ProfileMainActivity.this).getPicasso().load(url)
                                    .placeholder(R.drawable.avatar_default)
                                    .resize(DensityUtils.dp2px(ProfileMainActivity.this, 80), DensityUtils.dp2px(ProfileMainActivity.this, 80))
                                    .transform(ImageLoader.getInstance(ProfileMainActivity.this).new RoundedCornersTransformation(120))
                                    .into(mAvator);
                            GeekApplication.setUser(o.getData());
                            sendBroadcast(new Intent("com.geek.geekmall.action.update_photo"));
                            SharedPreUtil.getInstance().putUser(o.getData());
                        } else {
                            new ToastView(ProfileMainActivity.this, o.getErrorMsg()).show();
                        }


                    }
                }, errorListener(""));
    }

    private void updateSex(int sex) {
        MyLog.debug(ProfileMainActivity.class, imageFile);
        APIControl.getInstance().modifyUserInfo(this, user.getId(), "",
                "", sex,
                new DataResponseListener<User>() {
                    @Override
                    public void onResponse(User o) {
                        if (o.getStatus() == 200) {
//                            ImageLoader.getInstance(ProfileMainActivity.this).getPicasso().load(URLs.HOST + o.getData().getImgUrl())
//                                    .placeholder(R.drawable.my_avator)
//                                    .transform(ImageLoader.getInstance(ProfileMainActivity.this).new RoundedCornersTransformation(120))
//                                    .into(mAvator);
                            GeekApplication.setUser(o.getData());
                            SharedPreUtil.getInstance().putUser(o.getData());
                        } else {
                            new ToastView(ProfileMainActivity.this, o.getErrorMsg()).show();
                        }


                    }
                }, errorListener(""));
    }
}
