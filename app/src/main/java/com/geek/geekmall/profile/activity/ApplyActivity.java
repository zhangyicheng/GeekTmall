package com.geek.geekmall.profile.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.Order;
import com.geek.geekmall.bean.OrderProduct;
import com.geek.geekmall.bean.ReFund;
import com.geek.geekmall.bean.ReFundType;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.PhotoPathData;
import com.geek.geekmall.profile.adapter.PhotoUpLoadAdapter;
import com.geek.geekmall.profile.adapter.TypeListAdapter;
import com.geek.geekmall.utils.CameraUtil;
import com.geek.geekmall.utils.ImageUtils;
import com.geek.geekmall.views.CameraDialog;
import com.geek.geekmall.views.NoScrollGridView;
import com.geek.geekmall.views.ReFundReasonDialog;
import com.geek.geekmall.views.ReFundTypeDialog;
import com.geek.geekmall.views.SureDialog;
import com.geek.geekmall.views.ToastView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 7/1/15.
 */
public class ApplyActivity extends BaseActivity implements View.OnClickListener {
    private TextView mCameraView;
    private TextView mTypeView;
    private TextView mReasonView;
    private TextView mMoneyView;
    private TextView mIntroView;
    private TextView mBackView;
    private PhotoUpLoadAdapter mAdapter;
    private TextView mCommitView;
    private NoScrollGridView listView = null;
    private List<String> names;//照片名带后缀
    private List<String> mImages;
    private CameraDialog mDialog;
    private Uri photoUri;
    private TypeListAdapter mReFundAdapter;
    private SureDialog sureDialog;
    private User user;
    private String userId = "";
    private String orderId = "";
    private Order order;
    private OrderProduct orderProduct;
    private ReFundTypeDialog mReFundTypeDialog;
    private ReFundReasonDialog mReFundReasonDialog;
    private ReFundType mReFundType;
    private ReFundType mReason;
    private StringBuilder mPath;
    private ReFund mReFund;
private float totalPrice;
    private String returnMoney="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_layout);
        mReFundAdapter = new TypeListAdapter(this);
        orderId = getIntent().getStringExtra("goodsOrderId");
        totalPrice = getIntent().getFloatExtra("totalPrice",0f);
        order = (Order) getIntent().getSerializableExtra("order");
        mReFund = (ReFund) getIntent().getSerializableExtra("refund");
        orderProduct= (OrderProduct) getIntent().getSerializableExtra("orderProduct");
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
        }
        mImages = new ArrayList<>();
        mPath = new StringBuilder();
        boolean isContainRefund=true;
        if (order != null){
            for (int i=0;i<order.getGoodsList().size();i++){
                if (order.getGoodsList().get(i).getRefundStatus() == 0&&!order.getGoodsList().get(i).getGoodsOrderId().equals(orderId)){
                    isContainRefund = false;
                    break;
                }
            }

            if (isContainRefund){
                totalPrice = order.getPostage() +totalPrice;
            }
        } else {
            totalPrice = orderProduct.getTotalPrice();
        }

        init();
    }

    private void init() {
        mCommitView = (TextView) findViewById(R.id.commit);
        mBackView = (TextView) findViewById(R.id.back);
        listView = (NoScrollGridView) findViewById(R.id.grid_view);
        mAdapter = new PhotoUpLoadAdapter(this);
        listView.setAdapter(mAdapter);

        mCameraView = (TextView) findViewById(R.id.camera);
        mTypeView = (TextView) findViewById(R.id.type);
        mReasonView = (TextView) findViewById(R.id.reseaon);
        mMoneyView = (TextView) findViewById(R.id.money);
        mIntroView = (TextView) findViewById(R.id.intro);
        mMoneyView.setHint("最多退款 " + totalPrice);
        mCommitView.setOnClickListener(this);
        mCameraView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mTypeView.setOnClickListener(this);
        mReasonView.setOnClickListener(this);
        mMoneyView.setOnClickListener(this);
        mIntroView.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                List<String> paths = mAdapter.getPaths();
                if (paths != null && !paths.isEmpty() && position < paths.size()) {
                    sureDialog = new SureDialog(ApplyActivity.this, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mAdapter.getPaths().remove(position);
                            names.remove(position);
                            mAdapter.notifyDataSetChanged();
                            sureDialog.dismiss();
                        }
                    });
                    sureDialog.show();
                    sureDialog.setTitle("确定删除照片吗？");
                } else {

                    takePhoto();
                }
            }
        });

        mReFundType = new ReFundType(1, "退货退款");
    }

    private void apply() {
        if (mReFundType == null) {
            loadingDialog.dismiss();

            new ToastView(this, "请选择类型").show();
            return;
        }
        if (mReason == null) {
            loadingDialog.dismiss();
            new ToastView(this, "请选择原因").show();
            return;
        }
        if (TextUtils.isEmpty(mIntroView.getText().toString())) {
            loadingDialog.dismiss();
            new ToastView(this, "请描述具体问题").show();
            return;
        }
        if (!TextUtils.isEmpty(mMoneyView.getText().toString())){
            if (Float.valueOf(mMoneyView.getText().toString())>totalPrice){
                loadingDialog.dismiss();
                new ToastView(this, "金额超出限制").show();
                return;
            }
        } else {
//            new ToastView(this, "请输入退款金额").show();
//            return;
            returnMoney ="0";
        }

        APIControl.getInstance().apply(this, userId, orderId, returnMoney, mReFundType.getId(), mReason.getId(),
                mIntroView.getText().toString(), mPath.toString(), new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        loadingDialog.dismiss();
                        if (commonData.getStatus() == 200) {
                            Intent intent = new Intent("com.geektmall.apply.success");
                            intent.putExtra("goodsOrderId", orderId);
                            sendBroadcast(intent);
                            new ToastView(ApplyActivity.this, "申请成功").show();
                            finish();
                        } else {
                            new ToastView(ApplyActivity.this, commonData.getErrorMsg()).show();
                        }
                    }
                }, errorListener(""));
    }

    private void uploadPhoto() {

        APIControl.getInstance().uploadPhoto(this, mImages, "app/user/photo", new DataResponseListener<PhotoPathData>() {
            @Override
            public void onResponse(PhotoPathData commonData) {
                if (commonData.getStatus() == 200 && !commonData.getData().isEmpty()) {
//                    updatePhoto(commonData.getData().get(0));
                    for (int i = 0; i < commonData.getData().size(); i++) {
                        mPath.append(commonData.getData().get(i)).append(",");
                    }
                    mPath.deleteCharAt(mPath.length() - 1);
                    if (mReFund != null) {
                        update();
                    } else {
                        apply();
                    }
                } else {

                    new ToastView(ApplyActivity.this, commonData.getErrorMsg()).show();
                }
                mImages.clear();

            }
        }, errorListener(""));
    }

    private void update() {
        APIControl.getInstance().updateApply(this, mReFund.getId(), returnMoney, mReFundType.getId(), mReason.getId(),
                mIntroView.getText().toString(), mPath.toString(), new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        loadingDialog.dismiss();
                        if (commonData.getStatus() == 200) {
                            new ToastView(ApplyActivity.this, "修改申请成功").show();
                            finish();
                        } else {
                            new ToastView(ApplyActivity.this, commonData.getErrorMsg()).show();
                        }
                            mImages.clear();

                    }
                }, errorListener(""));
    }

    private void takePhoto() {
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
                photoUri = null;
                photoUri = CameraUtil.takePhoto(ApplyActivity.this, photoUri);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    private void commit() {
        loadingDialog.show();
        if (mReFundType == null) {
            loadingDialog.dismiss();

            new ToastView(this, "请选择类型").show();
            return;
        }
        if (mReason == null) {
            loadingDialog.dismiss();
            new ToastView(this, "请选择原因").show();
            return;
        }
        if (TextUtils.isEmpty(mIntroView.getText().toString())) {
            loadingDialog.dismiss();
            new ToastView(this, "请描述具体问题").show();
            return;
        }
        if (!TextUtils.isEmpty(mMoneyView.getText().toString())){
            if (Float.valueOf(mMoneyView.getText().toString())>totalPrice){
                loadingDialog.dismiss();
                new ToastView(this, "金额超出限制").show();
                return;
            }
        } else {
            returnMoney = "0";
        }

        if (names == null || names.isEmpty()) {
            if (mReFund != null) {
                update();
            } else {
                apply();
            }
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (names != null) {
                        for (int i = 0; i < names.size(); i++) {
                            Bitmap bitmap = null;
                            try {
                                bitmap = BitmapFactory.decodeStream(new FileInputStream(names.get(i)));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            ByteArrayOutputStream temp = ImageUtils.comp1(bitmap, 480, 480);

                            mImages.add(ImageUtils.bitmapToBase64(temp));
//                        if (i < names.size() - 1) {
//                            mImages.append(",");
//                        }
                            uploadPhoto();
                        }
                    }

                }
            }).start();

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            listView.setVisibility(View.VISIBLE);
            mCameraView.setVisibility(View.GONE);
            if (null != data && data.getData() != null) {
                photoUri = data.getData();
            }
//            Uri selectedImage =
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(photoUri, filePathColumns, null, null, null);
            if (c != null){
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String picturePath = c.getString(columnIndex);
                if (names == null) {
                    names = new ArrayList<String>();
                }
                names.add(picturePath);
                mAdapter.addPath(picturePath);

                c.close();
            } else {
                new ToastView(this,"请选择本地照片").show();
            }

//            String name = picturePath.substring(picturePath.lastIndexOf("/") + 1, picturePath.length());


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
            case R.id.commit:
//                apply();
//                uploadPhoto();
                commit();
                break;


            case R.id.back:
                finish();
                break;
            case R.id.type:
                mReFundTypeDialog = new ReFundTypeDialog(this);
                mReFundTypeDialog.setListener(new ReFundTypeDialog.ReFundTypeListener() {
                    @Override
                    public void setType(ReFundType type) {
                        mReFundType = type;
                        mTypeView.setText(type.getName());
                        mReFundTypeDialog.dismiss();
                    }
                });
                mReFundTypeDialog.show();
                break;
            case R.id.reseaon:
                mReFundReasonDialog = new ReFundReasonDialog(this);
                mReFundReasonDialog.setListener(new ReFundReasonDialog.ReFundTypeListener() {
                    @Override
                    public void setType(ReFundType type) {
                        mReason = type;
                        mReasonView.setText(type.getName());
                        mReFundReasonDialog.dismiss();
                    }

                });
                mReFundReasonDialog.show();
                break;
            case R.id.money:
                break;
            case R.id.intro:
                break;
            case R.id.camera:
                takePhoto();
                break;
            default:
                break;
        }
    }
}
