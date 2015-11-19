package com.gridpasswordview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.geekmall.R;


/**
 * 下载Dialog
 * Created by angelo on 14-10-1.
 */
public class PasswordDialog extends Dialog {
    private Button cancel;
    private Button ok;
    private TextView title;
    private GridPasswordView passwordView;
    private View.OnClickListener listener;
    private View.OnClickListener cancelListener;
    private RelativeLayout mTitleLayout;

    private String tit = "";

    public PasswordDialog(Context context, String title, View.OnClickListener listener) {
        super(context, R.style.dialog);
        this.listener = listener;
        setTitle(title);
    }

    public View.OnClickListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public void setTitle(String title) {
        tit = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_custom_dialog);

        cancel = (Button) findViewById(R.id.cancel);
        ok = (Button) findViewById(R.id.ok);
        title = (TextView) findViewById(R.id.title);
        passwordView = (GridPasswordView) findViewById(R.id.password);
        if (cancelListener == null) {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    passwordView.clearPassword();
                    dismiss();
                }
            });
        } else {
            cancel.setOnClickListener(cancelListener);
        }

        mTitleLayout = (RelativeLayout) findViewById(R.id.title_layout);

        if (!tit.equals("")) {
            title.setText(tit);
        }

        ok.setOnClickListener(listener);

    }

    public String getPasswodViewString() {
        return passwordView.getPassWord();
    }

    public void clear() {
        passwordView.clearPassword();
    }
}
