package com.bf.portugo.data;

import com.bf.portugo.common.Enums;
import com.bf.portugo.model.Verb;

import java.util.List;

/*
 * @author frielb
 * Created on 31/07/2018
 */

public interface IVerbDataSource {
    void fetchVerbItems(verbDataSourceListener listener);
    void fetchVerbItemsByType(VerbStockData.VerbType verbType, verbDataSourceListener listener);
    //void fetchVerbItemsByCategory(String category, verbDataSourceListener listener);

    public interface verbDataSourceListener{
        void onSuccess(List<Verb> verbs);
        void onError(Enums.ErrorCode errorCode, String errorMsg);
    }

}
