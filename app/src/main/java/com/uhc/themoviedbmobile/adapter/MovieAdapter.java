package com.uhc.themoviedbmobile.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uhc.themoviedbmobile.R;
import com.uhc.themoviedbmobile.activity.MovieDetailsActivity;
import com.uhc.themoviedbmobile.api.APIClient;
import com.uhc.themoviedbmobile.model.MovieModel;

/**
 * Created by const on 12/12/18.
 */
public class MovieAdapter extends PagedListAdapter<MovieModel, MovieAdapter.MovieViewHolder> {
    private final int MAX_LENGTH = 25;
    private Context ctx;

    public MovieAdapter(Context ctx) {
        super(DIFF_CALLBACK);
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(ctx).inflate(R.layout.adapter_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieModel item = getItem(position);

        if (item != null) {
            holder.bindTo(item);
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ln_item;
        private ImageView imv_poster;
        private ImageView imv_favorite;
        private TextView txv_title;
        private TextView txv_popularity;

        MovieViewHolder(View itemView) {
            super(itemView);

            ln_item = itemView.findViewById(R.id.ln_list_item);

            txv_title = itemView.findViewById(R.id.txv_adapter_movie_title);
            txv_popularity = itemView.findViewById(R.id.txv_adapter_movie_popularity);

            imv_poster = itemView.findViewById(R.id.imv_adapter_movie_poster);
            imv_favorite = itemView.findViewById(R.id.imv_adapter_movie_favorite);
        }

        void bindTo(MovieModel movie) {
            String poster = APIClient.getFullPosterPath(movie.getPoster_path());
            Picasso.get().load(poster).into(imv_poster);

            String title = movie.getTitle().length() > MAX_LENGTH ? (movie.getTitle().substring(0, MAX_LENGTH) + "...") : movie.getTitle();
            txv_title.setText(title);

            txv_popularity.setText(String.valueOf(movie.getPopularity()));
            imv_favorite.setImageResource(movie.isFavorite() ? R.drawable.icon_favorite : R.drawable.icon_not_favorite);
            imv_favorite.setColorFilter(ContextCompat.getColor(ctx, R.color.favorite_star_color));

            ln_item.setOnClickListener(view -> {
                Intent intent = new Intent(ctx, MovieDetailsActivity.class);
                intent.putExtra("id",movie.getId());
                ctx.startActivity(intent);
            });
        }
    }

    private static final DiffUtil.ItemCallback<MovieModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieModel oldItem, @NonNull MovieModel newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull MovieModel oldItem,
                                                  @NonNull MovieModel newItem) {
                    return oldItem == newItem;
                }
            };
}

