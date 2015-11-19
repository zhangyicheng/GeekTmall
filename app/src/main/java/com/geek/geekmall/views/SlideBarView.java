package com.geek.geekmall.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geek.geekmall.R;

/**
 * Created by apple on 4/24/15.
 */
public class SlideBarView extends RelativeLayout {
    public SlideBarView(Context context) {
        this(context,null);
    }

    public SlideBarView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SlideBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(final Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_slidebar_view, this);
        findViewById(R.id.iv_slidebar).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                context.sendBroadcast(new Intent("com.geek.geekmall.action.open_user_drawerlayout"));
            }
        });
    }
}
