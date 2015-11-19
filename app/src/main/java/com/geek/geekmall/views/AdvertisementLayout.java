package com.geek.geekmall.views;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.*;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.Advertisement;
import com.geek.geekmall.bean.Product;
import com.geek.geekmall.bean.Theme;
import com.geek.geekmall.home.activity.BannerThemeActivity;
import com.geek.geekmall.home.activity.ThemeActivity;
import com.geek.geekmall.product.activity.ProductInfoActivity;
import com.geek.geekmall.profile.activity.AgencyIntro;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.utils.MyLog;
import com.geek.geekmall.views.indicator.CirclePageIndicator;
import com.imbryk.viewPager.LoopViewPager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 广告控件
 * Created by zhangbob on 14/11/4.
 */
public class AdvertisementLayout extends RelativeLayout {

    private Context mContext;
    private LoopViewPager imageSwicher;
    private AdvertisementAdapter adapter;

    private ImageView defultImage;

    private int adertisementNum;

    private CirclePageIndicator circlePageIndicator;

    private TimerTask timerTask;

    private List<Advertisement> advertisements;
    //    private MyHandler handler;
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (adapter.getCount() < adertisementNum) {
                adertisementNum = 0;
            }
            imageSwicher.setCurrentItem(adertisementNum);
            adertisementNum++;
        }

    };

    static class MyHandler extends Handler {
        private WeakReference<AdvertisementLayout> mOuter;

        public MyHandler(AdvertisementLayout view) {
            mOuter = new WeakReference<AdvertisementLayout>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            AdvertisementLayout outer = mOuter.get();
            if (outer != null) {
                if (outer.adapter.getCount() < outer.adertisementNum) {
                    outer.adertisementNum = 0;
                }
                outer.imageSwicher.setCurrentItem(outer.adertisementNum);
                outer.adertisementNum++;
            }

        }
    }

    public AdvertisementLayout(Context context) {
        this(context, null, 0);
    }

    public AdvertisementLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdvertisementLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.mContext = context;

        LayoutInflater layout = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout.inflate(R.layout.advertisement_view, this, true);

        defultImage = (ImageView) findViewById(R.id.defult_advertisement);

        imageSwicher = (LoopViewPager) findViewById(R.id.imageSwicher);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.ad_indicator);
//        imageSwicher.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    timerTask.cancel();
//                    MyLog.debug(AdvertisementLayout.class, "-----------");
//                } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    setTimerTask();
//                }
//                return false;
//            }
//        });
        circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                MyLog.debug(AdvertisementLayout.class, "onPageScrolled");
//                timerTask.cancel();
            }

            @Override
            public void onPageSelected(int position) {
                adertisementNum = position;

//                MyLog.debug(AdvertisementLayout.class,"onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

//                MyLog.debug(AdvertisementLayout.class,"onPageScrollStateChanged");
            }
        });
        circlePageIndicator.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    timerTask.cancel();
                    MyLog.debug(AdvertisementLayout.class, "-----------");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    setTimerTask();
                }
                return false;
            }
        });
        adapter = new AdvertisementAdapter(mContext);

    }

    public void setData(List<Advertisement> advertisements) {
        if (advertisements == null || advertisements.isEmpty()) {
            defultImage.setBackgroundResource(R.drawable.home_banner_default);
            return;
        }
        defultImage.setVisibility(View.GONE);
        this.advertisements = advertisements;
        adapter.setAdvetisements(this.advertisements);
        imageSwicher.setAdapter(adapter);

        circlePageIndicator.setViewPager(imageSwicher);
        setTimerTask();
    }

    private void setTimerTask() {
        if (timerTask != null) {
            timerTask.cancel();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 3000);

    }
}

class AdvertisementAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> views;
    private List<Advertisement> urls;

    public AdvertisementAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void setAdvetisements(List<Advertisement> url) {
        urls = url;
        views = new ArrayList<ImageView>();
        for (int i = 0; i < urls.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            views.add(imageView);
        }

//        notifyDataSetChanged();
    }

    public List<Advertisement> getAdvertisements() {
        return urls;
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        } else {
            return 0;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if (views == null) {
            return null;
        }
        ImageView imageView = views.get(position);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if ("分销体系及操作方式介绍".equals(urls.get(position).getDiscription())) {
                    intent.setClass(mContext, AgencyIntro.class);
                    mContext.startActivity(intent);
                } else {
                    if ("2".equals(urls.get(position).getLinkType())) {
                        intent.setClass(mContext, ThemeActivity.class);
                        Theme theme = new Theme();
                        theme.setId(urls.get(position).getLinkTypeId());
                        theme.setImgUrl(urls.get(position).getImgUrl());
                        theme.setTitle(urls.get(position).getDiscription());
                        intent.putExtra("theme", theme);

                        intent.putExtra("linkTypeId", urls.get(position).getLinkType());
                        intent.putExtra("type", "2");
                        mContext.startActivity(intent);
                    } else if ("1".equals(urls.get(position).getLinkType())) {
                        intent.setClass(mContext, ProductInfoActivity.class);
                        Product product = new Product();
                        product.setId(urls.get(position).getLinkTypeId());

                        intent.putExtra("product", product);
                        mContext.startActivity(intent);
                    } else if ("3".equals(urls.get(position).getLinkType())) {
                        intent.setClass(mContext, BannerThemeActivity.class);
                        Product product = new Product();
                        product.setId(urls.get(position).getLinkTypeId());

                        intent.putExtra("linkTypeId", urls.get(position).getLinkTypeId());
                        intent.putExtra("type", "3");

                        mContext.startActivity(intent);
                    }
                }

            }
        });
//        ImageLoader.getInstance(mContext).bind(imageView, urls.get(position).getUrl());
        if (!TextUtils.isEmpty(urls.get(position).getImgUrl())) {
            String url = urls.get(position).getImgUrl();
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + url;
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .placeholder(R.drawable.home_banner_default)
                    .into(imageView);
        }
        container.removeView(imageView);
        container.addView(imageView);
        return imageView;
    }
}