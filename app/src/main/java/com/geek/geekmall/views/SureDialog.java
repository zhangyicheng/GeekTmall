package com.geek.geekmall.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geek.geekmall.R;

/**
 * Created by apple on 6/11/15.
 */
public class SureDialog extends Dialog{
    private Button cancel;
    private Button okBtn;
    private TextView titleView;
    private View.OnClickListener listener;
    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public SureDialog(Context context, View.OnClickListener listener) {
        super(context,R.style.dialog);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sure_dialog);
        okBtn = (Button) findViewById(R.id.ok_button);
        cancel = (Button) findViewById(R.id.cancel_button);
        titleView = (TextView) findViewById(R.id.title);
        okBtn.setOnClickListener(listener);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setTitle(String title) {
        if (titleView != null){
            titleView.setText(title);
        }
    }
}
