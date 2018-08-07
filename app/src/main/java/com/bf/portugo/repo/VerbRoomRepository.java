package com.bf.portugo.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.bf.portugo.common.Constants;
import com.bf.portugo.common.Enums;
import com.bf.portugo.data.FirebaseDataSource;
import com.bf.portugo.data.IVerbDataSource;
import com.bf.portugo.data.VerbDao;
import com.bf.portugo.data.VerbDatabase;
import com.bf.portugo.model.Verb;

import java.util.List;

/*
 * @author frielb
 * Created on 03/08/2018
 */
public class VerbRoomRepository {
    public static final String TAG = VerbRoomRepository.class.getSimpleName();

    public static enum VerbFilter{
        ALL,
        ESSENTIAL
    }

    private VerbDatabase mDb_Verb;
    private VerbDao mDao_Verb;
    private LiveData<List<Verb>> mObservableVerbs;
    private LiveData<List<Verb>> mObservableVerbsEssential;

    public VerbRoomRepository(Application application) {
        mDb_Verb = VerbDatabase.getDatabaseInstance(application);
        mDao_Verb = mDb_Verb.verbDao();
        //populateVerbsFromDB();
    }

    private int getRecordCount(LiveData<List<Verb>> verbList){
        if ((verbList != null) && verbList.getValue() != null && (verbList.getValue().size() > 0))
            return verbList.getValue().size();
        return 0;
    }

    public void fillDBFromDataSource(IVerbDataSource dataSource){
        Log.d(TAG, "fillDBFromDataSource: ");
        if (dataSource != null) {
            dataSource.fetchVerbItems(new IVerbDataSource.verbDataSourceListener() {
                @Override
                public void onSuccess(List<Verb> verbs) {
                    Log.d(TAG, "onSuccess: Verb count=" + String.valueOf(getRecordCount(mObservableVerbs)) + " DS COUNT=" + String.valueOf(verbs.size()));

                    //refresh local db
                    new deleteAllTask(mDao_Verb).execute();
                    new insertBatchTask(mDao_Verb).execute(verbs);
                }

                @Override
                public void onError(Enums.ErrorCode errorCode, String errorMsg) {
                    // TODO: 03/08/2018
                }
            });
        }
    }

    public void deleteAllRoomDbRecs(){
        int recCount = getRecordCount(mObservableVerbs);
        Log.d(TAG, "deleteAllRoomDbRecs: COUNT="+String.valueOf(recCount));
        new deleteAllTask(mDao_Verb).execute();
    }

    public void subscribeToChildUpdates(FirebaseDataSource dataSource){
        if (dataSource != null) {
            dataSource.attachChildListener(new FirebaseDataSource.IVerbChildEvent() {
               @Override
               public void onVerbAdded(Verb verb) {
                   new insertTask(mDao_Verb).execute(verb);
               }

               @Override
               public void onVerbChanged(Verb verb) {
                   // Replace on conflict
                   new insertTask(mDao_Verb).execute(verb);
               }

               @Override
               public void onVerbDeleted(Verb verb) {
                   new deleteItemTask(mDao_Verb).execute(verb);
               }
           });
        }
    }

    public void unsubscribeFromChildUpdates(FirebaseDataSource dataSource){
        if (dataSource != null)
            dataSource.removeChildListener();
    }

    private void refreshLiveDataSets(){
        Log.d(TAG, "refreshLiveDataSets: Count (All)="+String.valueOf(getRecordCount(getVerbs())));
        Log.d(TAG, "refreshLiveDataSets: Count (Essential)="+String.valueOf(getRecordCount(getVerbsEssential())));
    }

    public LiveData<List<Verb>> getVerbs() {
        if (mObservableVerbs == null)
            mObservableVerbs = new MutableLiveData<>();

        mObservableVerbs = populateVerbsFromDB(mObservableVerbs, VerbFilter.ALL);
        return mObservableVerbs;
    }

    public LiveData<List<Verb>> getVerbsEssential() {
        if (mObservableVerbsEssential == null)
            mObservableVerbsEssential = new MutableLiveData<>();

        mObservableVerbsEssential = populateVerbsFromDB(mObservableVerbsEssential, VerbFilter.ESSENTIAL);
        return mObservableVerbsEssential;
    }

    private LiveData<List<Verb>> populateVerbsFromDB(LiveData<List<Verb>> verbList, VerbFilter filter){
//        if (verbList == null)
//            verbList = new MutableLiveData<>();

        if (mDao_Verb != null){
            if (filter == VerbFilter.ESSENTIAL)
                verbList = mDao_Verb.getListVerbItemsEssential(Constants.VERB_CLASSIFICATIONCEILING_ESSENTIAL);
            else
                verbList = mDao_Verb.getListVerbItems();

            if ((verbList != null) && (verbList.getValue() != null))
                Log.d(TAG, "populateVerbsFromDB: Room Verb count="+String.valueOf(verbList.getValue().size()));
            else
                Log.d(TAG, "populateVerbsFromDB: Room Verb count=NULL");
        }

        return verbList;
    }


    //region TASKS

    private class insertTask extends AsyncTask<Verb, Void, Void> {

        private VerbDao mTaskDao;

        insertTask(VerbDao dao) {
            mTaskDao = dao;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            refreshLiveDataSets();
        }

        @Override
        protected Void doInBackground(final Verb... verbParams) {
            mTaskDao.insertVerbItem(verbParams[0]);
            return null;
        }
    }

    private class insertBatchTask extends AsyncTask<List<Verb>, Void, Void> {

        private VerbDao mTaskDao;
        private int mCounter;

        insertBatchTask(VerbDao dao) {
            mTaskDao = dao;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: Inserted count="+String.valueOf(mCounter));
            refreshLiveDataSets();        }

        @Override
        protected Void doInBackground(List<Verb>... lists) {
            mCounter = 0;
            List<Verb> vList = lists[0];
            for (Verb v : vList) {
                mTaskDao.insertVerbItem(v);
                mCounter++;
            }
            return null;
        }
    }

    private class deleteAllTask extends AsyncTask<Void, Void, Void> {

        private VerbDao mTaskDao;

        deleteAllTask(VerbDao dao) {
            mTaskDao = dao;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            refreshLiveDataSets();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTaskDao.deleteAllRecords();
            return null;
        }
    }

    private class deleteItemTask extends AsyncTask<Verb, Void, Void> {

        private VerbDao mTaskDao;

        deleteItemTask(VerbDao dao) {
            mTaskDao = dao;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            refreshLiveDataSets();
        }

        @Override
        protected Void doInBackground(Verb... verbParams) {
            mTaskDao.deleteVerbItem(verbParams[0]);
            return null;
        }
    }
    //endregion TASKS
}
