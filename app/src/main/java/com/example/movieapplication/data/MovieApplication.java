package com.example.movieapplication.data;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.movieapplication.data.modules.DataSourceModule;
import com.example.movieapplication.data.modules.DisposerModule;
import com.example.movieapplication.data.modules.MovieApplicationModule;
import com.example.movieapplication.data.modules.MoviesAdapterModule;

public class MovieApplication extends Application {

    private static MovieApplicationComponent component;
    private MoviesDatabase moviesDatabase;


    @Override
    public void onCreate() {
        super.onCreate();
        moviesDatabase = Room.databaseBuilder(this, MoviesDatabase.class, "movies").build();
        component = buildComponent();
    }

    public static MovieApplicationComponent getComponent() {
        return component;
    }

    protected MovieApplicationComponent buildComponent() {
        return DaggerMovieApplicationComponent.builder()
                .dataSourceModule(new DataSourceModule())
                .disposerModule(new DisposerModule())
                .moviesAdapterModule(new MoviesAdapterModule())
                .movieApplicationModule(new MovieApplicationModule(this, moviesDatabase))
                .build();
    }
}
