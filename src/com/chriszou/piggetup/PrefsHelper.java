package com.chriszou.piggetup;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsHelper {
    private static SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(AlarmApplication.getContext());
    
    public static long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }
    
    public static void putLong(String key, long value) {
        mPreferences.edit().putLong(key, value).commit();
    }

    public static void putInt(String key, int value) {
        mPreferences.edit().putInt(key, value).commit();
    }
    
    public static int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }
}
