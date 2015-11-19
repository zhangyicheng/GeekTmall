package com.geek.geekmall.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.geek.geekmall.R;
import com.geek.geekmall.bean.ReFundType;
import com.geek.geekmall.utils.ScreenUtils;


/**
 * @author angelo
 */
public class ReFundTypeDialog extends Dialog {
    private TextView one;
    private TextView two;
    private TextView three;
    private ReFundType type;
    private ReFundTypeListener listener;

    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public ReFundTypeDialog(Context context) {
        super(context, R.style.dialog);

    }

    public void setListener(ReFundTypeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refund_dialog);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (ScreenUtils.getScreenWidth(getContext()) * 0.8f);
        getWindow().setAttributes(lp);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = new ReFundType(1, one.getText().toString());
                listener.setType(type);
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = new ReFundType(2, two.getText().toString());
                listener.setType(type);
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = new ReFundType(3, three.getText().toString());
                listener.setType(type);
            }
        });
    }

    public ReFundType getType() {
        return type;
    }

    public interface ReFundTypeListener {
        void setType(ReFundType type);
    }
}