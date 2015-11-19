package com.geek.geekmall.product.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geek.geekmall.R;
import com.geek.geekmall.URLs;
import com.geek.geekmall.activity.BaseActivity;
import com.geek.geekmall.bean.GoodsImages;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.views.RecyclerImageView;
import com.geek.geekmall.views.indicator.CirclePageIndicator;
import com.imbryk.viewPager.LoopViewPager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by apple on 6/29/15.
 */
public class ImageViewActivity extends BaseActivity {
    private LoopViewPager imageSwicher;
    private ImageViewAdapter adapter;
    private CirclePageIndicator circlePageIndicator;
    private List<GoodsImages> images;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bigimage_view_pager);
        imageSwicher = (LoopViewPager) findViewById(R.id.imageSwicher);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.bigimage_indicator);
        this.images = (List<GoodsImages>) getIntent().getSerializableExtra("images");
        position = getIntent().getIntExtra("position", 0);

        adapter = new ImageViewAdapter(this);
        adapter.setImages(this.images);
        imageSwicher.setAdapter(adapter);

        circlePageIndicator.setViewPager(imageSwicher);
        circlePageIndicator.setCurrentItem(position);
    }

    public void overfinish() {
        finish();
    }
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
//            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            views.add(imageView);
        }

        notifyDataSetChanged();
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
        ImageView imageView = views.get(position);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (!TextUtils.isEmpty(urls.get(position).getImgUrl())) {
            String url = urls.get(position).getImgUrl();
            if (!url.startsWith("http://")) {
                url = URLs.IMAGE_URL + urls.get(position).getImgUrl().replace("small", "big");
            }
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(url)
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageViewActivity) mContext).overfinish();
            }
        });
        container.removeView(imageView);
        container.addView(imageView);
        return views.get(position);
    }
}