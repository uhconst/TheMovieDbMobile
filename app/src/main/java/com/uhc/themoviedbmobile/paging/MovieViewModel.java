package com.uhc.themoviedbmobile.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.uhc.themoviedbmobile.api.APIClient;
import com.uhc.themoviedbmobile.api.MovieAPI;
import com.uhc.themoviedbmobile.data.DataRepository;
import com.uhc.themoviedbmobile.data.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by const on 12/12/18.
 */
public class MovieViewModel extends ViewModel {

    private final DataRepository mRepository;
    private final MovieAPI mAPI;
    private static int PAGE_SIZE = 30;
    private static boolean PLACEHOLDERS = true;

    public MovieViewModel(DataRepository repository) {
        mRepository = repository;
        mAPI = APIClient.getClient(APIClient.TMDM_URL + APIClient.DIR_POPULAR);
    }

    @SuppressWarnings("unchecked")
    public LiveData<PagedList<Movie>> getAllMovies() {
        getAllMoviesOnline();
        return new LivePagedListBuilder<>(mRepository.getMovies(), new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(PLACEHOLDERS)
                .build()).build();
    }

    private void getAllMoviesOnline() {
        Call<ArrayList<Movie>> callBack = mAPI.getMovies(APIClient.API_KEY_VALUE, APIClient.LANGUAGE, 1);
        callBack.enqueue(new Callback<ArrayList<Movie>>() {
            @Override
            public void onResponse(Call<ArrayList<Movie>> call, Response<ArrayList<Movie>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Movie> movies = response.body();

                    mRepository.insertAll(movies);

                } else {
                    Log.e("API CALL", response.message());
//                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Movie>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "Unknown Error";
                } else {
                    errorMessage = t.getMessage();
                }
                // todo log errors
//                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
//                callback.onResult(new ArrayList<>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }

    public void updateMovieFavorite(int id, boolean favorite) {
        mRepository.updateMovieFavorite(id, favorite);
    }
}
