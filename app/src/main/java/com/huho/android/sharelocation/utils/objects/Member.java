package com.huho.android.sharelocation.utils.objects;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.huho.android.sharelocation.R;
import com.huho.android.sharelocation.ShareLocationApplication;
import com.huho.android.sharelocation.db.DbUtils;
import com.huho.android.sharelocation.utils.common.Utils;

import org.json.JSONArray;

public class Member {

    private int mId;
    private String mEmail;
    private String mPassword;
    private String mName;
    private LatLng mLatLng;
    private String mChannelName;
    private JSONArray mListChannel;
    private String mUrlImage;
    private String mHashImg;
    private String mDOB;
    private Marker mMarker;

    public Member() {
    }

    public Member(String channelName, String name, LatLng latLng){
        mChannelName = channelName;
        mName = name;
        mLatLng = latLng;
    }

    public Member(int mId, String mEmail, String mPassword, String mName, LatLng mLatLng, String mChannelName, JSONArray mListChannel, String mUrlImage, String mHashImg, String mDOB) {
        this.mId = mId;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mName = mName;
        this.mLatLng = mLatLng;
        this.mChannelName = mChannelName;
        this.mListChannel = mListChannel;
        this.mUrlImage = mUrlImage;
        this.mHashImg = mHashImg;
        this.mDOB = mDOB;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public LatLng getmLatLng() {
        return mLatLng;
    }

    public void setmLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    public String getmChannelName() {
        return mChannelName;
    }

    public void setmChannelName(String mChannelName) {
        this.mChannelName = mChannelName;
    }

    public JSONArray getmListChannel() {
        return mListChannel;
    }

    public void setmListChannel(JSONArray mListChannel) {
        this.mListChannel = mListChannel;
    }

    public String getmUrlImage() {
        return mUrlImage;
    }

    public void setmUrlImage(String mUrlImage) {
        this.mUrlImage = mUrlImage;
    }

    public String getmHashImg() {
        return mHashImg;
    }

    public void setmHashImg(String mHashImg) {
        this.mHashImg = mHashImg;
    }

    public String getmDOB() {
        return mDOB;
    }

    public void setmDOB(String mDOB) {
        this.mDOB = mDOB;
    }

    public Bitmap getBitmap(){
        if(Utils.stringIsNullOrEmpty(mUrlImage))
            return BitmapFactory.decodeResource(ShareLocationApplication.getInstance().getResources(), R.drawable.ic_marker);
        return BitmapFactory.decodeFile(mUrlImage);
    }

    public Marker getMarker(Context context,GoogleMap mGoogleMap){
        if(mMarker != null)
            mMarker.remove();
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        ImageView img = (ImageView) marker.findViewById(R.id.marker_img);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        img.setImageBitmap(getBitmap());
        numTxt.setText(mName);
        mMarker = mGoogleMap.addMarker(new MarkerOptions().position(mLatLng)
                .icon(BitmapDescriptorFactory.fromBitmap(Utils.createDrawableFromView(context, marker))));
        return mMarker;
    }

    public void setContentValues(ContentValues values){
        values.put(DbUtils.USER_TABLE.USER_NAME.toString(),mName);
        values.put(DbUtils.USER_TABLE.USER_EMAIL.toString(),mEmail);
        values.put(DbUtils.USER_TABLE.USER_PASS.toString(),mPassword);
        values.put(DbUtils.USER_TABLE.USER_IMAGE_URL.toString(),mUrlImage);
        values.put(DbUtils.USER_TABLE.USER_IMAGE_HASH.toString(),mHashImg);
        values.put(DbUtils.USER_TABLE.USER_DOB.toString(), mDOB);
        values.put(DbUtils.USER_TABLE.USER_LST_CH.toString(),mListChannel == null ? "" : mListChannel.toString());
    }

}



