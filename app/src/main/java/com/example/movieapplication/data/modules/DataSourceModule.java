package com.example.movieapplication.data.modules;

import com.example.movieapplication.data.MoviesDatabase;
import com.example.movieapplication.data.api.DataManager;
import com.example.movieapplication.data.api.DataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {

    @Provides
    @Singleton
    public DataSource provideDataSource(MoviesDatabase moviesDatabase) {
        return new DataManager(moviesDatabase);
    }
}
