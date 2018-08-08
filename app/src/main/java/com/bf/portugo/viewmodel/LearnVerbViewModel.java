package com.bf.portugo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bf.portugo.data.FirebaseDataSource;
import com.bf.portugo.model.Verb;
import com.bf.portugo.repo.VerbRoomRepository;

import java.util.List;


/*
 * @author frielb
 * Created on 05/08/2018
 */
public class LearnVerbViewModel extends ViewModel {

    private String TAG = LearnVerbViewModel.class.getSimpleName();

    private Verb mVerb;

    public Verb getVerb() {
        return mVerb;
    }

    public void setVerb(Verb mVerb) {
        this.mVerb = mVerb;
    }

}
