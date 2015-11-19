package com.geek.geekmall.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.geek.geekmall.R;

/**
 * Created by apple on 6/11/15.
 */
public class SexDialog extends Dialog{
    private TextView man;
    private TextView girl;
    private TextView cancel;
    private View.OnClickListener listener;
    private View.OnClickListener listener2;
    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public SexDialog(Context context, View.OnClickListener listener, View.OnClickListener listener2) {
        super(context,R.style.dialog);
        this.listener = listener;
        this.listener2 = listener2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_sex_dialog);
        man = (TextView) findViewById(R.id.man);
        girl = (TextView) findViewById(R.id.girl);
        cancel = (TextView) findViewById(R.id.cancel);

        man.setOnClickListener(listener);
        girl.setOnClickListener(listener2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
