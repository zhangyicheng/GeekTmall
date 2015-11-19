package com.geek.geekmall.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.geek.geekmall.R;

/**
 * Created by apple on 4/24/15.
 */
public class BaselineCustomTextView extends TextView {
    private int height;
    private TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    public BaselineCustomTextView(Context context) {
        this(context, null);
    }

    public BaselineCustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaselineCustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.paint.setColor(getTextColors().getColorForState(getDrawableState(), getResources().getColor(R.color.yellow)));
        this.paint.setTextSize(getTextSize());
        this.paint.setLinearText(true);
        this.paint.density = getResources().getDisplayMetrics().density;
        Rect localRect = new Rect();
        this.paint.getTextBounds(getText().toString(), 0, getText().length(), localRect);
        this.height = localRect.height();
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawText(getText().toString(), 0.0F, -1 + getHeight(), this.paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(this.height, MeasureSpec.EXACTLY));
    }
}
