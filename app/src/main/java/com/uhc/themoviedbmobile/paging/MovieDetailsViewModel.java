package com.uhc.themoviedbmobile.paging;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.uhc.themoviedbmobile.data.DataRepository;
import com.uhc.themoviedbmobile.data.Movie;

/**
 * Created by const on 12/15/18.
 */
public class MovieDetailsViewModel extends ViewModel {
    private final DataRepository mRepository;
    private MutableLiveData movie;
    private final MediatorLiveData<Movie> mMediator = new MediatorLiveData<>();
    private LiveData<Movie> mMovie = null;

    public MovieDetailsViewModel(DataRepository repository) {
        this.mRepository = repository;
        movie = new MutableLiveData<Movie>();
    }

    public MutableLiveData getMovie() {
        return movie;
    }

    /**
     * Load Movie from the database and set up the movie details.
     *
     * @return observable data of Movie
     */
    public MediatorLiveData<Movie> setMovie(int id) {
        if (mMovie == null) {
            mMediator.addSource(loadMovie(id), mMediator::setValue);
        }

        return mMediator;
    }


    private LiveData<Movie> loadMovie(int id) {
        mMovie = mRepository.getMovie(id);
        return mMovie;
    }

}
