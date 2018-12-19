package com.uhc.themoviedbmobile.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
    private final DataRepository repository;
    private final MovieAPI movie_api;
    private MutableLiveData<Boolean> first_time = new MutableLiveData<>();
    private WeakReference<Context> weak_ctx;
    private SharedPreferences prefs;
    private boolean inative_all;

    public MovieViewModel(DataRepository repository) {
        this.repository = repository;
        movie_api = APIClient.getClient(APIClient.TMDM_URL + APIClient.DIR_POPULAR);
    }

    @SuppressWarnings("unchecked")
    public LiveData<PagedList<MovieModel>> getAllMovies(Context ctx) {
        this.weak_ctx = new WeakReference<>(ctx);
        prefs = PreferenceManager.getDefaultSharedPreferences(weak_ctx.get());

        getAllMoviesOnline();

        int movie_limit = Integer.parseInt(DEFAULT_MOVIE_LIMIT);
        String pref_limit = prefs.getString(ctx.getString(R.string.pref_key_limit), DEFAULT_MOVIE_LIMIT);

        if (pref_limit != null)
            movie_limit = Integer.parseInt(pref_limit);

        return new LivePagedListBuilder<>(repository.getMovies(movie_limit), new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(PLACEHOLDERS)
                .build()).build();
    }

    public void getAllMoviesOnline() {
        if (TMDMUtils.isNetworkAvailable(weak_ctx.get())) {
            for (int page = 1; page <= TOTAL_PAGES; page++) {
                Call<ArrayList<MovieModel>> callBack = movie_api.getMovies(APIClient.API_KEY_VALUE, APIClient.LANGUAGE, page);
                callBack.enqueue(new Callback<ArrayList<MovieModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<MovieModel>> call, @NonNull Response<ArrayList<MovieModel>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<MovieModel> movies = response.body();
                            prefs.edit().putBoolean(weak_ctx.get().getString(R.string.pref_key_first_time), false).apply();
                            first_time.setValue(false);

                            // Inative all movies before inserting/updating
                            if (!inative_all) {
                                repository.inativeAll();
                                inative_all = true;
                            }

                            if (movies != null) {
                                for (MovieModel movie : movies) {
                                    boolean isFavorite = repository.isMovieFavorite(movie.getId());
                                    movie.setFavorite(isFavorite);
                                    movie.setAtivo(true);
                                    repository.insert(movie);
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
            first_time.setValue(prefs.getBoolean(weak_ctx.get().getString(R.string.pref_key_first_time), true));
            Log.d("getAllMoviesOnline", "No connection.");
        }
    }

    public String getLastUpdate() {
        if (TMDMUtils.isNetworkAvailable(weak_ctx.get()))
            return "";
        return prefs.getString(weak_ctx.get().getString(R.string.pref_key_last_update), "");
    }

    private void setLastUpdateNow() {
        prefs.edit().putString(weak_ctx.get().getString(R.string.pref_key_last_update), TMDMUtils.getCurrentDateTime()).apply();
    }

    public MutableLiveData<Boolean> isFirstTime() {
        return first_time;
    }
}
