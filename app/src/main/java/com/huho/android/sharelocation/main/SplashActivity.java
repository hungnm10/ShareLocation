
package com.huho.android.sharelocation.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.SharedPreferencesManager;
import com.huho.android.sharelocation.asyntask.LoginAsync;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;

import org.json.JSONArray;

/**
 * Show Logo and flash screen before to SignIn screen
 */

public class SplashActivity extends FragmentActivity implements IAsynTaskDelegate {
    private static int SPLASH_TIME_OUT = 2000;

    private FragmentManager fManager;

    private String mUserName;

    private String mPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        fManager = this.getSupportFragmentManager();
        mUserName = SharedPreferencesManager.getInstance().getUserEmail();
        mPassWord = SharedPreferencesManager.getInstance().getUserPassword();
        if (mUserName != "" && mPassWord != "")
            new LoginAsync(null, fManager, this).execute(new String[] {
                    mUserName, mPassWord
            });
        else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(SplashActivity.this, SignInFragment.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }, SPLASH_TIME_OUT);
        }
    }

    @Override
    public void didSuccessWithMessage(String message) {
        Intent i = new Intent(SplashActivity.this, BottomMainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
    }

    @Override
    public void didFailWithMessage(String message) {
        Intent i = new Intent(SplashActivity.this, SignInFragment.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void didSuccessWithJsonArray(JSONArray jsonArray) {

    }
}
