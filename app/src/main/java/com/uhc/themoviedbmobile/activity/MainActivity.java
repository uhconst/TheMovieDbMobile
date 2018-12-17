package com.uhc.themoviedbmobile.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.uhc.themoviedbmobile.R;
import com.uhc.themoviedbmobile.adapter.MovieAdapter;
import com.uhc.themoviedbmobile.viewmodel.MovieViewModel;
import com.uhc.themoviedbmobile.viewmodel.ViewModelFactory;

public class MainActivity extends TMDMActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelFactory viewModelFactory = ViewModelFactory.createFactory(this);
        MovieViewModel view_model = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);

        MovieAdapter adapter = new MovieAdapter(this);
        view_model.getAllMovies(this).observe(this, adapter::submitList);

        RecyclerView rv = findViewById(R.id.rv_movies);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivity(settingIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}