package com.bf.portugo.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.bf.portugo.model.Verb;


/*
 * @author frielb
 * Created on 05/08/2018
 */
public class LearnSamplesViewModel extends ViewModel {

    private String TAG = LearnSamplesViewModel.class.getSimpleName();

    private Verb mVerb;

    public Verb getVerb() {
        return mVerb;
    }

    public void setVerb(Verb mVerb) {
        this.mVerb = mVerb;
    }

}
