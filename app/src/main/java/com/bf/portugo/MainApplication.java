package com.bf.portugo;

import android.app.Application;

import com.bf.portugo.manager.Globals;


/*
 * @author frielb
 * Created on 12/08/2018
 */
@SuppressWarnings("WeakerAccess")
public class MainApplication extends Application {

    public void onCreate() {
        super.onCreate();

        Globals.getInstance().setHomeCreatedOnce(false);

    }

}