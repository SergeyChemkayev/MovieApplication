package com.example.movieapplication.data;

import android.app.Application;
import android.arch.persistence.room.Room;

public class MovieApplication extends Application {

    public static MovieApplication INSTANCE;
    private MoviesDatabase moviesDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        moviesDatabase = Room.databaseBuilder(this, MoviesDatabase.class, "movies").build();
    }

    public static MovieApplication getInstance() {
        return INSTANCE;
    }

    public MoviesDatabase getDatabase() {
        return moviesDatabase;
    }
}
