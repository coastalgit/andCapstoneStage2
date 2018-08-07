package com.bf.portugo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bf.portugo.common.Constants;
import com.bf.portugo.model.Verb;

/*
 * AAC Room DB access
 * @author frielb
 * Created on 03/08/2018
 */
@Database(entities = {Verb.class}, version = 1, exportSchema = false)
public abstract class VerbDatabase extends RoomDatabase {

        public abstract VerbDao verbDao(); // our abstract getter method

        private static VerbDatabase mInstance;

        public static VerbDatabase getDatabaseInstance(Context context){
            if (mInstance == null){
                synchronized (Verb.class){
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            VerbDatabase.class,
                            Constants.DBNAME_VERBBUNDLE_PT)
                            .build();
                }
            }
            return mInstance;
        }

}
