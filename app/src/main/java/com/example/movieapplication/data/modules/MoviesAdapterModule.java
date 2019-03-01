package com.example.movieapplication.data.modules;

import com.example.movieapplication.ui.adapter.MoviesAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesAdapterModule {

    @Provides
    @Singleton
    public MoviesAdapter provideMoviesAdapter(){
        return new MoviesAdapter();
    }
}
