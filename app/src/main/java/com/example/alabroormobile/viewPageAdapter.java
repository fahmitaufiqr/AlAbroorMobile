package com.example.alabroormobile;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class viewPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> isFragment = new ArrayList<>();
    private final List<String> isTitles = new ArrayList<>();

    public viewPageAdapter(FragmentManager fm){
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return isTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return isFragment.get(position);
    }

    @Override
    public int getCount() {
        return isTitles.size();
    }

    public void addFragment(Fragment fragment,String title){
        isFragment.add(fragment);
        isTitles.add(title);
    }
}
