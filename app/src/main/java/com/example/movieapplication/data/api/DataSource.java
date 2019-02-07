package com.example.movieapplication.data.api;

import com.example.movieapplication.entity.Movie;

import java.util.List;

import io.reactivex.Observable;

public interface DataSource {

    void putMovies(List<Movie> movies);

    Observable<List<Movie>> loadMovies();

    void removeMovies();

    void updateMovies(List<Movie> movies);
}
