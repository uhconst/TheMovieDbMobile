package com.uhc.themoviedbmobile.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.uhc.themoviedbmobile.data.DataRepository;
import com.uhc.themoviedbmobile.model.MovieModel;

/**
 * Created by const on 12/15/18.
 */
public class MovieDetailsViewModel extends ViewModel {
    private final DataRepository repository;
    private final MediatorLiveData<MovieModel> mediator = new MediatorLiveData<>();
    private final MutableLiveData<String> msg_update = new MutableLiveData<>();
    private LiveData<MovieModel> live_data_movie = null;

    public MovieDetailsViewModel(DataRepository repository) {
        this.repository = repository;
    }

    /**
     * Load Movie from the database and set up the live_data_movie details.
     *
     * @return observable data of Movie
     */
    public MediatorLiveData<MovieModel> setMovie(int id) {
        if (live_data_movie == null) {
            mediator.addSource(loadMovie(id), mediator::setValue);
        }

        return mediator;
    }


    private LiveData<MovieModel> loadMovie(int id) {
        live_data_movie = repository.getMovie(id);
        return live_data_movie;
    }

    public void updateMovieFavorite(int id, boolean favorite) {
        repository.updateMovieFavorite(id, favorite);
        msg_update.setValue(favorite ? MovieModel.FavoriteAdded : MovieModel.FavoriteRemoved);
    }

    public MutableLiveData<String> getFavoriteMsg() {
        return msg_update;
    }
}
