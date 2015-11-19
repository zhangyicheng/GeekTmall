package com.geek.geekmall.category.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.geek.geekmall.category.activity.SearchFragment;

import java.util.List;

/**
 * Created by apple on 4/29/15.
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    private String titles[] = {"分类", "品牌"};

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public SearchPagerAdapter(final FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public SearchPagerAdapter(final FragmentManager fm) {
        super(fm);
    }


    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles == null) {
            return "";
        }
        return titles[position];
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        if (titles == null) {
            return 0;
        }
        return titles.length;
    }
}
