
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

/**
 * Created by sev_user on 3/19/2016.
 */
public class JoinChannel extends AsyncTask<Integer, Void, String> {
    private IAsynTaskDelegate interfa;

    private ProgressHUD hud;

    private FragmentManager fManager;

    public JoinChannel(ProgressHUD aHud, FragmentManager aManger, IAsynTaskDelegate anInterface) {
        this.hud = aHud;
        this.fManager = aManger;
        this.interfa = anInterface;
    }

    @Override
    protected String doInBackground(Integer... params) {
        String result = CoreService.getInstance().joinChannel(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        if (hud != null)
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
        if (hud != null)
            hud.showHUD();
    }
}
