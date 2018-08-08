package com.bf.portugo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/*
 * @author frielb
 * Created on 07/08/2018
 */
public class LearnAllVerbsPagerAdapter extends FragmentPagerAdapter {

    private static int TABCOUNT = 2;

    public LearnAllVerbsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return TABCOUNT;
    }
}
