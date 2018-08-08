package com.bf.portugo.datacreation;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bf.portugo.data.FirebaseDataSource;
import com.bf.portugo.data.VerbStockData;
import com.bf.portugo.model.Verb;

import java.util.ArrayList;
import java.util.List;

/*
 * @author frielb
 * Created on 07/08/2018
 */
public class DBFuncsViewModel extends AndroidViewModel {

    private String TAG = DBFuncsViewModel.class.getSimpleName();

    private FirebaseDataSource mFbWriteFuncs;
    private List<Verb> mList_Reg;
    private List<Verb> mList_Irreg;
    private int mSingleRecIndex;

    public DBFuncsViewModel(@NonNull Application application) {
        super(application);

        mFbWriteFuncs = new FirebaseDataSource();

        VerbStockData stockData1 = new VerbStockData(VerbStockData.VerbType.REGULAR);
        mList_Reg = new ArrayList<>(stockData1.VERB_MAP.values());
        Log.d(TAG, "DbFuncsViewModel: REG="+String.valueOf(mList_Reg.size())+" recs");

        VerbStockData stockData2 = new VerbStockData(VerbStockData.VerbType.IRREGULAR);
        mList_Irreg = new ArrayList<>(stockData2.VERB_MAP.values());
        Log.d(TAG, "DbFuncsViewModel: IRREG="+String.valueOf(mList_Irreg.size())+" recs");

        mSingleRecIndex = 0;

    }

    public void populateFbWithStockData_All(){
        mFbWriteFuncs.addStockData_List(mList_Reg);
    }

    public void populateFbWithStockData_NextItem(){
        Log.d(TAG, "populateFbWithStockData_NextItem: INDEX="+String.valueOf(mSingleRecIndex));
        if (mSingleRecIndex < mList_Irreg.size()) {
            Verb v = mList_Irreg.get(mSingleRecIndex);
            mFbWriteFuncs.addStockData_Item(v);
            mSingleRecIndex++;
        }
    }

}
