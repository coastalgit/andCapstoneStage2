package com.bf.portugo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.bf.portugo.model.Verb;


/*
 * @author frielb
 * Created on 05/08/2018
 */
public class HomeViewModel extends ViewModel {

    private String TAG = HomeViewModel.class.getSimpleName();

    private boolean mHasAlreadySpokenWelcome;

    public HomeViewModel() {
        mHasAlreadySpokenWelcome = false;
    }

    public boolean getHasAlreadySpokenWelcome() {
        return mHasAlreadySpokenWelcome;
    }

    public void setHasAlreadySpokenWelcome(boolean mHasAlreadySpokenWelcome) {
        this.mHasAlreadySpokenWelcome = mHasAlreadySpokenWelcome;
    }

}
