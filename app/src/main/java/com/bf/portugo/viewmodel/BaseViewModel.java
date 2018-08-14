package com.bf.portugo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.bf.portugo.data.FirebaseDataSource;
import com.bf.portugo.model.Verb;
import com.bf.portugo.repo.VerbRoomRepository;
import com.bf.portugo.util.VerbHelper;

import java.util.List;


/*
 * @author frielb
 * Created on 02/08/2018
 */
public class BaseViewModel extends AndroidViewModel {

    private String TAG = BaseViewModel.class.getSimpleName();

    private boolean mHasCheckedForTTS;
    private VerbRoomRepository mRepo;

    public BaseViewModel(Application application) {
        super(application);
        mRepo = new VerbRoomRepository(application);
        Log.d(TAG, "BaseViewModel: CREATED");
        mHasCheckedForTTS = false;
    }

    public void hasVerbRecordsInRoom(VerbRoomRepository.IRoomQueryTaskComplete listener){
        mRepo.fetchVerbsFromRoomDB(listener);
//        List<Verb> verbList = mRepo.getVerbsSynchronously();
//        Log.d(TAG, "hasVerbRecordsInRoom: "+String.valueOf(VerbHelper.getListRecordCount(verbList)));
//        return (VerbHelper.getListRecordCount(verbList) > 0);
    }

    public boolean getHasCheckedForTTS() {
        return mHasCheckedForTTS;
    }

    public void setHasCheckedForTTS(boolean mHasCheckedForTTS) {
        this.mHasCheckedForTTS = mHasCheckedForTTS;
    }



}
