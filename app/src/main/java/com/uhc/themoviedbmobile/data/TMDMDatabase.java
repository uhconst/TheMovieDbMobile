package com.uhc.themoviedbmobile.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

/**
 * Created by const on 12/12/18.
 */
@Database(entities = {Movie.class}, version = 1)
public abstract class TMDMDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static volatile TMDMDatabase sInstance = null;

    @VisibleForTesting
    private static final String DATABASE_NAME = "db_movie";

    @NonNull
    public static synchronized TMDMDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (TMDMDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context,
                            TMDMDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return sInstance;
    }
}

