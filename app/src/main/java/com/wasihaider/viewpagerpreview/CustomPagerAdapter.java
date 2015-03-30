package com.wasihaider.viewpagerpreview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    Fragment fragments [];
    public CustomPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        fragments = new Fragment[count];
        for (int i = 0; i < fragments.length; i++){
            fragments[i] = Test.newInstance(i);
        }

    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }
}