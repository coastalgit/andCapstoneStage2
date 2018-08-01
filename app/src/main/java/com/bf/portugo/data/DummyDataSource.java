package com.bf.portugo.data;

import com.bf.portugo.model.Verb;

import java.util.ArrayList;
import java.util.List;

/*
 * @author frielb
 * Created on 01/08/2018
 */
public class DummyDataSource implements IVerbBundleDataSource {

    @Override
    public void fetchVerbItemsByType(VerbStockData.VerbType verbType, verbDataSourceListener listener) {
        if (listener != null){
            VerbStockData verbData = new VerbStockData(verbType);
            List<Verb> verbs = new ArrayList<>(verbData.VERB_MAP.values());
            listener.onSuccess(verbs);
        }
    }

    @Override
    public void fetchVerbItemsByCategory(String category, verbDataSourceListener listener) {
        VerbStockData verbData = new VerbStockData(VerbStockData.VerbType.ANY);
        List<Verb> verbs = new ArrayList<>(verbData.VERB_MAP.values());
        listener.onSuccess(verbs);
    }
}
