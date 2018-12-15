package com.uhc.themoviedbmobile.data;

import android.app.Application;
import android.arch.paging.DataSource;

import java.util.ArrayList;
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

    public DataSource.Factory<Integer, Movie> getMovies(int limit) {
        if (limit > 0)
            return mDao.getAllLimited(limit);

        return mDao.getAll();
    }

    public void insert(Movie movie) {
        mIoExecutor.execute(() -> mDao.insert(movie));
    }
    public void insertAll(ArrayList<Movie> movie) {
        mIoExecutor.execute(() -> mDao.insertAll(movie));
    }

    public void updateMovieFavorite(int id, boolean favorite) {
        mIoExecutor.execute(() -> mDao.update(id, favorite));
    }
}
