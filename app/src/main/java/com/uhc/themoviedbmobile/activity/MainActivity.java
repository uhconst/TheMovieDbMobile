package com.uhc.themoviedbmobile.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.uhc.themoviedbmobile.R;
import com.uhc.themoviedbmobile.adapter.MovieAdapter;
import com.uhc.themoviedbmobile.viewmodel.MovieViewModel;
import com.uhc.themoviedbmobile.viewmodel.ViewModelFactory;

public class MainActivity extends TMDMActivity {
    private RecyclerView rv;
    private MovieViewModel view_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelFactory viewModelFactory = ViewModelFactory.createFactory(this);
        view_model = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);

        MovieAdapter adapter = new MovieAdapter(this);
        view_model.getAllMovies().observe(this, adapter::submitList);

        rv = findViewById(R.id.rv_movies);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
    }
}