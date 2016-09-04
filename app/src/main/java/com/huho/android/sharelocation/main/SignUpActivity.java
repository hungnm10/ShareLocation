
package com.huho.android.sharelocation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.asyntask.RegisterAsync;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;
import com.huho.android.sharelocation.utils.common.ProgressHUD;
import com.huho.android.sharelocation.utils.common.SomeDialog;
import com.huho.android.sharelocation.utils.common.Utils;

import org.json.JSONArray;

/**
 * Created by sev_user on 5/11/2016.
 */
public class SignUpActivity extends FragmentActivity implements IAsynTaskDelegate {

    private Button mBtnRegister;

    private TextView mTvName;

    private TextView mTvEmail;

    private TextView mTvPassword;

    private String mName;

    private String mEmail;

    private String mPassword;

    private FragmentManager fManager;

    private ProgressHUD hud;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        fManager = this.getSupportFragmentManager();
        hud = new ProgressHUD(this);
        mTvName = (TextView) findViewById(R.id.tv_rg_name);
        mTvEmail = (TextView) findViewById(R.id.tv_rg_email);
        mTvPassword = (TextView) findViewById(R.id.tv_rg_passwrod);
        mBtnRegister = (Button) findViewById(R.id.btn_createnewaccount);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }

    private void Register() {
        mEmail = mTvEmail.getText().toString();
        mName = mTvName.getText().toString();
        mPassword = mTvPassword.getText().toString();
        if (Utils.stringIsNullOrEmpty(mEmail) || Utils.stringIsNullOrEmpty(mName)
                || Utils.stringIsNullOrEmpty(mPassword))
            Toast.makeText(getApplicationContext(), "Required field(s) is missing",
                    Toast.LENGTH_SHORT).show();
        else
            new RegisterAsync(hud, fManager, this).execute(new String[] {
                    mEmail, mPassword, mName
            });
    }

    @Override
    public void didSuccessWithMessage(String message) {
        Intent result = new Intent();
        result.putExtra("email", mEmail);
        result.putExtra("password", mPassword);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void didFailWithMessage(String message) {
        SomeDialog dialog = new SomeDialog("Error", message, "OKay", "", null);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void didSuccessWithJsonArray(JSONArray jsonArray) {

    }
}
