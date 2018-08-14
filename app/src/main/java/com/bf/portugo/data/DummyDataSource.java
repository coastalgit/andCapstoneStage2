package com.bf.portugo.data;

import com.bf.portugo.model.Verb;

import java.util.ArrayList;
import java.util.List;

/*
 * @author frielb
 * Created on 01/08/2018
 */
public class DummyDataSource implements IVerbDataSource {

    @Override
    public void fetchVerbItems(VerbDataSourceListener listener) {
        if (listener != null){
            VerbStockData verbData = new VerbStockData(VerbStockData.VerbType.ANY);
            List<Verb> verbs = new ArrayList<>(verbData.VERB_MAP.values());
            listener.onSuccess(verbs);
        }
    }

    @Override
    public void fetchVerbItemsByType(VerbStockData.VerbType verbType, VerbDataSourceListener listener) {
        if (listener != null){
            VerbStockData verbData = new VerbStockData(verbType);
            List<Verb> verbs = new ArrayList<>(verbData.VERB_MAP.values());
            listener.onSuccess(verbs);
        }
    }

}
