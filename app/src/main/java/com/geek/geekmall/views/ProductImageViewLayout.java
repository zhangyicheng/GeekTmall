package com.geek.geekmall.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.bean.GoodsImages;
import com.geek.geekmall.product.activity.ImageViewActivity;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.views.indicator.CirclePageIndicator;
import com.imbryk.viewPager.LoopViewPager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.squareup.picasso.MemoryPolicy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 5/20/15.
 */

public class ProductImageViewLayout extends RelativeLayout {

    private Context mContext;
    private LoopViewPager imageSwicher;
    private ImageViewAdapter adapter;

    private ImageView defultImage;

    private int adertisementNum;

    private CirclePageIndicator circlePageIndicator;

//    private TimerTask timerTask;

    private List<GoodsImages> images;

//    Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            if (adapter.getCount() <= adertisementNum) {
//                adertisementNum = 0;
//            }
//            imageSwicher.setCurrentItem(adertisementNum);
//            adertisementNum++;
//        }
//
//    };

    public ProductImageViewLayout(Context context) {
        this(context, null, 0);
    }

    public ProductImageViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProductImageViewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.mContext = context;

        LayoutInflater layout = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout.inflate(R.layout.image_view_pager, this, true);

        defultImage = (ImageView) findViewById(R.id.defult_imageview);

        imageSwicher = (LoopViewPager) findViewById(R.id.imageSwicher);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.image_indicator);

        adapter = new ImageViewAdapter(mContext);

    }

    public void setData(List<GoodsImages> images) {
        if (images == null || images.isEmpty()) {
            defultImage.setVisibility(View.VISIBLE);
            imageSwicher.setVisibility(GONE);
            circlePageIndicator.setVisibility(GONE);
            return;
        } else if (images.size() == 1) {
            defultImage.setVisibility(View.VISIBLE);
            imageSwicher.setVisibility(GONE);
            circlePageIndicator.setVisibility(GONE);
            String url = images.get(0).getImgUrl();
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + images.get(0).getImgUrl().replace("small", "big");
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .fit()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .error(R.drawable.home_banner_default)
                    .into(defultImage);
        } else {
            imageSwicher.setVisibility(VISIBLE);
            circlePageIndicator.setVisibility(VISIBLE);
            defultImage.setVisibility(View.GONE);
            this.images = images;
            adapter.setImages(this.images);
            imageSwicher.setAdapter(adapter);

            circlePageIndicator.setViewPager(imageSwicher);
        }

//        setTimerTask();
    }

//    private void setTimerTask() {
//        if (timerTask != null) {
//            timerTask.cancel();
//        }
//        timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(0);
//            }
//        };
//
//        Timer timer = new Timer();
//        timer.schedule(timerTask, 0, 5000);
//    }
}

class ImageViewAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> views;
    private List<GoodsImages> urls;

    public ImageViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void setImages(List<GoodsImages> url) {
        urls = url;
        views = new ArrayList<ImageView>();
        for (int i = 0; i < urls.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            views.add(imageView);
        }

//        notifyDataSetChanged();
    }

    public List<GoodsImages> getImages() {
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
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, ImageViewActivity.class);
                intent.putExtra("images", (Serializable) urls);
                intent.putExtra("position", position);
                mContext.startActivity(intent);

            }
        });
//
//        CommonTool.getBitmapUtils(mContext).configDefaultLoadFailedImage(
//            mContext.getResources().getDrawable(R.drawable.defult_advertisement))
//            .display(imageView, HttpConfig.IMAGE + urls.get(position).getPic());
        if (!TextUtils.isEmpty(urls.get(position).getImgUrl())) {
            String url = urls.get(position).getImgUrl();
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + urls.get(position).getImgUrl().replace("small", "big");
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
                    .fit()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .centerInside()
                    .placeholder(R.drawable.product_default_bg)
                    .into(imageView);
//            ImageLoader.getInstance(mContext).getBitmapUtils()
//                    .configDefaultLoadingImage(R.drawable.product_default_bg)
//                    .configDefaultLoadFailedImage(R.drawable.product_default_bg)
//                    .display(imageView, url);

//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.drawable.product_default_bg)
//                    .showImageOnFail(R.drawable.product_default_bg)
//                    .cacheInMemory(true)
//                    .cacheOnDisk(true)
//                    .build();
//            ImageLoader.getInstance(mContext).getUniversalImageLoader().displayImage(url, imageView, options);
        }
        container.removeView(imageView);
        container.addView(imageView);
        return views.get(position);
    }
}