package com.bf.portugo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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
public class LearnVerbsMainViewModel extends AndroidViewModel {

    private String TAG = LearnVerbsMainViewModel.class.getSimpleName();

    private VerbRoomRepository mRepo;
    private FirebaseDataSource mDataSource;
    private boolean mHasAlreadyPolledDataSource;

    public LearnVerbsMainViewModel(@NonNull Application application) {
        super(application);

        mRepo = new VerbRoomRepository(application);
        mHasAlreadyPolledDataSource = false;
        mDataSource = new FirebaseDataSource();
        refreshRoomFromDataSource();
    }

    public LiveData<List<Verb>> getObservableVerbsAll() {
        if (mRepo != null)
            return mRepo.getVerbs(true);
        return null;
    }

    public LiveData<List<Verb>> getObservableVerbsEssential() {
        if (mRepo != null)
            return mRepo.getVerbsEssential();
        return null;
    }

    public void refreshRoomFromDataSource(){
        Log.d(TAG, "refreshRoomFromDataSource: ");
        mRepo.fillDBFromDataSource(mDataSource);
    }

    public void subscribeToChildUpdates(){
        mRepo.subscribeToChildUpdates(mDataSource);
    }

    public boolean getHasAlreadyPolledDataSource() {
        return mHasAlreadyPolledDataSource;
    }

    public void setHasAlreadyPolledDataSource(boolean mHasAlreadyPolledDataSource) {
        this.mHasAlreadyPolledDataSource = mHasAlreadyPolledDataSource;
    }

}
