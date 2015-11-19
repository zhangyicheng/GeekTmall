package com.geek.geekmall.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * Created by apple on 3/20/15.
 */
public class ExpandListViewForScrollView extends ExpandableListView {
    public ExpandListViewForScrollView(Context context) {
        super(context);
    }

    public ExpandListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandListViewForScrollView(Context context, AttributeSet attrs,
                                       int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
