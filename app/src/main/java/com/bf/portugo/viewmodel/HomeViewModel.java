package com.bf.portugo.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;


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
        Log.i(TAG, "getHasAlreadySpokenWelcome: SET");
        return mHasAlreadySpokenWelcome;
    }

    public void setHasAlreadySpokenWelcome(boolean mHasAlreadySpokenWelcome) {
        this.mHasAlreadySpokenWelcome = mHasAlreadySpokenWelcome;
        Log.i(TAG, "setHasAlreadySpokenWelcome: SET-"+String.valueOf(mHasAlreadySpokenWelcome));
    }

}
