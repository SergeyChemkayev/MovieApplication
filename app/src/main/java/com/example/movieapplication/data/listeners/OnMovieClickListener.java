package com.example.movieapplication.data.listeners;

import android.view.View;

import com.example.movieapplication.entity.Movie;

public interface OnMovieClickListener {

    void onMovieClick(Movie movie, View coverView, View nameEngView, View premiereDateView);
}
