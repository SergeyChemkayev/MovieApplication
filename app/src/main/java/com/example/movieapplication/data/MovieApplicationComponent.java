package com.example.movieapplication.data;

import com.example.movieapplication.data.modules.DataSourceModule;
import com.example.movieapplication.data.modules.DisposerModule;
import com.example.movieapplication.data.modules.MovieApplicationModule;
import com.example.movieapplication.data.modules.MoviesAdapterModule;
import com.example.movieapplication.ui.activities.DataActivity;
import com.example.movieapplication.ui.activities.MovieActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {DataSourceModule.class, MovieApplicationModule.class, DisposerModule.class, MoviesAdapterModule.class})
@Singleton
public interface MovieApplicationComponent {

    void inject(DataActivity dataActivity);

    void inject(MovieActivity movieActivity);
}
