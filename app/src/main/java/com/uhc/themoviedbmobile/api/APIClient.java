package com.uhc.themoviedbmobile.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uhc.themoviedbmobile.model.MovieModel;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by const on 12/12/18.
 */
public class APIClient {
    // *** URLS ***
    public static final String TMDM_URL = "https://api.themoviedb.org/3/movie/";
    public static final String DIR_POPULAR = "popular/";
    public static final String TMDM_IMAGE_URL = "https://image.tmdb.org/t/p/w300";

    // *** API KEY ***
    public static final String API_KEY_PARAM = "api_key";
    public static final String API_KEY_VALUE = "a6122c64e9f272fb827cb5d0f219aabb";

    /// *** PARAMS ***/
    public static final String LANGUAGE_REQUEST_PARAM = "language";
    public static final String PAGE_REQUEST_PARAM = "page";

    public static final String ARRAY_RESULTS = "results";

    public static final String LANGUAGE = "en"; // todo get this dynamically

    public static MovieAPI getClient(String URL) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter((new ArrayList<MovieModel>()).getClass(), new MovieJsonDeserializer())
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(URL);

        return builder.build().create(MovieAPI.class);
    }

    public static String getFullPosterPath(String path) {
        if (path != null)
            return APIClient.TMDM_IMAGE_URL + path;

        return "";
    }
}
