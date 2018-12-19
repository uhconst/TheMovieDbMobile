package com.uhc.themoviedbmobile.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uhc.themoviedbmobile.R;
import com.uhc.themoviedbmobile.adapter.MovieAdapter;
import com.uhc.themoviedbmobile.viewmodel.MovieViewModel;
import com.uhc.themoviedbmobile.viewmodel.ViewModelFactory;

public class MainActivity extends TMDMActivity {
    private MovieViewModel view_model;
    private LinearLayout ln_last_update;
    private TextView txv_last_update;
    private TextView txv_empty_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelFactory view_model_factory = ViewModelFactory.createFactory(this);
        view_model = ViewModelProviders.of(this, view_model_factory).get(MovieViewModel.class);

        MovieAdapter adapter = new MovieAdapter(this);
        view_model.getAllMovies(this).observe(this, adapter::submitList);
        view_model.isFirstTime().observe(this, this::displayFirstTimeNoConnection);

        ln_last_update = findViewById(R.id.ln_last_update);
        txv_last_update = findViewById(R.id.txv_last_update);
        txv_empty_list = findViewById(R.id.txv_empty_list);

        RecyclerView rv = findViewById(R.id.rv_movies);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);

        setLastUpdate();

        SwipeRefreshLayout pullToRefresh = findViewById(R.id.srl_refresh);
        pullToRefresh.setOnRefreshListener(() -> {
            view_model.getAllMoviesOnline();
            setLastUpdate();
            pullToRefresh.setRefreshing(false);
        });
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

    private void setLastUpdate() {
        String last_update = view_model.getLastUpdate();
        txv_last_update.setText(last_update);
        ln_last_update.setVisibility(last_update.isEmpty() ? View.GONE : View.VISIBLE);
    }

    public void displayFirstTimeNoConnection(boolean no_connection) {
        if (no_connection)
            txv_empty_list.setText(R.string.no_connection);
        else
            txv_empty_list.setText("");
    }
}