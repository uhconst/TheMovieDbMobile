package com.uhc.themoviedbmobile.viewmodel;

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

import java.lang.ref.WeakReference;
import java.util.ArrayList;

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
    private WeakReference<Context> weak_ctx;

    public MovieViewModel(DataRepository repository) {
        mRepository = repository;
        mAPI = APIClient.getClient(APIClient.TMDM_URL + APIClient.DIR_POPULAR);
    }

    @SuppressWarnings("unchecked")
    public LiveData<PagedList<MovieModel>> getAllMovies(Context ctx) {
        this.weak_ctx = new WeakReference<>(ctx);

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

    public void getAllMoviesOnline() {
        if (TMDMUtils.isNetworkAvailable(weak_ctx.get())) {
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

        } else {
            Log.d("getAllMoviesOnline", "No connection.");
        }
    }

    public String getLastUpdate() {
        if (TMDMUtils.isNetworkAvailable(weak_ctx.get()))
            return "";

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(weak_ctx.get());
        return prefs.getString(weak_ctx.get().getString(R.string.pref_key_last_update), "");
    }

    private void setLastUpdateNow() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(weak_ctx.get());
        prefs.edit().putString(weak_ctx.get().getString(R.string.pref_key_last_update), TMDMUtils.getCurrentDateTime()).apply();
    }
}
