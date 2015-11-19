package com.geek.geekmall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.geek.geekmall.R;
import com.geek.geekmall.utils.ImageLoader;
import com.geek.geekmall.views.indicator.CirclePageIndicator;
import com.squareup.picasso.MemoryPolicy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 8/28/15.
 */
public class NavigationActivity extends BaseActivity {
    private ViewPager imageSwicher;
    private ImageViewAdapter adapter;
    private CirclePageIndicator circlePageIndicator;
    private int[] images = new int[]{R.drawable.nav_one, R.drawable.nav_two, R.drawable.nav_three};
    private Button mGoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);

        imageSwicher = (ViewPager) findViewById(R.id.imageSwicher);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.navi_image_indicator);
        mGoButton = (Button) findViewById(R.id.go);
        adapter = new ImageViewAdapter(this);
        imageSwicher.setAdapter(adapter);

        circlePageIndicator.setViewPager(imageSwicher);
        adapter.setImages(images);
        circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 2) {
                    mGoButton.setVisibility(View.VISIBLE);
                } else {
                    mGoButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private class ImageViewAdapter extends PagerAdapter {

        private Context mContext;
        private List<ImageView> views;
        private int[] urls;

        public ImageViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        public void setImages(int[] url) {
            urls = url;
            views = new ArrayList<ImageView>();
            for (int i = 0; i < urls.length; i++) {
                ImageView imageView = new ImageView(mContext);
                views.add(imageView);
            }

            notifyDataSetChanged();
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
            container.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = views.get(position);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoader.getInstance(mContext).getPicasso()
                    .load(urls[position])
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .config(Bitmap.Config.RGB_565)
                    .into(imageView);

            container.addView(imageView);
            return views.get(position);
        }
    }
}
