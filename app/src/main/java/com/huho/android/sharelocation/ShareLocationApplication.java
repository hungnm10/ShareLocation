package com.huho.android.sharelocation;

import android.app.Application;

/**
 * Created by sev_user on 3/4/2016.
 */
public class ShareLocationApplication extends Application {
    private static ShareLocationApplication mInstance = null;
    public static ShareLocationApplication getInstance() {
        if (mInstance == null) {
            mInstance = new ShareLocationApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
