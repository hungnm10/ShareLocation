package com.huho.android.sharelocation.db;

import android.util.Log;

import java.io.Closeable;

/**
 * Created by sev_user on 3/19/2016.
 */
public class DbUtils {
    private static final String TAG = "Tracker - " + DbUtils.class.getSimpleName();

    public static final void closeSilently(Closeable c) {
        if (c == null)
            return;
        try {
            c.close();
        } catch (Throwable t) {
            Log.d(TAG, t.getMessage());
        }
    }

    public static final void closeSilently(Closeable... c) {
        if (c == null)
            return;

        for (int i = 0; i < c.length; ++i) {
            closeSilently(c[i]);
        }
    }
    public enum USER_TABLE{
        ID("_id"),
        USER_NAME("name"),
        USER_EMAIL("email"),
        USER_PASS("password"),
        USER_IMAGE_URL("image_url"),
        USER_IMAGE_HASH("image_hash"),
        USER_DOB("u_dob"),
        USER_LST_CH("lst_ch");

        private final String text;

        private USER_TABLE(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }
}
