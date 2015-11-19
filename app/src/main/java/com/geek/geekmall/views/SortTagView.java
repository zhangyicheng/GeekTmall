package com.geek.geekmall.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geek.geekmall.R;

/**
 * Created by apple on 5/13/15.
 */
public class SortTagView extends LinearLayout {
    private LinearLayout mSortLayout;
    private ImageView mArrow;
    private int type = 0;
    private TextView mTitleView;
    private String title;
    private TatOnclickListener listener;

    public SortTagView(Context context) {
        this(context, null);
    }

    public SortTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.sort_tag_layout, this);
        mSortLayout = (LinearLayout) view.findViewById(R.id.sort_view);
        mTitleView = (TextView) view.findViewById(R.id.title);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SortTag);
        this.title = typedArray.getString(R.styleable.SortTag_text);
        if (title != null && !title.isEmpty()) {
            mTitleView.setText(title);
        }

        mArrow = (ImageView) findViewById(R.id.arrow);
        mSortLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type > 0) {
                    mArrow.setBackgroundResource(R.drawable.up_down_arrow_three);
                    type = -type;
                } else if (type <0) {
                    mArrow.setBackgroundResource(R.drawable.up_down_arrow_two);
                    type = -type;
                }
                if (listener != null) {
                    listener.onClick(type);
                }

            }
        });
    }

    public void setType(int type) {
        this.type = type;
    }

    public void reset() {
        mArrow.setBackgroundResource(R.drawable.up_down_arrow_one);
    }

    public TatOnclickListener getListener() {
        return listener;
    }

    public void setListener(TatOnclickListener listener) {
        this.listener = listener;
    }

    public interface TatOnclickListener {
        void onClick(int order);
    }
}
