package com.shaen.weatherclockwidget.main;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Darush on 9/2/2016.
 */
public class PrefManager1 {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared  preferences file name
    private static final String PREF_NAM = "my-intro-slid";
    private static final String IS_FIRST_TIME_LAUNC = "IsFirstTimeLaunc";

    public PrefManager1(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAM,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNC,isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch(){
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNC,true);
    }
}
