package com.uhc.themoviedbmobile.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.uhc.themoviedbmobile.data.DataMovieName;


/**
 * Created by const on 12/12/18.
 */
@Entity(tableName = DataMovieName.TABLE_NAME)
public class MovieModel {
    public static final String IsAdult = "Yes";
    public static final String IsNotAdult = "No";

    public static final String FavoriteAdded = "Movie added to favorite list.";
    public static final String FavoriteRemoved = "Movie removed from favorite list.";

    @PrimaryKey
    @ColumnInfo(name = DataMovieName.COL_ID)
    private int id;

    @ColumnInfo(name = DataMovieName.COL_POSTER_PATH)
    private String poster_path;

    @ColumnInfo(name = DataMovieName.COL_ADULT)
    private boolean adult;

    @ColumnInfo(name = DataMovieName.COL_OVERVIEW)
    private String overview;

    @ColumnInfo(name = DataMovieName.COL_RELEASE_DATE)
    private String release_date;

    @ColumnInfo(name = DataMovieName.COL_ORIGINAL_TITLE)
    private String original_title;

    @ColumnInfo(name = DataMovieName.COL_ORIGINAL_LANGUAGE)
    private String original_language;

    @ColumnInfo(name = DataMovieName.COL_TITLE)
    private String title;

    @ColumnInfo(name = DataMovieName.COL_BACKDROP_PATH)
    private String backdrop_path;

    @ColumnInfo(name = DataMovieName.COL_POPULARITY)
    private double popularity;

    @ColumnInfo(name = DataMovieName.COL_VOTE_COUNT)
    private int vote_count;

    @ColumnInfo(name = DataMovieName.COL_VIDEO)
    private boolean video;

    @ColumnInfo(name = DataMovieName.COL_VOTE_AVERAGE)
    private double vote_average;

    @ColumnInfo(name = DataMovieName.COL_FAVORITE)
    private boolean favorite;

    @ColumnInfo(name = DataMovieName.COL_ATIVO)
    private boolean ativo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
