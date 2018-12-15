package com.uhc.themoviedbmobile.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;
import com.uhc.themoviedbmobile.BuildConfig;

/**
 * Created by const on 12/15/18.
 */
public class TMDMActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEVELOPMENT)
            Stetho.initializeWithDefaults(this);
    }
}
