package com.uhc.themoviedbmobile.api;

import com.uhc.themoviedbmobile.model.MovieModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by const on 12/12/18.
 */
public interface MovieAPI {

    @GET(".")
    Call<ArrayList<MovieModel>> getMovies(@Query(APIClient.API_KEY_PARAM) String api_Key,
                                          @Query(APIClient.LANGUAGE_REQUEST_PARAM) String language,
                                          @Query(APIClient.PAGE_REQUEST_PARAM) int page);
}
