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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private OnMovieClickListener onMovieClickListener;

    public MoviesAdapter() {
        movies = new ArrayList<>();
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setMovies(List<Movie> movies) {
        Set<Movie> moviesSet = new HashSet<>(movies);
        movies.clear();
        movies.addAll(moviesSet);
        dispatchUpdates(movies);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        return new MovieViewHolder(inflater.inflate(R.layout.item_movie, viewGroup, false));
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

      class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_name_text_view)
        TextView nameView;
        @BindView(R.id.movie_name_eng_text_view)
        TextView nameEngView;
        @BindView(R.id.movie_premiere_date_text_view)
        TextView premiereDateView;
        @BindView(R.id.movie_cover_image_view)
        ImageView movieCoverView;

        private Movie movie;

        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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

        @OnClick
        public void onClick(View v) {
            if (onMovieClickListener != null) {
                onMovieClickListener.onMovieClick(movie, movieCoverView, nameEngView, premiereDateView);
            }
        }
    }
}
