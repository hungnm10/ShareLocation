package com.huho.android.sharelocation;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sev_user on 3/4/2016.
 */
public class SharedPreferencesManager {
    public static final String PREFERENCE_NAME = "com.huho.android.sharelocation";
    public static final String USER_EMAIL = "uEmail";
    public static final String USER_PASSWORD = "uPassword";
    public static final String ZOOM_LEVEL = "map_zoom_level";

    private static SharedPreferencesManager mInstance = null;
    private SharedPreferences mPreferences;
    private Context mContext;

    private SharedPreferencesManager() {
        mContext = ShareLocationApplication.getInstance();
        mPreferences = mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getInstance() {
        if (mInstance == null) {
            mInstance = new SharedPreferencesManager();
        }
        return mInstance;
    }

    public String getUserEmail(){
        return mPreferences.getString(USER_EMAIL,"");
    }

    public String getUserPassword(){
        return mPreferences.getString(USER_PASSWORD,"");
    }

    public void setUserEmail(String email){
        SharedPreferences.Editor ed = mPreferences.edit();
        ed.putString(USER_EMAIL, email);
        ed.commit();
    }

    public void setUserPassword(String password){
        SharedPreferences.Editor ed = mPreferences.edit();
        ed.putString(USER_PASSWORD, password);
        ed.commit();
    }

    public void setZoomLevel(int level){
        SharedPreferences.Editor ed = mPreferences.edit();
        ed.putInt(ZOOM_LEVEL, level);
        ed.commit();
    }

    public int getZoomLevel() {return mPreferences.getInt(ZOOM_LEVEL,15);};

}
