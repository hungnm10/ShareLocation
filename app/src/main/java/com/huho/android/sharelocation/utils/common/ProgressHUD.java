
package com.huho.android.sharelocation.utils.common;

import android.app.Activity;
import android.app.ProgressDialog;

public class ProgressHUD {

    private ProgressDialog mProgressBar;

    public ProgressHUD(Activity activity) {
        mProgressBar = new ProgressDialog(activity);
        mProgressBar.setMessage("Loading...");
        mProgressBar.setProgressStyle(ProgressDialog.THEME_HOLO_LIGHT);
    }

    public void showHUD() {
        mProgressBar.show();
    }

    public void dismissHUD() {
        mProgressBar.dismiss();
    }

}
