package com.example.movieapplication.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapplication.R;
import com.example.movieapplication.data.listeners.OnMovieClickListener;
import com.example.movieapplication.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private OnMovieClickListener onMovieClickListener;

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setMovies(List<Movie> movies) {
        if (movies == null) {
            movies = new ArrayList<>();
        }
        dispatchUpdates(movies);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new MovieViewHolder(inflater.inflate(R.layout.movie_item, viewGroup, false), onMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    private void dispatchUpdates(List<Movie> newList) {
        MoviesDiffUtilCallback moviesDiffUtilCallback = new MoviesDiffUtilCallback(movies, newList);
        DiffUtil.DiffResult itemsDiffResult = DiffUtil.calculateDiff(moviesDiffUtilCallback);
        movies = newList;
        itemsDiffResult.dispatchUpdatesTo(this);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameView;
        private TextView nameEngView;
        private TextView premiereDateView;
        private ImageView movieCoverView;

        private Movie movie;
        private OnMovieClickListener onMovieClickListener;

        public MovieViewHolder(View view, OnMovieClickListener onMovieClickListener) {
            super(view);
            nameView = itemView.findViewById(R.id.movie_name_text_view);
            nameEngView = itemView.findViewById(R.id.movie_name_eng_text_view);
            premiereDateView = itemView.findViewById(R.id.movie_premiere_date_text_view);
            movieCoverView = itemView.findViewById(R.id.movie_cover_image_view);
            this.onMovieClickListener = onMovieClickListener;
            view.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            this.movie = movie;
            nameView.setText(this.movie.getName());
            nameEngView.setText(this.movie.getNameEng());
            premiereDateView.setText(this.movie.getPremiere());
            Glide.with(itemView.getContext())
                    .load(this.movie.getImage())
                    .apply(new RequestOptions().centerCrop())
                    .into(movieCoverView);
        }

        @Override
        public void onClick(View v) {
            if (onMovieClickListener != null) {
                onMovieClickListener.onMovieClick(movie, movieCoverView, nameEngView, premiereDateView);
            }
        }
    }
}
