package com.uhc.themoviedbmobile.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.uhc.themoviedbmobile.api.APIClient;
import com.uhc.themoviedbmobile.api.MovieAPI;
import com.uhc.themoviedbmobile.data.DataRepository;
import com.uhc.themoviedbmobile.model.MovieModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by const on 12/12/18.
 */
public class MovieViewModel extends ViewModel {

    private final static int PAGE_SIZE = 20;
    private final static int TOTAL_MOVIES = 50;
    private final static int TOTAL_PAGES = 3;
    private final static boolean PLACEHOLDERS = true;
    private final DataRepository mRepository;
    private final MovieAPI mAPI;

    public MovieViewModel(DataRepository repository) {
        mRepository = repository;
        mAPI = APIClient.getClient(APIClient.TMDM_URL + APIClient.DIR_POPULAR);
    }

    @SuppressWarnings("unchecked")
    public LiveData<PagedList<MovieModel>> getAllMovies() {
        getAllMoviesOnline();
        return new LivePagedListBuilder<>(mRepository.getMovies(TOTAL_MOVIES), new PagedList.Config.Builder()
                .setPageSize(PAGE_SIZE)
                .setEnablePlaceholders(PLACEHOLDERS)
                .build()).build();
    }

    private void getAllMoviesOnline() {
        for (int page = 0; page < TOTAL_PAGES; page++) {
            Call<ArrayList<MovieModel>> callBack = mAPI.getMovies(APIClient.API_KEY_VALUE, APIClient.LANGUAGE, page);
            callBack.enqueue(new Callback<ArrayList<MovieModel>>() {
                @Override
                public void onResponse(Call<ArrayList<MovieModel>> call, Response<ArrayList<MovieModel>> response) {
                    if (response.isSuccessful()) {
                        ArrayList<MovieModel> movies = response.body();

                        mRepository.insertAll(movies);

                    } else {
                        Log.e("API CALL", response.message());
                        // todo
//                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<MovieModel>> call, Throwable t) {
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
    }

    public void updateMovieFavorite(int id, boolean favorite) {
        mRepository.updateMovieFavorite(id, favorite);
    }
}
