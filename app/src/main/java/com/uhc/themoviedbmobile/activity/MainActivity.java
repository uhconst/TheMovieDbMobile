package com.uhc.themoviedbmobile.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.uhc.themoviedbmobile.R;
import com.uhc.themoviedbmobile.adapter.MovieAdapter;
import com.uhc.themoviedbmobile.model.MovieModel;
import com.uhc.themoviedbmobile.viewmodel.MovieViewModel;
import com.uhc.themoviedbmobile.viewmodel.ViewModelFactory;

public class MainActivity extends TMDMActivity {
    private RecyclerView mRecycler;
    private MovieViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = ViewModelFactory.createFactory(this);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);

        setContentView(R.layout.activity_main);

        MovieAdapter adapter = new MovieAdapter(this);
        mViewModel.getAllMovies().observe(this, adapter::submitList);

        mRecycler = findViewById(R.id.rv_movies);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(adapter);

        initAction();
    }

    public void initAction() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                MovieModel movie = ((MovieAdapter.MovieViewHolder) viewHolder).getMovie();
                mViewModel.updateMovieFavorite(movie.getId(), !movie.isFavorite());
            }
        });

        itemTouchHelper.attachToRecyclerView(mRecycler);
    }
}