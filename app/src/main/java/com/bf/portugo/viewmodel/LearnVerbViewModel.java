package com.bf.portugo.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.bf.portugo.model.Verb;


/*
 * @author frielb
 * Created on 05/08/2018
 */
public class LearnVerbViewModel extends ViewModel {

    private String TAG = LearnVerbViewModel.class.getSimpleName();

    private Verb mVerb;
    private int mTabPosition;

    public Verb getVerb() {
        return mVerb;
    }

    public void setVerb(Verb mVerb) {
        this.mVerb = mVerb;
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    public void setTabPosition(int mTabPosition) {
        this.mTabPosition = mTabPosition;
    }

}
