package com.bf.portugo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.bf.portugo.data.FirebaseDataSource;


/*
 * @author frielb
 * Created on 02/08/2018
 */
public class BaseViewModel extends AndroidViewModel {

    private String TAG = BaseViewModel.class.getSimpleName();

    private boolean mHasCheckedForTTS;

    public BaseViewModel(Application application) {
        super(application);
        Log.d(TAG, "MainViewModel: CREATED");
        mHasCheckedForTTS = false;
    }

    public boolean getHasCheckedForTTS() {
        return mHasCheckedForTTS;
    }

    public void setHasCheckedForTTS(boolean mHasCheckedForTTS) {
        this.mHasCheckedForTTS = mHasCheckedForTTS;
    }

}
