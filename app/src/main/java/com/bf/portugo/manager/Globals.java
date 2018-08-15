package com.bf.portugo.manager;

/*
 * Singleton to hold run time variables
 * @author frielb
 * Created on 15/08/2018
 */
public class Globals {

    private static Globals instance;

    private boolean mHomeCreatedOnce = false;
    private boolean mWelomeMessagePlayedOnce = false;

    public static Globals getInstance()
    {
        if (instance == null)
        {
            synchronized (Globals.class)
            {
                if (instance == null)
                {
                    instance = new Globals();
                }
            }
        }
        return instance;
    }

    public boolean getHomeCreatedOnce() {
        return mHomeCreatedOnce;
    }

    public void setHomeCreatedOnce(boolean mHomeCreatedOnce) {
        this.mHomeCreatedOnce = mHomeCreatedOnce;
    }

    public boolean getWelomeMessagePlayedOnce() {
        return mWelomeMessagePlayedOnce;
    }

    public void setWelomeMessagePlayedOnce(boolean mWelomeMessagePlayedOnce) {
        this.mWelomeMessagePlayedOnce = mWelomeMessagePlayedOnce;
    }

}
