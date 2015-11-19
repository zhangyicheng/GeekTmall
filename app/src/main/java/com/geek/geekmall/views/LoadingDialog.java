package com.geek.geekmall.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.geek.geekmall.R;

/**
 * Created by apple on 6/1/15.
 */
public class LoadingDialog extends Dialog {
    Context context;

    ImageView vLoading; // 圆型进度条
    Animation anim;// 动画
    AnimationDrawable drawable;
    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public LoadingDialog(Context context) {
        super(context,R.style.DialogLoading);
    }


    /**
     * Similar to {@link Activity#onCreate}, you should initialize your dialog
     * in this method, including calling {@link #setContentView}.
     *
     * @param savedInstanceState If this dialog is being reinitalized after a
     *                           the hosting activity was previously shut down, holds the result from
     *                           the most recent call to {@link #onSaveInstanceState}, or null if this
     *                           is the first time.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.PROGRESS_VISIBILITY_ON);
        setContentView(R.layout.loading_dialog);
        setCanceledOnTouchOutside(false);
        vLoading = (ImageView) findViewById(R.id.loading);
        drawable = (AnimationDrawable) vLoading.getDrawable();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 按下了键盘上返回按钮
            this.dismiss();
            return true;
        }
        return false;
    }
    @Override
    public void show() {
        super.show();
//        vLoading.startAnimation(anim);
//        drawable.start();
    }
}
