
package com.huho.android.sharelocation.asyntask;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;

import com.huho.android.sharelocation.backgroundservice.CoreService;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;
import com.huho.android.sharelocation.utils.common.Constant;
import com.huho.android.sharelocation.utils.common.ProgressHUD;
import com.huho.android.sharelocation.utils.common.SomeDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginAsync extends AsyncTask<String, Void, String> {
    private IAsynTaskDelegate interfa;

    private ProgressHUD hud;

    private FragmentManager fManager;

    public LoginAsync(ProgressHUD aHud, FragmentManager aManger, IAsynTaskDelegate anInterface) {
        this.hud = aHud;
        this.fManager = aManger;
        this.interfa = anInterface;
    }

    @Override
    protected String doInBackground(String... params) {
        String email = params[0];
        String pass = params[1];
        String result = CoreService.getInstance().sendLogin(email, pass);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        if (null != hud)
            hud.dismissHUD();
        if (isCancelled()) {
            return;
        }
        if (result != null) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(result);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (obj == null) {
                    SomeDialog dialog = new SomeDialog("Error", Constant.INTERNET_CONNECT_ERROR,
                            "OKay", "", null);
                    dialog.show(fManager, "dialog");
                } else {
                    if (obj.getInt("success") == 1) {
                        this.interfa.didSuccessWithMessage(obj.getString("message"));
                    } else {
                        this.interfa.didFailWithMessage(obj.getString("message"));
                    }
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            SomeDialog dialog = new SomeDialog("Login", Constant.INTERNET_CONNECT_ERROR, "OK", "",
                    null);
            dialog.show(fManager, "dialog");
        }
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        if (null != hud)
            hud.showHUD();
    }
}
