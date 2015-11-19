package com.geek.geekmall.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by apple on 4/24/15.
 */
public class BitmapView implements Target {
    private static BitmapView mBitmapView;
    private Context mContext;
    private BitmapDrawable mBitmapDrawable;

    public static BitmapView getInstance() {
        if (mBitmapView == null) {
            mBitmapView = new BitmapView();
        }
        return mBitmapView;
    }

    public void showImage(Context context, String url) {
        mContext = context.getApplicationContext();
        if (url != null && !TextUtils.isEmpty(url)) {
            Picasso.with(mContext).load(url).into(this);
        }
    }

    /**
     * Callback when an image has been successfully loaded.
     * <p/>
     * <strong>Note:</strong> You must not recycle the bitmap.
     *
     * @param bitmap
     * @param from
     */
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (bitmap != null) {
            this.mBitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmap);
        }
    }

    /**
     * Callback indicating the image could not be successfully loaded.
     * <p/>
     * <strong>Note:</strong> The passed {@link Drawable} may be {@code null} if none has been
     * specified via {@link RequestCreator#error(Drawable)}
     * or {@link RequestCreator#error(int)}.
     *
     * @param errorDrawable
     */
    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    /**
     * Callback invoked right before your request is submitted.
     * <p/>
     * <strong>Note:</strong> The passed {@link Drawable} may be {@code null} if none has been
     * specified via {@link RequestCreator#placeholder(Drawable)}
     * or {@link RequestCreator#placeholder(int)}.
     *
     * @param placeHolderDrawable
     */
    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
