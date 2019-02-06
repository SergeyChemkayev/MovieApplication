package com.example.movieapplication.data.api;

import com.example.movieapplication.entity.Movie;

import java.util.List;

import io.reactivex.Flowable;

public interface MovieCacheSource {

    void putMovies(List<Movie> movies);

    Flowable<List<Movie>> getMovies();

    void removeMovies();

    void updateMovies(List<Movie> movies);
}
