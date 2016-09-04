
package com.huho.android.sharelocation.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.google.android.gms.maps.model.LatLng;
import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.asyntask.CreateChannelAsync;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;
import com.huho.android.sharelocation.main.gmaps.MapsActivity;
import com.huho.android.sharelocation.utils.common.ProgressHUD;
import com.huho.android.sharelocation.utils.common.Utils;
import com.huho.android.sharelocation.utils.custom.EditTextDatePicker;
import com.huho.android.sharelocation.utils.objects.Channel;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CreateChannelActivity extends BaseActivity implements IAsynTaskDelegate {

    public static final int REQUEST_CODE_SRC = 98;

    public static final int REQUEST_CODE_DES = 99;

    private ProgressHUD hud;

    private FragmentManager fManager;

    private LatLng mLatLngSrc;

    private LatLng mLatLngDes;

    @InjectView(R.id.edt_channelName)
    protected EditText mChannelName;

    @InjectView(R.id.edt_channelDes)
    protected EditText mChannelDes;

    @InjectView(R.id.edt_channelDateStart)
    protected EditTextDatePicker mChannelDateStart;

    @InjectView(R.id.edt_channelDateEnd)
    protected EditTextDatePicker mChannelDateEnd;

    @InjectView(R.id.edt_channelLocalSrc)
    protected EditText mEdtChannelSrc;

    @InjectView(R.id.btn_channelLocalSrc)
    protected Button mBtnChannelSrc;

    @InjectView(R.id.edt_channelLocalDes)
    protected EditText mEdtChannelDes;

    @InjectView(R.id.btn_channelLocalDes)
    protected Button mBtnChannelDes;

    @InjectView(R.id.edt_max)
    protected EditText mMax;

    @InjectView(R.id.btn_createchannel)
    protected Button mbtnCreateChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_channel);
        ButterKnife.inject(this);
        hud = new ProgressHUD(this);
        fManager = getSupportFragmentManager();
        Date d = new Date();
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        curFormater.setTimeZone(TimeZone.getDefault());
        mChannelDateStart.setText(curFormater.format(d));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                String add = data.getStringExtra("Address");
                double lat = data.getDoubleExtra("lat", 0);
                double lng = data.getDoubleExtra("lng", 0);
                if (requestCode == REQUEST_CODE_SRC) {
                    mLatLngSrc = new LatLng(lat, lng);
                    mEdtChannelSrc.setText(add);
                } else if (requestCode == REQUEST_CODE_DES) {
                    mLatLngDes = new LatLng(lat, lng);
                    mEdtChannelDes.setText(add);
                }
            }
        }
    }

    @OnClick({
            R.id.btn_channelLocalSrc, R.id.btn_channelLocalDes
    })
    public void setLocation(View v) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        int requestCode = v.getId() == R.id.btn_channelLocalSrc ? REQUEST_CODE_SRC
                : REQUEST_CODE_DES;
        startActivityForResult(intent, requestCode);
    }

    @OnClick(R.id.btn_createchannel)
    public void createChannel(){

        if (mChannelName.getText().toString().trim().equalsIgnoreCase("")) {
            mChannelName.setError("This file can not be blank!");
            return;
        }
        if (mChannelDes.getText().toString().equalsIgnoreCase("")) {
            mChannelDes.setError("This file can not be blank!");
            return;
        }

        if (mChannelDateStart.getText().toString().equalsIgnoreCase("")) {
            mChannelDateStart.setError("This file can not be blank!");
            return;
        }

        if (mChannelDateEnd.getText().toString().equalsIgnoreCase("")) {
            mChannelDateEnd.setError("This file can not be blank!");
            return;
        }

        if (mEdtChannelSrc.getText().toString().equalsIgnoreCase("")) {
            mEdtChannelSrc.setError("This file can not be blank!");
            return;
        }

        if (mEdtChannelDes.getText().toString().equalsIgnoreCase("")) {
            mEdtChannelDes.setError("This file can not be blank!");
            return;
        }

        if (mMax.getText().toString().equalsIgnoreCase("")) {
            mMax.setError("This file can not be blank!");
            return;
        }

        Channel channel = new Channel();
        channel.setName(mChannelName.getText().toString());
        channel.setDescription(mChannelDes.getText().toString());
        channel.setIsPrivate(false);
        channel.setLocationSrc(mLatLngSrc);
        channel.setLocationDes(mLatLngDes);
        channel.setTime(Utils.convertToUTCTime(mChannelDateStart.getText().toString()));
        channel.setMaxMember( Integer.parseInt(mMax.getText().toString()));
        channel.setmTimeEnd(mChannelDateEnd.getText().toString());

        new CreateChannelAsync(hud,fManager,this).execute( new Channel[] {channel});
    }

    @Override
    public void didSuccessWithMessage(String message) {
        finish();
    }

    @Override
    public void didFailWithMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void didSuccessWithJsonArray(JSONArray jsonArray) {

    }
}
