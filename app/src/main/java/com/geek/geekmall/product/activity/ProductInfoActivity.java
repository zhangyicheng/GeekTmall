package com.geek.geekmall.product.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.geek.geekmall.GeekApplication;
import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.activity.MainActivity;
import com.geek.geekmall.bean.GoodsAttr;
import com.geek.geekmall.bean.GoodsImages;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.User;
import com.geek.geekmall.control.APIControl;
import com.geek.geekmall.control.DataResponseListener;
import com.geek.geekmall.home.activity.SettlementActivity;
import com.geek.geekmall.home.adapter.LikeAdapter;
import com.geek.geekmall.model.BuyData;
import com.geek.geekmall.model.CartSpecData;
import com.geek.geekmall.model.CommentPageData;
import com.geek.geekmall.model.CommonData;
import com.geek.geekmall.model.PhotoData;
import com.geek.geekmall.model.ProductInfoData;
import com.geek.geekmall.model.ProductPageData;
import com.geek.geekmall.product.adapter.AttrsAdapter;
import com.geek.geekmall.product.adapter.CommentAdapter;
import com.geek.geekmall.product.adapter.PhotoAdapter;
import com.geek.geekmall.product.adapter.SpecAdapter;
import com.geek.geekmall.register.LoginActivity;
import com.geek.geekmall.utils.DensityUtils;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.utils.ScreenUtils;
import com.geek.geekmall.views.CountAdjustView;
import com.geek.geekmall.views.GradientRelativeLayout;
import com.geek.geekmall.views.GradientTextView;
import com.geek.geekmall.views.GridViewForScrollView;
import com.geek.geekmall.views.HorizontalListView;
import com.geek.geekmall.views.ListViewForScrollView;
import com.geek.geekmall.views.OptionsItemView;
import com.geek.geekmall.views.ProductImageViewLayout;
import com.geek.geekmall.views.ShareDialog;
import com.geek.geekmall.views.ToastView;
import com.geek.geekmall.views.ViewPagerScrollView;
import com.geek.geekmall.wxapi.WeiXinConstants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 5/19/15.
 */
public class ProductInfoActivity extends BaseActivity implements View.OnClickListener, ViewPagerScrollView.OnScrollListener {
    private List<GoodsImages> images;
    private ProductImageViewLayout mViewPager;
    private ViewPagerScrollView mScrollView;
    private GradientRelativeLayout mGradientLayout;
    private GradientTextView mGradientView;
    private GradientTextView mShareView;

    private RadioGroup mGroup;
    private RadioButton mIntroButton;
    private RadioButton mPhotoButton;
    private RadioButton mCommentButton;
    private Button mBuyBtn;
    private Button mAddCart;
    private TextView mLikeView;
    private ImageView mLikeAnim;
    private ImageView mCartAnim;
    private TextView mCartNumber;
    private GradientTextView mBack;
    private TextView mNameView;
    private TextView mPriceView;
    private TextView mOldPriceView;
    private TextView mYunFeiView;
    private TextView mSalesView;
    private TextView mFavorNum;
    private Product mProduct;
    private User user;
    private String userId = "";
    private String token = "";
    private String goodsId = "";
    private OptionsItemView mTuihuo;
    private OptionsItemView mMian;
    private OptionsItemView mGurrent;
    private LikeAdapter mLikeAdapter;
    //    private GridViewForScrollView mSpecGridView;
//    private TextView mSpecName;
//    private SpecAdapter mSpecAdapter;
    private LinearLayout mContentLayout;
    private ListViewForScrollView mAttrsListView;
    private ListViewForScrollView mPhotoListView;

    private List<GoodsAttr> mAttrs;
    private AttrsAdapter mAttrsAdapter;
    private CommentAdapter mCommentAdapter;
    //    private WebView mWebView;
    private List<String> format;
    private ImageButton shoppintCartBtn;
    private IWXAPI api;
    private ShareDialog mShareDialog;
    private String format1 = "";
    private String format2 = "";
    private String format3 = "";
    private int sepcSize = 0;
    private int size = 0;
    private TextView mRemainNumber;
    private CountAdjustView mCountAdjustView;
    private HorizontalListView mHorizontalListView;
    private TextView mStoreName;
    private ImageView mStoreAvator;
    private RelativeLayout mToolLayout;
    private Button mWantButton;
    private boolean isWant;
    private PhotoAdapter mPhotoAdapter;
    private LinearLayout mFirstView;
    private ImageView mReturnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        api = WXAPIFactory.createWXAPI(this, WeiXinConstants.APP_ID);
        mProduct = (Product) getIntent().getSerializableExtra("product");
        isWant = getIntent().getBooleanExtra("isWant", false);
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
        if (mProduct != null) {
            if (mProduct.getId() != null) {
                goodsId = mProduct.getId();
            } else if (mProduct.getGoodsId() != null) {
                goodsId = mProduct.getGoodsId();
            }
        }
        format = new ArrayList<>();

        init();
        mYunFeiView.setText(getResources().getString(R.string.stock, 0));
        mSalesView.setText(getResources().getString(R.string.sale, 0));
        mFavorNum.setText(getResources().getString(R.string.favor, 0));
        getProdcutInfo();
        getGuessLike();

    }

    @Override
    protected void onResume() {
        super.onResume();
        user = GeekApplication.getUser();
        if (user != null) {
            userId = user.getId();
            token = user.getToken();
        }
    }

    private void getProdcutInfo() {
        APIControl.getInstance().getProductInfo(this, userId, token, goodsId,
                new DataResponseListener<ProductInfoData>() {
                    @Override
                    public void onResponse(ProductInfoData data) {
                        if (data.getStatus() == 200 && data.getData().getGoodsInfo() != null) {

                            mProduct = data.getData().getGoodsInfo();
                            if (mProduct.getIsFirstFree() == 1) {
                                mMian.setVisibility(View.VISIBLE);
                            } else {
                                mMian.setVisibility(View.GONE);
                            }
                            getPhotos();
                            mRemainNumber.setText("库存" + mProduct.getStock() + "件");
                            mAttrs = data.getData().getGoodsAttr();
                            if (mProduct.getIsFavour() == 1) {
                                mLikeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon_pressed, 0, 0, 0);
                            }
                            if (mProduct.getMemberPrice() != null) {
                                mPriceView.setText("￥" + mProduct.getMemberPrice());
                            }
                            if (mProduct.getMarketPrice() != null) {
                                mOldPriceView.setText("￥" + mProduct.getMarketPrice());
                            }
                            if (mProduct.getIsFindWant() == 1) {
                                mWantButton.setText("已想要");
                            } else {
                                mWantButton.setText("想要");
                            }
                            mNameView.setText(mProduct.getDisplayName());
                            mAttrsAdapter = new AttrsAdapter(ProductInfoActivity.this);
                            mAttrsAdapter.setmAttrs(mAttrs);
                            mAttrsListView.setAdapter(mAttrsAdapter);
                            sepcSize = data.getData().getGoodsSpec().size();
                            for (int i = 0; i < sepcSize; i++) {
                                View view = getLayoutInflater().inflate(R.layout.spec_layout, null);
                                final GridViewForScrollView specGridView = (GridViewForScrollView) view.findViewById(R.id.spec_gridview);
                                specGridView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

                                final SpecAdapter specAdapter = new SpecAdapter(ProductInfoActivity.this, specGridView);
                                specAdapter.setmSpecs(data.getData().getGoodsSpec().get(i).getSpecList());
                                specGridView.setAdapter(specAdapter);
                                final int k = i;

                                specGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        specGridView.setItemChecked(position, true);
                                        specAdapter.notifyDataSetChanged();
//                                        format.add(k, specAdapter.getItem(position).getId());
                                        if (k == 0) {
                                            format1 = specAdapter.getItem(position).getId();
                                        } else if (k == 1) {
                                            format2 = specAdapter.getItem(position).getId();
                                        } else if (k == 2) {
                                            format3 = specAdapter.getItem(position).getId();
                                        }

//                                        if (size != sepcSize && !TextUtils.isEmpty(format1)) {
//                                            size++;
//                                        }
//                                        if (size != sepcSize && TextUtils.isEmpty(format2)) {
//                                            size++;
//                                        }
//                                        if (size != sepcSize && TextUtils.isEmpty(format3)) {
//                                            size++;
//                                        }
                                        if (!TextUtils.isEmpty(format1) && !TextUtils.isEmpty(format2) && !TextUtils.isEmpty(format3)) {
                                            size = 3;
                                        } else if (!TextUtils.isEmpty(format1) && !TextUtils.isEmpty(format2)) {
                                            size = 2;
                                        } else if (!TextUtils.isEmpty(format1)) {
                                            size = 1;
                                        }
                                        if (size == sepcSize) {
                                            getPrice();
                                        }
                                    }
                                });
                                TextView specName = (TextView) view.findViewById(R.id.spec_name);
                                specName.setText(data.getData().getGoodsSpec().get(i).getSpecName());
                                mContentLayout.addView(view);

                            }

                            images = data.getData().getGoodsImages();
                            mViewPager.setData(images);
                            mCartNumber.setText(mProduct.getShoppingCartNumber() + "");
                            mYunFeiView.setText(getResources().getString(R.string.stock, mProduct.getPostage()));
                            if (TextUtils.isEmpty(mProduct.getSales())) {
                                mSalesView.setText(getResources().getString(R.string.sale, 0));
                            } else {
                                mSalesView.setText(getResources().getString(R.string.sale, mProduct.getSales()));
                            }
                            if (TextUtils.isEmpty(mProduct.getFavourNumber())) {
                                mFavorNum.setText(getResources().getString(R.string.favor, 0));
                            } else {
                                mFavorNum.setText(getResources().getString(R.string.favor, mProduct.getFavourNumber()));
                            }

                            mStoreName.setText(mProduct.getStoreName());
                            mMian.setTitle(getResources().getString(R.string.support_service2, mProduct.getReachFree()));
                            if (!TextUtils.isEmpty(mProduct.getStoreImg())) {

                                ImageLoader.getInstance(ProductInfoActivity.this).getPicasso()
                                        .load(mProduct.getStoreImg()).into(mStoreAvator);
                            }
                        }

                    }
                }, errorListener(URLs.PRODUCT_INFO_URL));
    }

    private void getGuessLike() {
        APIControl.getInstance().guessLike(this, userId, 10 + "", new DataResponseListener<ProductPageData>() {
            @Override
            public void onResponse(ProductPageData productPageData) {
                if (productPageData.getStatus() == 200) {
                    mLikeAdapter.setmProducts(productPageData.getData().getDataList());
                    mLikeAdapter.notifyDataSetChanged();
                }
            }
        }, errorListener(URLs.PRODUCT_GUESS_URL));
    }

    private void init() {
        mFirstView = (LinearLayout) findViewById(R.id.isFirst);
        if (mProduct.getIsFirstFree() == 1) {
            mFirstView.setVisibility(View.VISIBLE);
        }
        mReturnView = (ImageView) findViewById(R.id.return_top);
        mReturnView.setOnClickListener(this);
        mToolLayout = (RelativeLayout) findViewById(R.id.toolbar);
        mWantButton = (Button) findViewById(R.id.want);
        if (isWant) {
            mToolLayout.setVisibility(View.GONE);
            mWantButton.setVisibility(View.VISIBLE);

        }
        mToolLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mPhotoListView = (ListViewForScrollView) findViewById(R.id.photo_web);
        mStoreName = (TextView) findViewById(R.id.store_name);
        mStoreAvator = (ImageView) findViewById(R.id.cover);
        mWantButton.setOnClickListener(this);
        mHorizontalListView = (HorizontalListView) findViewById(R.id.like_listview);
        mLikeAdapter = new LikeAdapter(this);
        mHorizontalListView.setAdapter(mLikeAdapter);
        mHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductInfoActivity.this, ProductInfoActivity.class);
                intent.putExtra("product", mLikeAdapter.getItem(position));
                startActivity(intent);
                finish();
            }
        });
        mCountAdjustView = (CountAdjustView) findViewById(R.id.count_ajust_view);
        mRemainNumber = (TextView) findViewById(R.id.remain);
        mShareView = (GradientTextView) findViewById(R.id.share);
        shoppintCartBtn = (ImageButton) findViewById(R.id.to_shoppingcart_btn);
        mAttrsListView = (ListViewForScrollView) findViewById(R.id.attrs_list);
        mContentLayout = (LinearLayout) findViewById(R.id.spec_content);
        mBack = (GradientTextView) findViewById(R.id.back);
        mNameView = (TextView) findViewById(R.id.name);
        mPriceView = (TextView) findViewById(R.id.price);
        mOldPriceView = (TextView) findViewById(R.id.oldPrice);
        mYunFeiView = (TextView) findViewById(R.id.yunfei);
        mSalesView = (TextView) findViewById(R.id.sales_num);
        mFavorNum = (TextView) findViewById(R.id.favor_num);
        if (mProduct != null) {
            if (mProduct.getMemberPrice() != null) {
                mPriceView.setText("￥" + mProduct.getMemberPrice());
            }
            if (mProduct.getMarketPrice() != null) {
                mOldPriceView.setText("￥" + mProduct.getMarketPrice());
            }
            if (mProduct.getDisplayName() != null) {
                mNameView.setText(mProduct.getDisplayName());
            } else if (mProduct.getGoodsName() != null) {
                mNameView.setText(mProduct.getGoodsName());
            }
        }

        mOldPriceView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        images = new ArrayList<>();
        mViewPager = (ProductImageViewLayout) findViewById(R.id.imageview);

        mMian = (OptionsItemView) findViewById(R.id.mian);
        mTuihuo = (OptionsItemView) findViewById(R.id.tuihuo);
        mGurrent = (OptionsItemView) findViewById(R.id.gurrent);
//        mMian.setOnClickListener(this);
//        mTuihuo.setOnClickListener(this);
//        mGurrent.setOnClickListener(this);
        mShareView.setOnClickListener(this);

        mGroup = (RadioGroup) findViewById(R.id.radio_group);
        mIntroButton = (RadioButton) findViewById(R.id.intruduce);
        mPhotoButton = (RadioButton) findViewById(R.id.photos);
        mCommentButton = (RadioButton) findViewById(R.id.comments);
        mCartNumber = (TextView) findViewById(R.id.cart_number);
        mCartAnim = (ImageView) findViewById(R.id.animation_cart);
        mLikeView = (TextView) findViewById(R.id.btn_like);
        mBuyBtn = (Button) findViewById(R.id.btn_buy);
        mAddCart = (Button) findViewById(R.id.btn_add_to_cart);
        mLikeAnim = (ImageView) findViewById(R.id.iv_addlike_anim);
        mLikeView.setOnClickListener(this);
        mBuyBtn.setOnClickListener(this);
        mAddCart.setOnClickListener(this);
        mBack.setOnClickListener(this);
        shoppintCartBtn.setOnClickListener(this);
        mGradientLayout = (GradientRelativeLayout) findViewById(R.id.header);
        mGradientLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        mScrollView = (ViewPagerScrollView) findViewById(R.id.scrollview);
        mScrollView.setOnScrollListener(this);
//        mGroup.check(mPhotoButton.getId());
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkId = radioGroup.getCheckedRadioButtonId();
                if (checkId == mIntroButton.getId()) {
                    mAttrsListView.setVisibility(View.VISIBLE);
                    mPhotoListView.setVisibility(View.GONE);
                    mAttrsListView.setAdapter(mAttrsAdapter);
                } else if (checkId == mPhotoButton.getId()) {
                    getPhotos();
                } else if (checkId == mCommentButton.getId()) {
                    getComments();
                }
            }
        });
        mCountAdjustView.setListener(new CountAdjustView.ModifyListener() {
            @Override
            public void onModify(String number) {
                if (!TextUtils.isEmpty(stock)) {
                    if (Integer.valueOf(number) > Integer.valueOf(stock)) {
                        mCountAdjustView.setNumber(Integer.valueOf(number) - 1);
                        new ToastView(ProductInfoActivity.this, "库存不足").show();
                    }

                } else {
                    mCountAdjustView.setNumber(0);
                    new ToastView(ProductInfoActivity.this, "请选择规格").show();
                }

            }
        });

        mPhotoAdapter = new PhotoAdapter(this);
//        mPhotoListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == SCROLL_STATE_IDLE) {
//                    ImageLoader.getInstance(ProductInfoActivity.this).getPicasso().resumeTag(ProductInfoActivity.this);
//                } else {
//                    ImageLoader.getInstance(ProductInfoActivity.this).getPicasso().pauseTag(ProductInfoActivity.this);
//                }
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });

    }

    private void startAnimation() {

        mCartAnim.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0F, 0.3F, 1.0F, 0.3F, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0.0F, Animation.RELATIVE_TO_PARENT,
                -0.20F, Animation.ABSOLUTE, 0.0F, Animation.RELATIVE_TO_PARENT, 0.70F);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0F, 0.4F);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation1);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(500L);
        animationSet.setAnimationListener(new AnimationListener1());

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.3F, 1.0F, 0.3F, 1.0F,
                Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        scaleAnimation.setInterpolator(new BounceInterpolator());
        scaleAnimation.setDuration(500L);
        scaleAnimation.setAnimationListener(new AnimationListener2(animationSet));
        mCartAnim.startAnimation(scaleAnimation);
    }

    private void deleteFavor() {
        APIControl.getInstance().deleteFavorList(this, user.getId(), user.getToken(), mProduct.getId(),
                new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        if (commonData.getStatus() == 200) {
                            mProduct.setIsFavour(0);
                            mLikeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_gray, 0, 0, 0);
                        }
                    }
                }, errorListener(""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_like:
                if (!GeekApplication.isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else if (mProduct.getIsFavour() == 1) {
                    deleteFavor();
                } else {
                    mLikeAnim.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.add_like);
                    mLikeAnim.startAnimation(animation);
                    animation.setAnimationListener(new AnimationListener3());
                    addFavor();
                }

                break;
            case R.id.btn_add_to_cart:
                if (!GeekApplication.isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    if (size == sepcSize) {
                        addCart();
                    } else {
                        mScrollView.smoothScrollTo(0, (int) mContentLayout.getY());
                        new ToastView(this, "请选择规格").show();
                    }

                }

                break;
            case R.id.to_shoppingcart_btn:
                Intent intent = new Intent(ProductInfoActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                sendBroadcast(new Intent("com.geek.geekmall.action.cart"));
                finish();
                break;

            case R.id.btn_buy:
                if (!GeekApplication.isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {

                    if (size == sepcSize) {
                        buyProduct();
                    } else {
                        new ToastView(this, "请选择规格").show();
                    }
//                        buyProduct();
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.mian:
                startActivity(new Intent(this, RuleTwoActivity.class));
                break;
            case R.id.tuihuo:
                startActivity(new Intent(this, RuleOneActivity.class));
                break;
            case R.id.gurrent:
                startActivity(new Intent(this, RuleThreeActivity.class));
                break;
            case R.id.share:
//                share();
                if (GeekApplication.isLogin()) {
                    String share_url = URLs.HOST + "/geektmall-api/phone/goodsDetail_phone.html?";
                    if (GeekApplication.isLogin()) {
                        share_url = share_url + "id=" + mProduct.getId() + "&bindingToken=" + GeekApplication.getAgentInfo().getBindingToken();
                    }
                    if (images != null && images.size() > 0) {
                        mShareDialog = new ShareDialog(this, share_url, mProduct.getDisplayName(), images.get(0).getImgUrl());
                        mShareDialog.show();
                    }
                } else {
                    new ToastView(this,"请登录后再分享").show();
                }

                break;
            case R.id.want:
                if (GeekApplication.isLogin()) {
                    if (mProduct.getIsFindWant() != 1) {
                        want();
                    } else {
                        deleteWant();
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }


                break;
            case R.id.return_top:
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
                break;
            default:
                break;
        }
    }

    private void deleteWant() {
        APIControl.getInstance().getDeleteWant(this, goodsId, userId, new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {
                if (commonData.getStatus() == 200) {
                    mWantButton.setText("想要");
                    mProduct.setIsFindWant(0);
                    new ToastView(ProductInfoActivity.this, "删除成功").show();
                } else {
                    new ToastView(ProductInfoActivity.this, "删除失败").show();
                }
            }
        }, errorListener(""));
    }

    private void want() {
        APIControl.getInstance().saveWant(this, userId, token, mProduct.getId(), new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {
                if (commonData.getStatus() == 200) {
                    mWantButton.setText("已想要");
                    mProduct.setIsFindWant(1);
                    new ToastView(ProductInfoActivity.this, "加入想要成功").show();
                } else {
                    new ToastView(ProductInfoActivity.this, "加入失败").show();
                }
            }
        }, errorListener(""));
    }

    private String spec = "";
    private String stock = "";

    private void getPrice() {
        loadingDialog.show();
        APIControl.getInstance().getProductPrice(this, goodsId, userId, token, format1,
                format2, format3,
                new DataResponseListener<CartSpecData>() {
                    @Override
                    public void onResponse(CartSpecData cartSpecData) {
                        loadingDialog.dismiss();
                        if (cartSpecData.getStatus() == 200) {
                            spec = cartSpecData.getData().getId();
                            stock = cartSpecData.getData().getStock();
                            mRemainNumber.setText("库存" + cartSpecData.getData().getStock() + "件");
                        }
                    }
                }, errorListener(""));
    }

    private void buyProduct() {
        loadingDialog.show();
        APIControl.getInstance().buyProduct(this, goodsId, userId, token, mCountAdjustView.getNumber(), spec,
                new DataResponseListener<BuyData>() {
                    @Override
                    public void onResponse(BuyData buyData) {
                        loadingDialog.dismiss();
                        if (buyData.getStatus() == 200) {
                            Intent intent = new Intent();
                            intent.setClass(ProductInfoActivity.this, SettlementActivity.class);
                            intent.putExtra("list", buyData.getData());
                            startActivity(intent);
                        } else {
                            new ToastView(ProductInfoActivity.this, buyData.getErrorMsg()).show();
                        }

                    }
                }, errorListener(""));
    }

    private void addCart() {
        if (format == null || mAttrs == null) {
            new ToastView(this, "商品规格不存在").show();
            return;
        }
        if (format.size() == mAttrs.size()) {

        }
        startAnimation();
        loadingDialog.show();
        APIControl.getInstance().addCart(this, userId, token, goodsId, mCountAdjustView.getNumber(), spec,
                new DataResponseListener<CommonData>() {
                    @Override
                    public void onResponse(CommonData commonData) {
                        loadingDialog.dismiss();

                        if (commonData.getStatus() != 200) {
                            new ToastView(ProductInfoActivity.this, commonData.getErrorMsg()).show();
                        } else {
                            mCartNumber.setText(Integer.valueOf(mCartNumber.getText().toString()) + 1 + "");
                        }
                        MyLog.debug(ProductInfoActivity.class, commonData.getErrorMsg());
                    }
                }, errorListener(""));
    }

    private void getPhotos() {
        if (!TextUtils.isEmpty(mProduct.getId())) {
            APIControl.getInstance().productPhoto(this, userId, token, mProduct.getId(),
                    new DataResponseListener<PhotoData>() {
                        @Override
                        public void onResponse(PhotoData data) {
                            mAttrsListView.setVisibility(View.GONE);
                            mPhotoListView.setVisibility(View.VISIBLE);
                            mPhotoAdapter.setmPhotos(data.getData());
                            mPhotoAdapter.setmListView(mPhotoListView);
                            mPhotoListView.setAdapter(mPhotoAdapter);
//                            new ListViewUtil().setListViewHeightBasedOnChildren(mPhotoListView);
                        }
                    }, errorListener(URLs.PRODUCT_INFO_PHOTO_URL));
        } else {
            new ToastView(this, "数据错误").show();
        }
    }

    private void getComments() {
        APIControl.getInstance().productComment(this, userId, token, mProduct.getId(), 1, SIZE,
                new DataResponseListener<CommentPageData>() {
                    @Override
                    public void onResponse(CommentPageData data) {

                        mAttrsListView.setVisibility(View.VISIBLE);
                        mPhotoListView.setVisibility(View.GONE);
                        if (data.getStatus() == 200) {
                            mCommentAdapter = new CommentAdapter(ProductInfoActivity.this);
                            mCommentAdapter.setmCommets(data.getData().getDataList());
                            mAttrsListView.setAdapter(mCommentAdapter);
                        }

                    }
                }, errorListener(URLs.PRODUCT_INFO_COMMENT_URL));
    }

    private void addFavor() {
        loadingDialog.show();
        APIControl.getInstance().addFavor(this, userId, token, mProduct.getId(), new DataResponseListener<CommonData>() {
            @Override
            public void onResponse(CommonData commonData) {
                loadingDialog.dismiss();
                if (commonData.getStatus() == 200) {
                    mProduct.setIsFavour(1);
                    mLikeView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_icon_pressed, 0, 0, 0);
                }
                MyLog.debug(ProductInfoActivity.class, commonData.getErrorMsg());
            }
        }, errorListener(""));
    }

    /**
     * 回调方法， 返回MyScrollView滑动的Y方向距离
     *
     * @param scrollY
     */
    @Override
    public void onScroll(float scrollY) {
        float alphat = 0;
        if (scrollY <= DensityUtils.dp2px(this, 400) && scrollY > 0) {
            alphat = scrollY / DensityUtils.dp2px(this, 400);
        } else if (scrollY > DensityUtils.dp2px(this, 400)) {
            alphat = 1;
        }
        if (scrollY > ScreenUtils.getScreenHeight(this)){
            mReturnView.setVisibility(View.VISIBLE);
        } else {
            mReturnView.setVisibility(View.GONE);
        }
        mGradientLayout.change(alphat);
    }

    class AnimationListener1 implements Animation.AnimationListener {


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mCartAnim.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class AnimationListener2 implements Animation.AnimationListener {
        AnimationSet mAnimationSet;

        AnimationListener2(AnimationSet animationSet) {
            mAnimationSet = animationSet;
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mCartAnim.startAnimation(mAnimationSet);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class AnimationListener3 implements Animation.AnimationListener {


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mLikeAnim.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
