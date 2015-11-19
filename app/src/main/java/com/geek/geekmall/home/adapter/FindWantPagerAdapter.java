package com.geek.geekmall.home.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by apple on 4/28/15.
 */
public class FindWantPagerAdapter extends PagerAdapter {
    private List<View> views;
    private String[] titles = {"发现","开售","想要"};
    public FindWantPagerAdapter(List<View> views) {
        this.views = views;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(View view, int position) {
        ((ViewPager) view).addView(views.get(position), 0);
        return views.get(position);
    }
}

