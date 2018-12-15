package com.uhc.themoviedbmobile.data;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.uhc.themoviedbmobile.model.MovieModel;

import java.util.ArrayList;

/**
 * Created by const on 12/12/18.
 */
@Dao
public interface MovieDao {
    /**
     * Returns all data in table for Paging.
     */
    @Query("SELECT * FROM " + DataMovieName.TABLE_NAME + " ORDER BY " + DataMovieName.COL_POPULARITY + " DESC")
    DataSource.Factory<Integer, MovieModel> getAll();

    /**
     * Returns all data with limit in table for Paging.
     * @param limit Limit movies number to be returned
     */
    @Query("SELECT * FROM " + DataMovieName.TABLE_NAME + " ORDER BY " + DataMovieName.COL_POPULARITY + " DESC LIMIT :limit")
    DataSource.Factory<Integer, MovieModel> getAllLimited(int limit);

    /**
     * Returns Movie by id.
     * @param id Movie id to be get Movie
     */
    @Query("SELECT * FROM " + DataMovieName.TABLE_NAME + " WHERE " + DataMovieName.COL_ID + " = :id")
    LiveData<MovieModel> getMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<MovieModel> movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieModel movie);

    @Delete
    void delete(MovieModel movie);

    @Query("UPDATE " + DataMovieName.TABLE_NAME +
            " SET " + DataMovieName.COL_FAVORITE + " = :favorite" +
            " WHERE " + DataMovieName.COL_ID + " = :id")
    void update(int id, boolean favorite);
}
