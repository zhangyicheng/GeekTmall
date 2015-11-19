package com.geek.geekmall.utils;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by apple on 4/23/15.
 */
class MyGloableLayoutListener
        implements ViewTreeObserver.OnGlobalLayoutListener {
    View view;
    int top, bottom, left, right;

    MyGloableLayoutListener(View view, int top, int bottom, int left, int right) {
        this.view = view;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public void onGlobalLayout() {
        Rect rect = new Rect();
        this.view.getHitRect(rect);
        rect.top -= this.top;
        rect.bottom += this.bottom;
        rect.left -= this.left;
        rect.right += this.right;
        ((View) this.view.getParent()).setTouchDelegate(new TouchDelegate(rect, this.view));

    }
}