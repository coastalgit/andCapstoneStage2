package com.bf.portugo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bf.portugo.common.Enums;
import com.bf.portugo.ui.fragment.LearnMainVerbsFragment;


/*
 * @author frielb
 * Created on 07/08/2018
 */
@SuppressWarnings("FieldCanBeLocal")
public class LearnMainVerbsPagerAdapter extends FragmentPagerAdapter {

    private static final int TABCOUNT = 2;

    public LearnMainVerbsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // NOTE TO SELF:
        // The fragments themselves persist within the SupportFragmentManager,
        // hence this method is called only once (for each fragment instance created).

        Fragment frag = null;
        switch (position){
            case 0:
                frag = LearnMainVerbsFragment.newInstance(Enums.VerbFilter.ESSENTIAL);
                break;
            case 1:
                frag = LearnMainVerbsFragment.newInstance(Enums.VerbFilter.ALL);
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return TABCOUNT;
    }
}
