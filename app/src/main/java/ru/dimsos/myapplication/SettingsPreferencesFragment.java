package ru.dimsos.myapplication;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsPreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}