package com.uhc.themoviedbmobile;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.uhc.themoviedbmobile.paging.MovieAdapter;
import com.uhc.themoviedbmobile.paging.MovieViewModel;
import com.uhc.themoviedbmobile.paging.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = ViewModelFactory.createFactory(this);
        MovieViewModel mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);

        setContentView(R.layout.activity_main);

        MovieAdapter adapter = new MovieAdapter();
        mViewModel.getAllMovies().observe(this, adapter::submitList);

        RecyclerView mRecycler = findViewById(R.id.rv_movies);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(adapter);
    }
}