package com.example.movieapplication.data.api;

import com.example.movieapplication.entity.Movie;

import java.util.List;

import io.reactivex.Observable;

public interface DataSource {

    Observable<List<Movie>> loadMovies();
}
