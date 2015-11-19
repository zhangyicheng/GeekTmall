package com.geek.geekmall.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.geek.geekmall.R;

/**
 * Created by apple on 5/20/15.
 */
public class GradientTextView extends CustomTextView implements IGradientView {
    private int startColor;
    private int endColor;
    private float[] arrayOfFloat;

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientRelativeLayout);
        this.startColor = typedArray.getColor(R.styleable.GradientRelativeLayout_startColor, -1);
        this.endColor = typedArray.getColor(R.styleable.GradientRelativeLayout_endColor, -1);
        typedArray.recycle();
        float[] arrayOfFloat = new float[20];
        arrayOfFloat[0] = 0.0F;
        arrayOfFloat[1] = 0.0F;
        arrayOfFloat[2] = 0.0F;
        arrayOfFloat[3] = 0.0F;
        arrayOfFloat[4] = (-Color.red(this.startColor) + Color.red(this.endColor));
        arrayOfFloat[5] = 0.0F;
        arrayOfFloat[6] = 0.0F;
        arrayOfFloat[7] = 0.0F;
        arrayOfFloat[8] = 0.0F;
        arrayOfFloat[9] = (-Color.green(this.startColor) + Color.green(this.endColor));
        arrayOfFloat[10] = 0.0F;
        arrayOfFloat[11] = 0.0F;
        arrayOfFloat[12] = 0.0F;
        arrayOfFloat[13] = 0.0F;
        arrayOfFloat[14] = (-Color.blue(this.startColor) + Color.blue(this.endColor));
        arrayOfFloat[15] = 0.0F;
        arrayOfFloat[16] = 0.0F;
        arrayOfFloat[17] = 0.0F;
        arrayOfFloat[18] = 1.0F;
        arrayOfFloat[19] = 0.0F;
        this.arrayOfFloat = arrayOfFloat;
        Drawable[] drawables = getCompoundDrawables();
        int j = drawables.length;
        int i = 0;
        while (i < j) {
            Drawable localDrawable = drawables[i];
            if (localDrawable != null)
                localDrawable.mutate();
            i++;
        }
    }

    @Override
    public void change(float alpha) {
        int i = (int) (Color.red(this.startColor) - alpha * (Color.red(this.startColor) - Color.red(this.endColor)));
        int j = (int) (Color.green(this.startColor) - alpha * (Color.green(this.startColor) - Color.green(this.endColor)));
        int k = (int) (Color.blue(this.startColor) - alpha * (Color.blue(this.startColor) - Color.blue(this.endColor)));
        this.arrayOfFloat[4] = (Color.red(this.startColor) - alpha * (Color.red(this.startColor) - Color.red(this.endColor)));
        this.arrayOfFloat[9] = (Color.green(this.startColor) - alpha * (Color.green(this.startColor) - Color.green(this.endColor)));
        this.arrayOfFloat[14] = (Color.blue(this.startColor) - alpha * (Color.blue(this.startColor) - Color.blue(this.endColor)));
        for (Drawable localDrawable : getCompoundDrawables())
            if (localDrawable != null)
                localDrawable.setColorFilter(new ColorMatrixColorFilter(this.arrayOfFloat));
        setTextColor(Color.argb(255, i, j, k));
    }

}
