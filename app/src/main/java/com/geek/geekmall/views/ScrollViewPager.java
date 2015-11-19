package com.geek.geekmall.views;

/**
 * Created by apple on 5/8/15.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.geek.geekmall.utils.ListViewUtil;
import com.geek.geekmall.utils.MyLog;

/**
 * 自动适应高度的ViewPager
 *
 * @author
 */
public class ScrollViewPager extends ViewPager {

    public ScrollViewPager(Context context) {
        super(context);
    }

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

        @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
////            int h = new ListViewUtil().setListViewHeightBasedOnChildren((ListViewForScrollView)child);
//            if (h > height)
//                height = h;
//        }
//            MyLog.debug(ScrollViewPager.class,"onmeasure==========="+height);
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
