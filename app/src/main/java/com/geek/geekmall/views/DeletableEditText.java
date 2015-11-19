package com.geek.geekmall.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.geek.geekmall.R;

/**
 * Created by apple on 4/22/15.
 */
public class DeletableEditText extends CustomEditText implements View.OnFocusChangeListener {
    private Drawable mDrawable;
    private Context mContext;
    private OnFocusChangeListener listener;

    public DeletableEditText(Context context) {
        this(context,null);
        mContext = context;
    }

    public DeletableEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        mContext = context;
    }

    public DeletableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        this.mDrawable = this.mContext.getResources().getDrawable(R.drawable.edittext_icon);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textChange();
            }
        });
        super.setOnFocusChangeListener(this);
    }

    private void textChange() {
        if (length() >= 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, this.mDrawable, null);
            int i = (int) TypedValue.applyDimension(1, 5.0F, this.mContext.getResources().getDisplayMetrics());
            setPadding(getPaddingLeft(), getPaddingTop(), i, getPaddingBottom());
            return;
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }

    /**
     * Called when the focus state of a view has changed.
     *
     * @param v        The view whose state has changed.
     * @param hasFocus The new focus state of v.
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (this.listener != null)
            this.listener.onFocusChange(v, hasFocus);
        if ((length() > 1) && (hasFocus)) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, this.mDrawable, null);
            int i = (int) TypedValue.applyDimension(1, 5.0F, this.mContext.getResources().getDisplayMetrics());
            setPadding(getPaddingLeft(), getPaddingTop(), i, getPaddingBottom());
            return;
        }
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }


    public boolean onTouchEvent(MotionEvent event) {
        if ((this.mDrawable != null) && (event.getAction() == MotionEvent.ACTION_UP)) {
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = (-50 + rect.right);
            if (rect.contains(x, y))
                setText("");
        }
        return super.onTouchEvent(event);
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener paramOnFocusChangeListener) {
        if (paramOnFocusChangeListener != this)
            this.listener = paramOnFocusChangeListener;
    }
}
