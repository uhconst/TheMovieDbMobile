package com.uhc.themoviedbmobile.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.uhc.themoviedbmobile.R;

/**
 * Created by const on 12/16/18.
 */
public class SettingActivity extends TMDMActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
