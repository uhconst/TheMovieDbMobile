package com.uhc.themoviedbmobile.api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.uhc.themoviedbmobile.model.MovieModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by const on 12/12/18.
 */
class MovieJsonDeserializer implements JsonDeserializer {

    private static String TAG = MovieJsonDeserializer.class.getSimpleName();

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<MovieModel> movies = null;
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            JsonArray moviesJsonArray = jsonObject.getAsJsonArray(APIClient.ARRAY_RESULTS);
            movies = new ArrayList<>(moviesJsonArray.size());
            for (int i = 0; i < moviesJsonArray.size(); i++) {
                MovieModel dematerialized = context.deserialize(moviesJsonArray.get(i), MovieModel.class);
                movies.add(dematerialized);
            }
        } catch (JsonParseException e) {
            Log.e(TAG, String.format("Could not deserialize element: %s", json.toString()));
        }
        return movies;
    }
}
