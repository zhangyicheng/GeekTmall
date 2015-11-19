package com.geek.geekmall.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.geek.geekmall.R;

/**
 * Created by apple on 4/24/15.
 */
public class SearchView extends LinearLayout {
    public SearchView(Context context) {
        this(context,null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(final Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_search_view, this);
        findViewById(R.id.search).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                context.sendBroadcast(new Intent("com.geek.geekmall.action.search"));
            }
        });
    }
}
