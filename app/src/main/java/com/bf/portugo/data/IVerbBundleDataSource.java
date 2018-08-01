package com.bf.portugo.data;

import com.bf.portugo.model.Verb;

import java.util.List;

/*
 * @author frielb
 * Created on 31/07/2018
 */

public interface IVerbBundleDataSource {
    void fetchVerbItemsByType(VerbStockData.VerbType verbType, verbDataSourceListener listener);
    void fetchVerbItemsByCategory(String category, verbDataSourceListener listener);

    public interface verbDataSourceListener{
        void onSuccess(List<Verb> verbs);
        void onError(int errorCode, String errorMsg);
    }

}
