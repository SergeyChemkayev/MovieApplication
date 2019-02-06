package com.example.movieapplication.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.movieapplication.entity.RoomMovie;

@Database(entities = {RoomMovie.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract RoomMoviesDao moviesDao();
}
