package com.geek.geekmall.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.geek.geekmall.bean.StoreTitle;
import com.geek.geekmall.home.activity.StoreFragment;

import java.util.List;

/**
 * Created by apple on 4/29/15.
 */
public class StorePagerAdapter extends FragmentPagerAdapter {
    private List<StoreFragment> fragments;
    private List<StoreTitle> titles;

    public List<StoreFragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<StoreFragment> fragments) {
        this.fragments = fragments;
    }

    public StorePagerAdapter(final FragmentManager fm, List<StoreTitle> titles, List<StoreFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    public StorePagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    public void setTitles(List<StoreTitle> titles) {
        this.titles = titles;
    }

    public List<StoreTitle> getTitles() {
        return titles;
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
        return titles.get(position).getDisplayName();
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        if (titles == null) {
            return 0;
        }
        return titles.size();
    }
}
