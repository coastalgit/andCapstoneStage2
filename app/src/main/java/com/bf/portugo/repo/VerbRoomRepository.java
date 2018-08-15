package com.bf.portugo.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.bf.portugo.data.FirebaseDataSource;
import com.bf.portugo.data.IVerbDataSource;
import com.bf.portugo.data.VerbDao;
import com.bf.portugo.data.VerbDatabase;
import com.bf.portugo.model.Verb;

import java.util.List;

import static com.bf.portugo.common.Constants.VERB_CLASSIFICATIONCEILING_ESSENTIAL;
import static com.bf.portugo.common.Enums.*;
import static com.bf.portugo.util.VerbHelper.getLiveListRecordCount;

/*
 * @author frielb
 * Created on 03/08/2018
 */
@SuppressWarnings({"Convert2MethodRef", "SpellCheckingInspection", "JavaDoc"})
public class VerbRoomRepository {
    private static final String TAG = VerbRoomRepository.class.getSimpleName();

    private interface IAsyncTaskComplete{
        void doRefresh();
    }

    public interface IRoomQueryTaskComplete{
        void onVerbListFromRoom(List<Verb> verbs);
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final VerbDatabase mDb_Verb;
    private final VerbDao mDao_Verb;
    private LiveData<List<Verb>> mObservableVerbs;
    private LiveData<List<Verb>> mObservableVerbsEssential;

    public VerbRoomRepository(Application application) {
        mDb_Verb = VerbDatabase.getDatabaseInstance(application);
        mDao_Verb = mDb_Verb.verbDao();
    }

    public void fillDBFromDataSource(IVerbDataSource dataSource){
        Log.d(TAG, "fillDBFromDataSource: ");
        if (dataSource != null) {
            dataSource.fetchVerbItems(new IVerbDataSource.VerbDataSourceListener() {
                @Override
                public void onSuccess(List<Verb> verbs) {
                    if (verbs != null) {
                        Log.d(TAG, "onSuccess: Verb count=" + String.valueOf(getLiveListRecordCount(mObservableVerbs)) + " DS COUNT=" + String.valueOf(verbs.size()));
                        //refresh local db
                        new deleteAllTask(mDao_Verb, () -> refreshLiveDataSets()).execute();
                        new insertBatchTask(mDao_Verb, verbs, () -> refreshLiveDataSets()).execute();
                    }
                }

                @Override
                public void onError(ErrorCode errorCode, String errorMsg) {
                    //
                }
            });
        }
    }

    @SuppressWarnings("unused")
    public void deleteAllRoomDbRecs(){
        int recCount = getLiveListRecordCount(mObservableVerbs);
        Log.d(TAG, "deleteAllRoomDbRecs: COUNT="+String.valueOf(recCount));
        new deleteAllTask(mDao_Verb, () -> refreshLiveDataSets()).execute();
    }

    public void subscribeToChildUpdates(FirebaseDataSource dataSource){
        if (dataSource != null) {
            dataSource.attachChildListener(new FirebaseDataSource.IVerbChildEvent() {
               @Override
               public void onVerbAdded(Verb verb) {
                   new insertTask(mDao_Verb, () -> refreshLiveDataSets()).execute(verb);
               }

               @Override
               public void onVerbChanged(Verb verb) {
                   // Replace on conflict
                   new insertTask(mDao_Verb, () -> refreshLiveDataSets()).execute(verb);
               }

               @Override
               public void onVerbDeleted(Verb verb) {
                   new deleteItemTask(mDao_Verb,  () -> refreshLiveDataSets()).execute(verb);
               }
           });
        }
    }

    @SuppressWarnings("unused")
    public void unsubscribeFromChildUpdates(FirebaseDataSource dataSource){
        if (dataSource != null)
            dataSource.removeChildListener();
    }

    private void refreshLiveDataSets(){
        int countAll = getLiveListRecordCount(getVerbs(true));
        Log.d(TAG, "refreshLiveDataSets: Count (All)="+String.valueOf(countAll));
        int countEss = getLiveListRecordCount(getVerbsEssential());
        Log.d(TAG, "refreshLiveDataSets: Count (Essential)="+String.valueOf(countEss));
    }

    /**
     * Method allowing a refresh from Room or just returning the existing list if already populated
     *
     * @param refreshFromRoom
     * @return
     */
    public LiveData<List<Verb>> getVerbs(boolean refreshFromRoom) {
        if ((refreshFromRoom) || (getLiveListRecordCount(mObservableVerbs) == 0))
            populateVerbsFromDB_All();

        return mObservableVerbs;
    }

    public LiveData<List<Verb>> getVerbsEssential() {
        populateVerbsFromDB_Essential();
        return mObservableVerbsEssential;
    }

    private void populateVerbsFromDB_All(){
        if (mDao_Verb != null){
            if (mObservableVerbs == null)
                mObservableVerbs = new MutableLiveData<>();

            mObservableVerbs = mDao_Verb.getListVerbItems();
        }
    }

    private void populateVerbsFromDB_Essential(){
        if (mDao_Verb != null){
            if (mObservableVerbsEssential == null)
                mObservableVerbsEssential = new MutableLiveData<>();

            mObservableVerbsEssential = mDao_Verb.getListVerbItemsEssential(VERB_CLASSIFICATIONCEILING_ESSENTIAL);
        }
    }

    //Unobserved (non LiveData) call
    public void fetchVerbsFromRoomDB(IRoomQueryTaskComplete listener){
        new selectAllTask(mDao_Verb, listener).execute();
    }

    //region TASKS (STATIC INNER CLASSES)

    private static class selectAllTask extends AsyncTask<Void, Void, List<Verb>> {

        private final VerbDao mTaskDao;
        private final IRoomQueryTaskComplete mListener;

        selectAllTask(VerbDao dao, IRoomQueryTaskComplete listener) {
            mTaskDao = dao;
            mListener = listener;
        }

        @Override
        protected List<Verb> doInBackground(Void... voids) {
            return mTaskDao.getListVerbItemsSync();
        }

        @Override
        protected void onPostExecute(List<Verb> roomVerbs) {
            super.onPostExecute(roomVerbs);
            if (mListener != null)
                mListener.onVerbListFromRoom(roomVerbs);
        }
    }

    private static class insertTask extends AsyncTask<Verb, Void, Void> {

        private final VerbDao mTaskDao;
        private final IAsyncTaskComplete mListener;

        insertTask(VerbDao dao, IAsyncTaskComplete listener) {
            mTaskDao = dao;
            mListener = listener;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mListener != null)
                mListener.doRefresh();
        }

        @Override
        protected Void doInBackground(final Verb... verbParams) {
            mTaskDao.insertVerbItem(verbParams[0]);
            return null;
        }
    }

    //Unchecked generics array warning
    private static class insertBatchTask extends AsyncTask<Void, Void, Void> {

        private final VerbDao mTaskDao;
        private int mCounter;
        private final IAsyncTaskComplete mListener;
        private final List<Verb> mVerbList;

        insertBatchTask(VerbDao dao, List<Verb> verbList, IAsyncTaskComplete listener) {
            mTaskDao = dao;
            mVerbList = verbList;
            mListener = listener;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: Inserted count=" + String.valueOf(mCounter));
            if (mListener != null)
                mListener.doRefresh();
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(Void... voids) {
            mCounter = 0;
            if (mVerbList != null){
                for (Verb v : mVerbList) {
                    mTaskDao.insertVerbItem(v);
                    mCounter++;
                }
            }
            return null;
        }
    }

    private static class deleteAllTask extends AsyncTask<Void, Void, Void> {

        private final VerbDao mTaskDao;
        private final IAsyncTaskComplete mListener;

        deleteAllTask(VerbDao dao, IAsyncTaskComplete listener) {
            mTaskDao = dao;
            mListener = listener;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mListener != null)
                mListener.doRefresh();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mTaskDao.deleteAllRecords();
            return null;
        }
    }

    private static class deleteItemTask extends AsyncTask<Verb, Void, Void> {

        private final VerbDao mTaskDao;
        private final IAsyncTaskComplete mListener;

        deleteItemTask(VerbDao dao, IAsyncTaskComplete listener) {
            mTaskDao = dao;
            mListener = listener;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mListener != null)
                mListener.doRefresh();
        }

        @Override
        protected Void doInBackground(Verb... verbParams) {
            mTaskDao.deleteVerbItem(verbParams[0]);
            return null;
        }
    }
    //endregion TASKS
}
