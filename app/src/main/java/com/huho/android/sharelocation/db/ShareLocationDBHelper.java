package com.huho.android.sharelocation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sev_user on 3/19/2016.
 */
public class ShareLocationDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "Tracker - " + ShareLocationDBHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "ShareLocation.db";
    private static final String TABLE_MEMBER = "tbMember";


    public ShareLocationDBHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    public static String getTableMember() {
        return TABLE_MEMBER;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        String command = String.format("create table %s (%s integer primary key, %s text,%s text,%s text, %s text, %s text, %s text, %s text);",
                TABLE_MEMBER,
                DbUtils.USER_TABLE.ID.toString(),
                DbUtils.USER_TABLE.USER_NAME.toString(),
                DbUtils.USER_TABLE.USER_EMAIL.toString(),
                DbUtils.USER_TABLE.USER_PASS.toString(),
                DbUtils.USER_TABLE.USER_IMAGE_URL.toString(),
                DbUtils.USER_TABLE.USER_IMAGE_HASH.toString(),
                DbUtils.USER_TABLE.USER_DOB.toString(),
                DbUtils.USER_TABLE.USER_LST_CH.toString());
        Log.d(TAG,"on create: " + command);
        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
