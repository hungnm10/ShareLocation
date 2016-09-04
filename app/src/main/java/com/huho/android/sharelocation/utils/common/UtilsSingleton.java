package com.huho.android.sharelocation.utils.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huho.android.sharelocation.utils.objects.Member;

/**
 */
public class UtilsSingleton {

    private static UtilsSingleton instance;
    private Member mCurrentMember;
    private String mUserEmail;
    private String mUserPw;

    public static UtilsSingleton getIntance()
    {
        if(instance == null)
        {
            instance = new UtilsSingleton();
        }
        return instance;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String userEmail) {
        this.mUserEmail = userEmail;
    }

    public String getUserPw() {
        return mUserPw;
    }

    public void setUserPw(String userPw) {
        this.mUserPw = userPw;
    }

    public Member getCurrentMember() {
        return mCurrentMember;
    }

    public void setCurrentMember(Member currentMember) {
        this.mCurrentMember = currentMember;
    }

    public boolean stringIsNullOrEmpty(String s){
        return s == null || s.isEmpty() || s.trim().length() <= 0;
    }
    public  Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
