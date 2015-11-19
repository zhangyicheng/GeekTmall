/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.geek.geekmall.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

/**
 * Avatar utilities
 */
public class ImageLoader {
    static final int DISK_CACHE_SIZE = 500 * 1024; // 500MB

    private static final String TAG = "AvatarLoader";

    private static final float CORNER_RADIUS_IN_DIP = 120;

    private final Context context;
    private final Picasso p;
    private final BitmapUtils bitmapUtils;
    private com.nostra13.universalimageloader.core.ImageLoader mUniverImageLoader;
    private final RoundedCornersTransformation transformation = new RoundedCornersTransformation();

    /**
     * The max size of avatar images, used to rescale images to save memory.
     */
//    private static int avatarSize = 0;

    private int with;
    private int height;
    private static ImageLoader mInstance;

    public static ImageLoader getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader(context);
                }
            }
        }

        return mInstance;
    }

    /**
     * Create avatar helper
     *
     * @param context
     */
    private ImageLoader(final Context context) {
        this.context = context;

        OkHttpClient client = new OkHttpClient();

        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(SDCardUtils.getSDCardPath(), "geekmall");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        client.setCache(cache);
        p = new Picasso.Builder(context).downloader(new OkHttpDownloader(client)).build();
//        p.setIndicatorsEnabled(true);
//        p.setLoggingEnabled(true);
        bitmapUtils = new BitmapUtils(context, cacheDir.getPath(), 0.15f, DISK_CACHE_SIZE);
        mUniverImageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(500 * 1024 * 1024)
                .diskCacheFileCount(500)
                .writeDebugLogs() // Remove for release app
                .build();
        mUniverImageLoader.init(configuration);
    }

    public com.nostra13.universalimageloader.core.ImageLoader getUniversalImageLoader() {
        return mUniverImageLoader;
    }

    public Picasso getPicasso() {
        return p;
    }

    public BitmapUtils getBitmapUtils() {
        return bitmapUtils;
    }

//    private int getMaxAvatarSize(final Context context) {
//        int[] attrs = {android.R.attr.layout_height};
//        TypedArray array = context.getTheme().obtainStyledAttributes(R.style.AvatarXLarge, attrs);
//        // Passing default value of 100px, but it shouldn't resolve to default anyway.
//        int size = array.getLayoutDimension(0, 100);
//        array.recycle();
//        return size;
//    }

    private boolean deleteCache(final File cache) {
        if (cache.isDirectory())
            for (File f : cache.listFiles())
                deleteCache(f);
        return cache.delete();
    }


    public class RoundedCornersTransformation implements Transformation {
        private float radius = 3;

        public RoundedCornersTransformation(float radius) {

            float density = context.getResources().getDisplayMetrics().density;
            this.radius = radius * density;
        }

        public RoundedCornersTransformation() {

        }

        @Override
        public Bitmap transform(Bitmap source) {
            return ImageUtils.roundCorners(source, this.radius);
        }

        @Override
        public String key() {
            return "RoundedCornersTransformation";
        }
    }

    public class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }
    }
}
