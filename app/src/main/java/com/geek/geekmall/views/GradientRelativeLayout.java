package com.geek.geekmall.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.geek.geekmall.R;

/**
 * Created by apple on 5/20/15.
 */
public class GradientRelativeLayout extends RelativeLayout implements IGradientView {
    private int startColor;
    private int endColor;
    private int red;
    private int green;
    private int blue;
    private int alpha;
    private Paint paint;

    public GradientRelativeLayout(Context context) {
        super(context);
    }

    public GradientRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientRelativeLayout);
        this.startColor = typedArray.getColor(R.styleable.GradientRelativeLayout_startColor, -1);
        this.endColor = typedArray.getColor(R.styleable.GradientRelativeLayout_endColor, -1);
        typedArray.recycle();
        this.paint = new Paint();
        this.paint.setARGB(0, 80, 80, 86);
        if (getBackground() != null)
            getBackground().mutate();
    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawARGB(this.alpha, this.red, this.green, this.blue);
        canvas.drawLine(0.0F, -1 + getHeight(), getWidth(), -1 + getHeight(), this.paint);
        super.dispatchDraw(canvas);
    }

    @Override
    public void change(float alpha) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if ((view instanceof IGradientView))
                ((IGradientView) view).change(alpha);
        }
        this.alpha = ((int) (255.0F * alpha));
        this.red = ((int) (Color.red(this.startColor) - alpha * (Color.red(this.startColor) - Color.red(this.endColor))));
        this.green = ((int) (Color.green(this.startColor) - alpha * (Color.green(this.startColor) - Color.green(this.endColor))));
        this.blue = ((int) (Color.blue(this.startColor) - alpha * (Color.blue(this.startColor) - Color.blue(this.endColor))));
        this.paint.setAlpha((int) (38.0F * alpha));
        if (getBackground() != null) {
            getBackground().setAlpha((int) (255.0F * (1.0F - alpha)));
            return;
        }
        invalidate();
    }
}
