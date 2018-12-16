package com.uhc.themoviedbmobile.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uhc.themoviedbmobile.R;
import com.uhc.themoviedbmobile.api.APIClient;
import com.uhc.themoviedbmobile.model.MovieModel;
import com.uhc.themoviedbmobile.viewmodel.MovieDetailsViewModel;
import com.uhc.themoviedbmobile.viewmodel.ViewModelFactory;

/**
 * Created by const on 12/15/18.
 */
public class MovieDetailsActivity extends TMDMActivity {
    private MovieDetailsViewModel mdViewModel;
    private MovieModel movie;
    private int id;

    private ImageView imv_poster;
    private ImageView imv_favorite;
    private LinearLayout ln_favorite;
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
        ln_favorite = findViewById(R.id.ln_details_favorite);
        imv_favorite = findViewById(R.id.imv_details_favorite);
        txv_release_date = findViewById(R.id.txv_details_release_date);
        txv_popularity = findViewById(R.id.txv_details_popularity);
        txv_vote_average = findViewById(R.id.txv_details_vote_average);
        txv_vote_count = findViewById(R.id.txv_details_vote_count);
        txv_adult = findViewById(R.id.txv_details_adult);

        ln_favorite.setOnClickListener(view -> {
            mdViewModel.updateMovieFavorite(movie.getId(), !movie.isFavorite());
        });

        mdViewModel.setMovie(id).observe(this, this::loadMovie);
    }

    private void loadMovie(MovieModel movie) {
        if (movie == null) {
            return;
        }
        this.movie = movie;

        String poster = APIClient.getFullPosterPath(movie.getPoster_path());
        Picasso.get().load(poster).into(imv_poster);

        imv_favorite.setImageResource(movie.isFavorite() ? R.drawable.icon_favorite : R.drawable.icon_not_favorite);
        imv_favorite.setColorFilter(ContextCompat.getColor(this, R.color.favorite_star_color));
        txv_title.setText(movie.getTitle());
        txv_release_date.setText(movie.getRelease_date());
        txv_popularity.setText(String.valueOf(movie.getPopularity()));
        txv_vote_average.setText(String.valueOf(movie.getVote_average()));
        txv_vote_count.setText(String.valueOf(movie.getVote_count()));
        txv_adult.setText(movie.isAdult() ? MovieModel.IsAdult : MovieModel.IsNotAdult);
    }

}