package com.bf.portugo.data;

import android.util.Log;

import com.bf.portugo.model.Verb;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*
 * @author frielb
 * Created on 01/08/2018
 */
public class FirebaseDataSource implements IVerbDataSource {

    private String TAG = FirebaseDataSource.class.getSimpleName();
    public static final String NODE_VERBS = "verbs_pt";
    private static final String FIELD_ISIRREGULAR = "isIrregular";

    public interface IVerbChildEvent {
        void onVerbAdded(Verb verb);
        void onVerbChanged(Verb verb);
        void onVerbDeleted(Verb verb);
    }

    private FirebaseDatabase mFDb;
    private DatabaseReference mFDbRef_Verbs;
    private ValueEventListener mFDbValueEventListener;
    private ChildEventListener mFDbChildEventListener;
    private VerbDataSourceListener mVerbDataSourceListener;
    private IVerbChildEvent mVerbChildEventListener;

    public FirebaseDataSource() {
        this.mFDb = FirebaseDatabase.getInstance();
        this.mFDbRef_Verbs = mFDb.getReference().child(NODE_VERBS);

        mFDbValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Verb> verbs = new ArrayList<>();
                for (DataSnapshot vSnapshot : dataSnapshot.getChildren()) {
                    Verb v = vSnapshot.getValue(Verb.class);
                    Log.d(TAG, "onDataChange: v=[" + v.getWord_en() + "]");
                    verbs.add(v);
                }
                if (mVerbDataSourceListener != null) {
                    mVerbDataSourceListener.onSuccess(verbs);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: Read failed");
            }
        };

    }

    //region Firebase testing
    /**
     * For Firebase testing only. Used from a developer dedicated admin view to insert data to Firebase.
     * @param verbs
     */
    public void addStockData_List(List<Verb> verbs){
        for (Verb v : verbs) {
            String id = mFDbRef_Verbs.push().getKey();
            Log.d(TAG, "addStockData (LIST): ID=["+id+"]");
            mFDbRef_Verbs.child(id).setValue(v);
        }
    }

    public void addStockData_Item(Verb verb){
        String id = mFDbRef_Verbs.push().getKey();
        Log.d(TAG, "addStockData (ITEM): ID=["+id+"]");
        mFDbRef_Verbs.child(id).setValue(verb);
    }

    public void deleteAllFbRecs(){
        mFDbRef_Verbs.removeValue();
    }

    //endregion Firebase testing

    public void attachChildListener(IVerbChildEvent childEventListener){
        Log.d(TAG, "attachFbDbChildListener: ");
        removeChildListener(); // remove any existing
        mVerbChildEventListener = childEventListener;
        //if (mFDbChildEventListener == null) {
            mFDbChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    Verb v = dataSnapshot.getValue(Verb.class);
                    Log.d(TAG, "onChildAdded: child:["+v.getWord_en()+"]");
                    if (mVerbChildEventListener != null){
                        mVerbChildEventListener.onVerbAdded(v);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Verb v = dataSnapshot.getValue(Verb.class);
                    Log.d(TAG, "onChildChanged: child:["+v.getWord_en()+"]");
                    if (mVerbChildEventListener != null){
                        mVerbChildEventListener.onVerbChanged(v);
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Verb v = dataSnapshot.getValue(Verb.class);
                    Log.d(TAG, "onChildRemoved: child:["+v.getWord_en()+"]");
                    if (mVerbChildEventListener != null){
                        mVerbChildEventListener.onVerbDeleted(v);
                    }
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    Log.d(TAG, "onChildMoved: s:["+s+"]");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(TAG, "onCancelled: ");
                }
            };
        //}
        mFDbRef_Verbs.addChildEventListener(mFDbChildEventListener);
    }

    public void removeChildListener() {
        Log.d(TAG, "removeChildListener: ");
        if (mFDbChildEventListener != null)
            mFDbRef_Verbs.removeEventListener(mFDbChildEventListener);
        mFDbChildEventListener = null;
    }

    @Override
    public void fetchVerbItems(VerbDataSourceListener listener) {
        this.mVerbDataSourceListener = listener;
        mFDbRef_Verbs.addListenerForSingleValueEvent(mFDbValueEventListener);
    }

    @Override
    public void fetchVerbItemsByType(VerbStockData.VerbType verbType, VerbDataSourceListener listener) {
        if (verbType == VerbStockData.VerbType.ANY){
            fetchVerbItems(listener);
            return;
        }

        this.mVerbDataSourceListener = listener;
        Query query = mFDbRef_Verbs
                .orderByChild(FIELD_ISIRREGULAR)
                .equalTo(verbType == VerbStockData.VerbType.IRREGULAR);

        query.addListenerForSingleValueEvent(mFDbValueEventListener);
    }

}
