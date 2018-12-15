package com.uhc.themoviedbmobile.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uhc.themoviedbmobile.R;
import com.uhc.themoviedbmobile.api.APIClient;
import com.uhc.themoviedbmobile.data.Movie;
import com.uhc.themoviedbmobile.paging.MovieDetailsViewModel;
import com.uhc.themoviedbmobile.paging.ViewModelFactory;

/**
 * Created by const on 12/15/18.
 */
public class MovieDetailsActivity extends TMDMActivity {
    private MovieDetailsViewModel mdViewModel;
    private int id;

    private ImageView imv_poster;
    private TextView txv_title;
    private TextView txv_release_date;
    private TextView txv_popularity;
    private TextView txv_vote_average;
    private TextView txv_vote_count;
    private TextView txv_adult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        id = getIntent().getIntExtra("id", 0);

        ViewModelFactory viewModelFactory = ViewModelFactory.createFactory(this);
        mdViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel.class);

        imv_poster = findViewById(R.id.txv_details_poster);
        txv_title = findViewById(R.id.txv_details_title);
        txv_release_date = findViewById(R.id.txv_details_release_date);
        txv_popularity = findViewById(R.id.txv_details_popularity);
        txv_vote_average = findViewById(R.id.txv_details_vote_average);
        txv_vote_count = findViewById(R.id.txv_details_vote_count);
        txv_adult = findViewById(R.id.txv_details_adult);

        mdViewModel.setMovie(id).observe(this, this::loadMovie);
    }

    private void loadMovie(Movie movie) {
        if (movie == null) {
            return;
        }

        String poster = APIClient.getFullPosterPath(movie.getPoster_path());
        Picasso.get().load(poster).into(imv_poster);

        txv_title.setText(movie.getTitle());
        txv_release_date.setText(movie.getRelease_date());
        txv_popularity.setText(String.valueOf(movie.getPopularity()));
        txv_vote_average.setText(String.valueOf(movie.getVote_average()));
        txv_vote_count.setText(String.valueOf(movie.getVote_count()));
        txv_adult.setText(movie.isAdult() ? Movie.IsAdult : Movie.IsNotAdult);
    }

}
