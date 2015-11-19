package com.geek.geekmall.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by apple on 4/22/15.
 */
public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        this(context,null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init(context);
    }
    private void init(Context context){
        setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Medium.ttf"));
    }

}
