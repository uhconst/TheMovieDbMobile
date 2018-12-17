package com.uhc.themoviedbmobile.activity;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.uhc.themoviedbmobile.R;

/**
 * Created by const on 12/16/18.
 */
public class SettingActivity extends TMDMActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
