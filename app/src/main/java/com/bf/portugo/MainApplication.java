package com.bf.portugo;

import android.app.Application;
import android.content.Context;

import com.bf.portugo.manager.Globals;


/*
 * @author frielb
 * Created on 12/08/2018
 */
public class MainApplication extends Application {

    private static Context mContext;

    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        Globals.getInstance().setHomeCreatedOnce(false);

    }

    public static Context getContext() {
        return mContext;
    }

}