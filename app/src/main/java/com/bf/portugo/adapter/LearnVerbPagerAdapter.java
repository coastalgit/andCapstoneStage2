package com.bf.portugo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bf.portugo.common.Enums;
import com.bf.portugo.ui.fragment.LearnMainVerbsFragment;
import com.bf.portugo.ui.fragment.LearnVerbFragment;


/*
 * @author frielb
 * Created on 07/08/2018
 */
public class LearnVerbPagerAdapter extends FragmentPagerAdapter {

    private static int TABCOUNT = 3;

    public LearnVerbPagerAdapter(FragmentManager fm) {
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
                frag = LearnVerbFragment.newInstance(Enums.VerbTense.PAST);
                break;
            case 1:
                frag = LearnVerbFragment.newInstance(Enums.VerbTense.PRESENT);
                break;
            case 2:
                frag = LearnVerbFragment.newInstance(Enums.VerbTense.FUTURE);
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return TABCOUNT;
    }
}
