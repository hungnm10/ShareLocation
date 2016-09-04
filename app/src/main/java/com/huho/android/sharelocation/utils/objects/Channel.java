
package com.huho.android.sharelocation.utils.objects;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by sev_user on 3/19/2016.
 */
public class Channel {
    private int mId;

    private String mName;

    private String mDescription;

    private String mTimeStart;

    private String mTimeEnd;

    private int mAdminId;

    private LatLng mLocationSrc;

    private LatLng mLocationDes;

    private int mMaxMember;

    private boolean mIsPrivate;

    private int mCurrentUser;

    public Channel(int id, String name, String description, String timeStart, String mTimeEnd,
            int admin_id, LatLng mLocationSrc, LatLng mLocationDes, int mMaxMember,
            int mCurrentUser, boolean mIsPrivate) {
        this.mId = id;
        this.mName = name;
        this.mDescription = description;
        this.mTimeStart = timeStart;
        this.mTimeEnd = mTimeEnd;
        this.mAdminId = admin_id;
        this.mLocationSrc = mLocationSrc;
        this.mLocationDes = mLocationDes;
        this.mMaxMember = mMaxMember;
        this.mIsPrivate = mIsPrivate;
        this.mCurrentUser = mCurrentUser;
    }

    public Channel() {

    }

    public String getmTimeEnd() {
        return mTimeEnd;
    }

    public void setmTimeEnd(String mTimeEnd) {
        this.mTimeEnd = mTimeEnd;
    }

    public int getmCurrentUser() {
        return mCurrentUser;
    }

    public void setmCurrentUser(int mCurrentUser) {
        this.mCurrentUser = mCurrentUser;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getTime() {
        return mTimeStart;
    }

    public void setTime(String time) {
        this.mTimeStart = time;
    }

    public int getAdminId() {
        return mAdminId;
    }

    public void setAdminId(int mAdminId) {
        this.mAdminId = mAdminId;
    }

    public LatLng getLocationSrc() {
        return mLocationSrc;
    }

    public void setLocationSrc(LatLng mLocationSrc) {
        this.mLocationSrc = mLocationSrc;
    }

    public LatLng getLocationDes() {
        return mLocationDes;
    }

    public void setLocationDes(LatLng mLocationDes) {
        this.mLocationDes = mLocationDes;
    }

    public int getMaxMember() {
        return mMaxMember;
    }

    public void setMaxMember(int mMaxMember) {
        this.mMaxMember = mMaxMember;
    }

    public boolean getIsPrivate() {
        return mIsPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.mIsPrivate = isPrivate;
    }
}
