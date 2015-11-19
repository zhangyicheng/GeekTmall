package com.geek.geekmall.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.geek.geekmall.utils.MyLog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by apple on 9/22/15.
 */
public class ResizableImageView extends RecyclerImageView  {

    private int fixedHeight = -1;
    private int fixedWidth = -1;
    private int mHeight;
    private SizeChangeListener listener;

    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SizeChangeListener getListener() {
        return listener;
    }

    public void setListener(SizeChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (fixedHeight != -1 && fixedWidth != -1) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) Math.ceil((float) width * (float) fixedHeight / (float) fixedWidth);
            setMeasuredDimension(width, height);
        } else {
            Drawable d = getDrawable();
            if (d != null) {
                // ceil not round - avoid thin vertical gaps along the left/right edges
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
                mHeight = height;
                MyLog.debug(ResizableImageView.class, height + "+++++++++++++");
                setMeasuredDimension(width, height);
                if (listener != null){
                    listener.onChange(height);
                }
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    public int getImageHeight() {
        return mHeight;
    }

    public void setFixedHeight(int fixedHeight) {
        this.fixedHeight = fixedHeight;
    }

    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }



    public interface SizeChangeListener {
        void onChange(int size);
    }
}
