
package com.huho.android.sharelocation.asyntask;

import android.support.v4.app.FragmentManager;
import android.os.AsyncTask;
import android.util.Log;

import com.huho.android.sharelocation.backgroundservice.CoreService;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;
import com.huho.android.sharelocation.utils.common.Constant;
import com.huho.android.sharelocation.utils.common.SomeDialog;
import com.huho.android.sharelocation.utils.objects.Member;

import org.json.JSONException;
import org.json.JSONObject;

public class GetMemberLocationAsync extends AsyncTask<Member, Void, String> {
    private IAsynTaskDelegate interfa;

    private FragmentManager fManager;

    public GetMemberLocationAsync(FragmentManager aManger, IAsynTaskDelegate anInterface) {
        this.fManager = aManger;
        this.interfa = anInterface;
    }

    @Override
    protected String doInBackground(Member... params) {
        return CoreService.getInstance().sendMemberLocation(params[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if (isCancelled()) {
            return;
        }
        if (result != null) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (obj == null) {
                    SomeDialog dialog = new SomeDialog("Error", Constant.INTERNET_CONNECT_ERROR,
                            "OKay", "", null);
                    dialog.show(fManager, "dialog");
                } else {
                    if (obj.getInt("success") == 1) {
                        this.interfa.didSuccessWithJsonArray(obj.getJSONArray("users"));
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
            if (!dialog.isInLayout())
                dialog.show(fManager, "dialog");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
