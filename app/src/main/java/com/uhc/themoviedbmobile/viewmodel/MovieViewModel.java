package com.uhc.themoviedbmobile.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.uhc.themoviedbmobile.R;
import com.uhc.themoviedbmobile.api.APIClient;
import com.uhc.themoviedbmobile.api.MovieAPI;
import com.uhc.themoviedbmobile.data.DataRepository;
import com.uhc.themoviedbmobile.model.MovieModel;
import com.uhc.themoviedbmobile.utils.TMDMUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by const on 12/12/18.
 */
public class MovieViewModel extends ViewModel {
    private final static int PAGE_SIZE = 20;
    private final static int TOTAL_PAGES = 3;
    private final static String DEFAULT_MOVIE_LIMIT = "50";
    private final static boolean PLACEHOLDERS = true;
    private final DataRepository mRepository;
    private final MovieAPI mAPI;
    private Context ctx;

    public MovieViewModel(DataRepository repository) {
        mRepository = repository;
        mAPI = APIClient.getClient(APIClient.TMDM_URL + APIClient.DIR_POPULAR);
    }

    @SuppressWarnings("unchecked")
    public LiveData<PagedList<MovieModel>> getAllMovies(Context ctx) {
        this.ctx = ctx;
        if (TMDMUtils.isNetworkAvailable(ctx))
            getAllMoviesOnline();

        int movie_limit = Integer.parseInt(DEFAULT_MOVIE_LIMIT);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String pref_limit = prefs.getString(ctx.getString(R.string.pref_key_limit), DEFAULT_MOVIE_LIMIT);

        if (pref_limit != null)
            movie_limit = Integer.parseInt(pref_limit);

        return new LivePagedListBuilder<>(mRepository.getMovies(movie_limit), new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(PLACEHOLDERS)
                .build()).build();
    }

    private void getAllMoviesOnline() {
        for (int page = 1; page <= TOTAL_PAGES; page++) {
            Call<ArrayList<MovieModel>> callBack = mAPI.getMovies(APIClient.API_KEY_VALUE, APIClient.LANGUAGE, page);
            callBack.enqueue(new Callback<ArrayList<MovieModel>>() {
                @Override
                public void onResponse(Call<ArrayList<MovieModel>> call, Response<ArrayList<MovieModel>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<MovieModel> movies = response.body();

                        if (movies != null) {
                            for (MovieModel movie : movies) {
                                boolean isFavorite = mRepository.isMovieFavorite(movie.getId());
                                movie.setFavorite(isFavorite);
                                mRepository.insert(movie);
                            }
                        }

                        setLastUpdateNow();

                    } else {
                        Log.e("getAllMoviesOnline", response.message());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<MovieModel>> call, Throwable t) {
                    String errorMessage;
                    if (t.getMessage() == null)
                        errorMessage = "Unknown Error";
                    else
                        errorMessage = t.getMessage();

                    Log.e("getAllMoviesOnline", errorMessage);
                }
            });
        }
    }

    public String getLastUpdate() {
        if (TMDMUtils.isNetworkAvailable(ctx))
            return "";

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getString(ctx.getString(R.string.pref_key_last_update), "");
    }

    private void setLastUpdateNow() {
        @SuppressLint("SimpleDateFormat") String date_time = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(Calendar.getInstance().getTime());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefs.edit().putString(ctx.getString(R.string.pref_key_last_update), date_time).apply();
    }
}
