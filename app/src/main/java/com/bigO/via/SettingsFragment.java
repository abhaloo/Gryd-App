package com.bigO.via;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;

public class SettingsFragment extends androidx.preference.PreferenceFragmentCompat {

    private CheckBoxPreference listViewDefault;

    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }

}
