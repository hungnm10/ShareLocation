
package com.huho.android.sharelocation.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.SharedPreferencesManager;
import com.huho.android.sharelocation.asyntask.LoginAsync;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;
import com.huho.android.sharelocation.utils.common.ProgressHUD;
import com.huho.android.sharelocation.utils.common.SomeDialog;
import com.huho.android.sharelocation.utils.common.Utils;

import org.json.JSONArray;

public class SignInFragment extends FragmentActivity implements OnClickListener, IAsynTaskDelegate {

    private static final String TAG = "Tracker - SignInFragment";

    private static final int SIGN_UP_RESULT_CODE = 100;

    private Button btn_createnewacc;

    private TextView btn_fotgotpassword;

    private Button btn_signin;

    private EditText edt_email;

    private EditText edt_pass;

    private FragmentManager fManager;

    private ProgressHUD hud;

    private String mEmail;

    private String mPassWord;

    // private Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singinfragment_layout);
        fManager = this.getSupportFragmentManager();
        hud = new ProgressHUD(this);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_pass = (EditText) findViewById(R.id.edt_passwrod);
        edt_pass.setSelectAllOnFocus(true);
        btn_createnewacc = (Button) findViewById(R.id.btn_createaccount);
        btn_createnewacc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                processSignUp();
            }
        });

        btn_fotgotpassword = (TextView) findViewById(R.id.btn_forgotpassFrag);
        btn_fotgotpassword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // processForgotPass();
            }
        });

        btn_signin = (Button) findViewById(R.id.btn_singinFrag);
        btn_signin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                processSignIn();
            }
        });

    }

    private void processSignIn() {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_pass.getWindowToken(), 0);
        mEmail = edt_email.getText().toString().trim();
        mPassWord = edt_pass.getText().toString().trim();
        String[] arr = new String[2];
        arr[0] = mEmail;
        arr[1] = mPassWord;
        if (Utils.stringIsNullOrEmpty(mEmail) || Utils.stringIsNullOrEmpty(mPassWord))
            Toast.makeText(getApplicationContext(), "Required field(s) is missing", Toast.LENGTH_SHORT).show();
        else
            new LoginAsync(hud, fManager, this).execute(arr);
    }

    private void processSignUp() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivityForResult(intent, SIGN_UP_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP_RESULT_CODE && resultCode == RESULT_OK && data != null) {
            mEmail = data.getStringExtra("email");
            mPassWord = data.getStringExtra("password");
            edt_email.setText(mEmail);
            edt_pass.setText(mPassWord);
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    public void didSuccessWithMessage(String message) {
        SharedPreferencesManager.getInstance().setUserEmail(mEmail);
        SharedPreferencesManager.getInstance().setUserPassword(mPassWord);
        Intent i = new Intent(getApplicationContext(), BottomMainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
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
