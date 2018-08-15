package com.bf.portugo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.util.Log;

import com.bf.portugo.repo.VerbRoomRepository;


/*
 * @author frielb
 * Created on 02/08/2018
 */
@SuppressWarnings({"FieldCanBeLocal", "CanBeFinal", "unused"})
public class BaseViewModel extends AndroidViewModel {

    private final String TAG = BaseViewModel.class.getSimpleName();

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
    }

    public boolean getHasCheckedForTTS() {
        return mHasCheckedForTTS;
    }

    public void setHasCheckedForTTS(boolean mHasCheckedForTTS) {
        this.mHasCheckedForTTS = mHasCheckedForTTS;
    }



}
