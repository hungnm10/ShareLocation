package com.huho.android.sharelocation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.huho.android.sharelocation.utils.objects.Member;

import org.json.JSONArray;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by sev_user on 3/19/2016.
 */
public class ShareLocationDBControll implements Closeable {

    private static ShareLocationDBHelper mHelper;
    private Context mContext = null;

    public ShareLocationDBControll(Context context) {
        mContext = context;
        mHelper = new ShareLocationDBHelper(context);
    }

    public static void insert(String tableName,ContentValues values) {
        SQLiteDatabase mDb = mHelper.getWritableDatabase();
        try {
            long t = mDb.insertOrThrow(tableName, null, values);
        }
        catch (SQLException e){
            Log.d("hung.nq1",e.toString());
        }
    }

    public static void update(String tableName,ContentValues values, String whereClause) {
        SQLiteDatabase mDb = mHelper.getWritableDatabase();
        try {
            mDb.update(tableName, values, whereClause, null);
        }catch (SQLException e){
            Log.d("hung.nq1",e.toString());
        }

    }

    private static void delete(String tableName,String whereClause) {
        SQLiteDatabase mDb = mHelper.getWritableDatabase();
        mDb.delete(tableName, whereClause, null);
    }

    public static Cursor getCursorById(String tableName,int id) {
        SQLiteDatabase mDb = mHelper.getReadableDatabase();
        String orderBy = "_id ASC";
        String selectionargs = "_id = " + id;
        Cursor cursor = mDb.query(tableName, null, selectionargs,
                null, null, null, orderBy);
        return cursor;
    }

    public static Cursor getCursorByEmail(String tableName,String email) {
        SQLiteDatabase mDb = mHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery("select * from "+tableName+" where "+ DbUtils.USER_TABLE.USER_EMAIL.toString() + " = ?",new String[]{ email });
        return cursor;
    }

    public static void insertOrUpdateMember(Member m){
        ContentValues values = new ContentValues();
        String tbName = ShareLocationDBHelper.getTableMember();
        m.setContentValues(values);
        Member t = null;
        try {
            t = getMemberFromDB(m.getmEmail());
            if (t != null) {
                update(tbName, values, DbUtils.USER_TABLE.ID.toString() + "=" + t.getmId());
            } else {
                insert(tbName,values);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            Log.d("hung.nq1",e.toString());
        }
    }

    public static void deleteMember(Member m){
        delete(ShareLocationDBHelper.getTableMember(), DbUtils.USER_TABLE.ID.toString() + "=" + m.getmId());
    }

    public static Member getMemberFromDB(int id){
        String tbName = ShareLocationDBHelper.getTableMember();
        Cursor cursor = null;
        Member m = new Member();
        try {
            cursor = getCursorById(tbName,id);
            if (cursor != null && cursor.moveToFirst()) {
                m.setmId(cursor.getInt(DbUtils.USER_TABLE.ID.ordinal()));
                m.setmName(cursor.getString(DbUtils.USER_TABLE.USER_NAME.ordinal()));
                m.setmEmail(cursor.getString(DbUtils.USER_TABLE.USER_EMAIL.ordinal()));
                m.setmPassword(cursor.getString(DbUtils.USER_TABLE.USER_PASS.ordinal()));
                m.setmUrlImage(cursor.getString(DbUtils.USER_TABLE.USER_IMAGE_URL.ordinal()));
                m.setmHashImg(cursor.getString(DbUtils.USER_TABLE.USER_IMAGE_HASH.ordinal()));
                m.setmDOB(cursor.getString(DbUtils.USER_TABLE.USER_DOB.ordinal()));
                String lstCh = cursor.getString(DbUtils.USER_TABLE.USER_LST_CH.ordinal());
                m.setmListChannel(new JSONArray(lstCh));
            } else {
                return null;
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            DbUtils.closeSilently(cursor);
        }
        return m;
    }

    public static Member getMemberFromDB(String email){
        String tbName = ShareLocationDBHelper.getTableMember();
        Cursor cursor = null;
        Member m = new Member();
        try {
            cursor = getCursorByEmail(tbName,email);
            if (cursor != null && cursor.moveToFirst()) {
                m.setmId(cursor.getInt(DbUtils.USER_TABLE.ID.ordinal()));
                m.setmName(cursor.getString(DbUtils.USER_TABLE.USER_NAME.ordinal()));
                m.setmEmail(cursor.getString(DbUtils.USER_TABLE.USER_EMAIL.ordinal()));
                m.setmPassword(cursor.getString(DbUtils.USER_TABLE.USER_PASS.ordinal()));
                m.setmUrlImage(cursor.getString(DbUtils.USER_TABLE.USER_IMAGE_URL.ordinal()));
                m.setmHashImg(cursor.getString(DbUtils.USER_TABLE.USER_IMAGE_HASH.ordinal()));
                m.setmDOB(cursor.getString(DbUtils.USER_TABLE.USER_DOB.ordinal()));
                String lstCh = cursor.getString(DbUtils.USER_TABLE.USER_LST_CH.ordinal());
                m.setmListChannel(new JSONArray(lstCh));
            }
            else
                return null;

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            DbUtils.closeSilently(cursor);
        }
        return m;
    }

    @Override
    public void close() throws IOException {
        if (mHelper != null) {
            mHelper.close();
        }
    }
}
