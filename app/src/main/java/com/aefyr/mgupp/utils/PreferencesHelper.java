package com.aefyr.mgupp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Aefyr on 11.10.2018.
 */
public class PreferencesHelper {
    private static class Key {
        private static String SCHEDULE_ID = "schedule_id";
    }

    private static PreferencesHelper sInstance;
    private SharedPreferences mPrefs;

    public static PreferencesHelper getInstance(Context c) {
        return sInstance != null ? sInstance : new PreferencesHelper(c);
    }

    private PreferencesHelper(Context c) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(c);
        sInstance = this;
    }

    public String getScheduleId() {
        return mPrefs.getString(Key.SCHEDULE_ID, "oof");
    }

    public void setScheduleId(String scheduleId) {
        mPrefs.edit().putString(Key.SCHEDULE_ID, scheduleId).apply();
    }
}
