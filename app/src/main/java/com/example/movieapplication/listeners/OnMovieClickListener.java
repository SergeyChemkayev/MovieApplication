package com.example.movieapplication.listeners;

import android.view.View;

import com.example.movieapplication.entity.Movie;

public interface OnMovieClickListener {
    void onMovieClick(Movie movie, View coverView, View descriptionView,View nameEngView, View premiereDateView);
}
