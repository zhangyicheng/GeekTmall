package com.geek.geekmall.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geek.geekmall.R;

/**
 * Created by apple on 6/11/15.
 */
public class CameraDialog extends Dialog{
    private TextView photo;
    private TextView camera;
    private View.OnClickListener listener;
    private View.OnClickListener listener2;
    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public CameraDialog(Context context,View.OnClickListener listener, View.OnClickListener listener2) {
        super(context,R.style.dialog);
        this.listener = listener;
        this.listener2 = listener2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_photo_dialog);
        photo = (TextView) findViewById(R.id.select_photo);
        camera = (TextView) findViewById(R.id.take_photo);
        photo.setOnClickListener(listener);
        camera.setOnClickListener(listener2);
    }
}
