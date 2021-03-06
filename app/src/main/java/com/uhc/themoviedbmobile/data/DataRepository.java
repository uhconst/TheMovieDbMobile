package com.uhc.themoviedbmobile.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;

import com.uhc.themoviedbmobile.model.MovieModel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by const on 12/12/18.
 */
public class DataRepository {

    private final MovieDao mDao;
    private final ExecutorService mIoExecutor;
    private static volatile DataRepository sInstance = null;

    public static DataRepository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    TMDMDatabase database = TMDMDatabase.getInstance(application);
                    sInstance = new DataRepository(database.movieDao(), Executors.newSingleThreadExecutor());
                }
            }
        }
        return sInstance;
    }

    public DataRepository(MovieDao dao, ExecutorService executor) {
        mIoExecutor = executor;
        mDao = dao;
    }

    public DataSource.Factory<Integer, MovieModel> getMovies(int limit) {
        if (limit > 0)
            return mDao.getAllLimited(limit);

        return mDao.getAll();
    }

    public LiveData<MovieModel> getMovie(int id) {
        try {
            return mIoExecutor.submit(() -> mDao.getMovieById(id)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isMovieFavorite(int id) {
        try {
            return mIoExecutor.submit(() -> mDao.isMovieFavorite(id)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insert(MovieModel movie) {
        mIoExecutor.execute(() -> mDao.insert(movie));
    }

    public void inativeAll() {
        mIoExecutor.execute(mDao::inativeAll);
    }

    public void updateMovieFavorite(int id, boolean favorite) {
        mIoExecutor.execute(() -> mDao.update(id, favorite));
    }
}
