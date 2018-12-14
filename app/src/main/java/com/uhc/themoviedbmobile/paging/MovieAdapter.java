package com.uhc.themoviedbmobile.paging;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uhc.themoviedbmobile.R;
import com.uhc.themoviedbmobile.api.APIClient;
import com.uhc.themoviedbmobile.data.Movie;

/**
 * Created by const on 12/12/18.
 */
public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {

    public MovieAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie item = getItem(position);

        if (item != null) {
            holder.bindTo(item);
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mTitle;
        private TextView mPopularity;
        private TextView mFavorite;
        private Movie mMovie;

        MovieViewHolder(View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.adapter_movie_image);
            mTitle = itemView.findViewById(R.id.adapter_movie_title);
            mPopularity = itemView.findViewById(R.id.adapter_movie_popularity);
            mFavorite = itemView.findViewById(R.id.adapter_movie_favorite);
        }

        public Movie getMovie() {
            return mMovie;
        }

        void bindTo(Movie movie) {
            mMovie = movie;

            if (movie.getPoster_path() != null) {
                String poster = APIClient.TMDM_IMAGE_URL + movie.getPoster_path();
                Picasso.get().load(poster).into(mImage);
            }

            String title = movie.getTitle().length() > 30 ? (movie.getTitle().substring(0, 30) + "...") : movie.getTitle();
            mTitle.setText(title);
            mPopularity.setText(String.valueOf(movie.getPopularity()));
            mFavorite.setText(movie.isFavorite() ? "S" : "N"); // todo use a star icon
        }
    }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem,
                                                  @NonNull Movie newItem) {
                    return oldItem == newItem;
                }
            };
}

