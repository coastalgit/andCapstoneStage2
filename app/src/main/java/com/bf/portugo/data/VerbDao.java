package com.bf.portugo.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bf.portugo.model.Verb;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/*
 * @author frielb
 * Created on 03/08/2018
 */
@Dao
public interface VerbDao {

    @Query("SELECT * FROM Verb")
    LiveData<List<Verb>> getListVerbItems();

    @Query("SELECT * FROM Verb")
    List<Verb> getListVerbItemsSync();

    @Query("SELECT * FROM Verb WHERE classification <= :classificationCeiling")
    LiveData<List<Verb>> getListVerbItemsEssential(int classificationCeiling);

//    @Query("SELECT * FROM WordSet WHERE itemId=:itemId")
//    LiveData<List<WordSet>> getListWordSetItemsById(String itemId);

    //LiveData<List<WordSet>> getListWordSetItemsByCategory(String itemId);

    @Insert(onConflict = REPLACE)
    void insertVerbItem(Verb verb);

    @Delete
    void deleteVerbItem(Verb verb);

    @Query("DELETE FROM Verb")
    void deleteAllRecords();

}
