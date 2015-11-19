package com.geek.geekmall.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by apple on 4/22/15.
 */
public class GeekUtils {
    /**
     * 获取行高度
     * @param context
     * @param size
     * @return
     */
    public static float getLineHeight(Context context, float size) {
        Paint paint = new Paint();
        paint.setTextSize(size);
        paint.setTypeface(Typeface.createFromAsset(context.getResources().getAssets(), "assets/fonts/Roboto-Medium.ttf"));
        Paint.FontMetrics metrics = paint.getFontMetrics();
        return metrics.bottom - metrics.top;
    }

    public static int dpTopx(Context context, int dimen) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimen, resources.getDisplayMetrics());
    }

    public static GradientDrawable getGradientDrawable(Context context, int res1, int res2, int res3) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradientDrawable.setColor(res1);
        gradientDrawable.setStroke(dpTopx(context, 1), res2);
        gradientDrawable.setCornerRadius(res3);
        return gradientDrawable;
    }

    public static StateListDrawable getStateListDrawable(Drawable drawable1, Drawable drawable2) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, drawable2);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, drawable1);
        //没有任何状态时显示的图片，我们给它设置我空集合
        stateListDrawable.addState(new int[0], drawable1);
        return stateListDrawable;
    }

    public static void hideSoftInputMethod(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void setBackgroundResource(View view, int res) {
        int bottom = view.getPaddingBottom();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int left = view.getPaddingLeft();
        view.setBackgroundResource(res);
        view.setPadding(left, top, right, bottom);
    }

    /**
     * 扩大点击事件区域
     * 我们知道在oncreate中View.getWidth和View.getHeight无法获得一个view的高度和宽度，
     * 这是因为View组件布局要在onResume回调后完成。
     * 所以现在需要使用getViewTreeObserver().addOnGlobalLayoutListener()来获得宽度或者高度。
     * 这是获得一个view的宽度和高度的方法之一。
     *
     * @param view
     * @param paramInt1
     * @param paramInt2
     * @param paramInt3
     * @param paramInt4
     */
    public static void expandViewArea(View view, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new MyGloableLayoutListener(view, paramInt1, paramInt2, paramInt3, paramInt4));
    }

    @SuppressLint({"NewApi"})
    public static void setBackgroundDrawable(View view, Drawable drawable) {
        int bottom = view.getPaddingBottom();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int left = view.getPaddingLeft();
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        view.setPadding(left, top, right, bottom);
    }

    /**
     * 获得字符高度
     *
     * @param size
     * @return
     */
    public static float getFontHeight(float size) {
        Paint paint = new Paint();
        paint.setTextSize(size);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        //ascent 是baseline之上至字符最高处的距离
        //descent：是baseline之下至字符最低处的距离
        //leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
        //top：是指的是最高字符到baseline的值,即ascent的最大值
        //bottom：是指最低字符到baseline的值,即descent的最大值
        return metrics.descent - metrics.ascent;
    }


    public static int spTopx(Context context, int dimen) {
        if (context == null)
            return 0;
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dimen, resources.getDisplayMetrics());
    }

    public static void showInputMethod(Context context, View view) {
        if (view == null)
            return;
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        inputMethodManager.showSoftInput(view, 0);
    }

    public static void setBackgroundColor(View view, int res) {
        int bottom = view.getPaddingBottom();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int left = view.getPaddingLeft();
        view.setBackgroundColor(res);
        view.setPadding(left, top, right, bottom);
    }


}
