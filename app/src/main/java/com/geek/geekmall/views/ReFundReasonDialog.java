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
public class ReFundReasonDialog extends Dialog {
    private TextView one;
    private TextView two;
    private TextView three;
    private TextView four;
    private TextView five;
    private TextView six;
    private TextView seven;

    private ReFundType type;
    private ReFundTypeListener listener;

    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public ReFundReasonDialog(Context context) {
        super(context, R.style.dialog);

    }

    public void setListener(ReFundTypeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refund_reason_dialog);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (ScreenUtils.getScreenWidth(getContext()) * 0.8f);
        getWindow().setAttributes(lp);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        five = (TextView) findViewById(R.id.five);
        six = (TextView) findViewById(R.id.six);
        seven = (TextView) findViewById(R.id.seven);

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
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = new ReFundType(4, four.getText().toString());
                listener.setType(type);
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = new ReFundType(5, five.getText().toString());
                listener.setType(type);
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = new ReFundType(6, six.getText().toString());
                listener.setType(type);
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = new ReFundType(7, seven.getText().toString());
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