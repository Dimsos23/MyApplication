package ru.dimsos.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

public class SharedPrefsHelper {

    private final SharedPreferences mPrefs;

    public SharedPrefsHelper(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void putString(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
    }

    public void putBoolean(String key, Boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
    }

    public String getString(String key) {
        return mPrefs.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return mPrefs.getBoolean(key, true);
    }
}
