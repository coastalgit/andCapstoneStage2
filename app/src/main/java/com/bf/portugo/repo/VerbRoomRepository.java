package com.bf.portugo.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.bf.portugo.common.Constants;
import com.bf.portugo.data.FirebaseDataSource;
import com.bf.portugo.data.IVerbDataSource;
import com.bf.portugo.data.VerbDao;
import com.bf.portugo.data.VerbDatabase;
import com.bf.portugo.model.Verb;

import java.util.ArrayList;
import java.util.List;

import static com.bf.portugo.common.Constants.VERB_CLASSIFICATIONCEILING_ESSENTIAL;
import static com.bf.portugo.common.Enums.*;

/*
 * @author frielb
 * Created on 03/08/2018
 */
@SuppressWarnings({"Convert2MethodRef", "SpellCheckingInspection"})
public class VerbRoomRepository {
    private static final String TAG = VerbRoomRepository.class.getSimpleName();

/*
    public static enum VerbFilter{
        ALL,
        ESSENTIAL
    }
*/

    interface IAsyncTaskComplete{
        void doRefresh();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final VerbDatabase mDb_Verb;
    private final VerbDao mDao_Verb;
    private List<Verb> mObservableVerbsSync;
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
                    if (verbs != null) {
                        Log.d(TAG, "onSuccess: Verb count=" + String.valueOf(getRecordCount(mObservableVerbs)) + " DS COUNT=" + String.valueOf(verbs.size()));
                        //refresh local db
                        new deleteAllTask(mDao_Verb, () -> refreshLiveDataSets()).execute();
                        //new insertBatchTask(mDao_Verb, () -> refreshLiveDataSets()).execute(verbs);
                        new insertBatchTask(mDao_Verb, verbs, () -> refreshLiveDataSets()).execute();
                    }
                }

                @Override
                public void onError(ErrorCode errorCode, String errorMsg) {
                    // TODO: 03/08/2018
                }
            });
        }
    }

    public void deleteAllRoomDbRecs(){
        int recCount = getRecordCount(mObservableVerbs);
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

    public void unsubscribeFromChildUpdates(FirebaseDataSource dataSource){
        if (dataSource != null)
            dataSource.removeChildListener();
    }

    private void refreshLiveDataSets(){
        int countAll = getRecordCount(getVerbs());
        Log.d(TAG, "refreshLiveDataSets: Count (All)="+String.valueOf(countAll));
        int countEss = getRecordCount(getVerbsEssential());
        Log.d(TAG, "refreshLiveDataSets: Count (Essential)="+String.valueOf(countEss));
    }

    public List<Verb> getVerbsSync() {
        populateVerbsFromDB_All_Sync();
        return mObservableVerbsSync;
    }

    public LiveData<List<Verb>> getVerbs() {
        populateVerbsFromDB_All();
        return mObservableVerbs;
    }

    public LiveData<List<Verb>> getVerbsEssential() {
        populateVerbsFromDB_Essential();
        return mObservableVerbsEssential;
    }

    private void populateVerbsFromDB_All_Sync(){
        if (mDao_Verb != null){
            if (mObservableVerbsSync == null)
                mObservableVerbsSync = new ArrayList<>();

            mObservableVerbsSync = mDao_Verb.getListVerbItemsSync();
        }

    }

    private void populateVerbsFromDB_All(){
        if (mDao_Verb != null){
            if (mObservableVerbs == null)
                mObservableVerbs = new MutableLiveData<>();

            mObservableVerbs = mDao_Verb.getListVerbItems();

            //if ((mObservableVerbs != null) && (mObservableVerbs.getValue() != null))
//            if (mObservableVerbs.getValue() != null)
//                Log.d(TAG, "populateVerbsFromDB_All: Room Verb count="+String.valueOf(mObservableVerbs.getValue().size()));
//            else
//                Log.d(TAG, "populateVerbsFromDB_All: Room Verb count=NULL");
        }

    }

    private void populateVerbsFromDB_Essential(){
        if (mDao_Verb != null){
            if (mObservableVerbsEssential == null)
                mObservableVerbsEssential = new MutableLiveData<>();

            mObservableVerbsEssential = mDao_Verb.getListVerbItemsEssential(VERB_CLASSIFICATIONCEILING_ESSENTIAL);

            //if ((mObservableVerbsEssential != null) && (mObservableVerbsEssential.getValue() != null))
//            if (mObservableVerbsEssential.getValue() != null)
//                Log.d(TAG, "populateVerbsFromDB_Essential: Room Verb count="+String.valueOf(mObservableVerbsEssential.getValue().size()));
//            else
//                Log.d(TAG, "populateVerbsFromDB_Essential: Room Verb count=NULL");
        }

    }

/*

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
*/


    //region TASKS (STATIC INNER CLASSES)

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
            //refreshLiveDataSets();
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
    //private static class insertBatchTask extends AsyncTask<List<Verb>, Void, Void> {
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
            //refreshLiveDataSets();        }
            if (mListener != null)
                mListener.doRefresh();
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(Void... voids) {
            mCounter = 0;
/*
            List<Verb> vList = (List<Verb>) lists[0];
            for (Verb v : vList) {
                mTaskDao.insertVerbItem(v);
                mCounter++;
            }
*/
            if (mVerbList != null){
                for (Verb v : mVerbList) {
                    mTaskDao.insertVerbItem(v);
                    mCounter++;
                }
            }

            return null;
        }
    }

/*
    //Unchecked generics array warning
    //private static class insertBatchTask extends AsyncTask<List<Verb>, Void, Void> {
    private static class insertBatchTask extends AsyncTask<Object, Void, Void> {

        private final VerbDao mTaskDao;
        private int mCounter;
        private final IAsyncTaskComplete mListener;

        insertBatchTask(VerbDao dao, IAsyncTaskComplete listener) {
            mTaskDao = dao;
            mListener = listener;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: Inserted count=" + String.valueOf(mCounter));
            //refreshLiveDataSets();        }
            if (mListener != null)
                mListener.doRefresh();
        }

        @SafeVarargs
        @Override
        //protected final Void doInBackground(List<Verb>... lists) {
        protected final Void doInBackground(Object... lists) {
            mCounter = 0;
*/
/*
            List<Verb> vList = (List<Verb>) lists[0];
            for (Verb v : vList) {
                mTaskDao.insertVerbItem(v);
                mCounter++;
            }
*//*


            for (Object obj : lists){
                if (obj instanceof List){
                    List<Verb> vList = (List<Verb>) obj;
                    for (Verb v : vList) {
                        mTaskDao.insertVerbItem(v);
                        mCounter++;
                    }
                }
            }

            return null;
        }
    }
*/

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
