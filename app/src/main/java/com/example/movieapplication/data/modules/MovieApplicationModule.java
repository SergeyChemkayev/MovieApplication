package com.example.movieapplication.data.modules;

import android.content.Context;

import com.example.movieapplication.data.MoviesDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieApplicationModule {
    private Context appContext;
    private MoviesDatabase moviesDatabase;

    public MovieApplicationModule(Context context, MoviesDatabase moviesDatabase) {
        appContext = context;
        this.moviesDatabase = moviesDatabase;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return appContext;
    }

    @Provides
    @Singleton
    public MoviesDatabase provideDatabase() {
        return moviesDatabase;
    }
}
