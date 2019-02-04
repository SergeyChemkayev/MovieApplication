package com.example.movieapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapplication.entity.MovieElement;
import com.example.movieapplication.vieholder.MovieElementViewHolder;

public class MoviesAdapter extends RecyclerView.Adapter<MovieElementViewHolder> {

    @NonNull
    @Override
    public MovieElementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieElementViewHolder holder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MovieViewHolder extends MovieElementViewHolder implements View.OnClickListener{
        private TextView nameView;
        private TextView nameEngView;
        private TextView descriptionView;
        private TextView premiereDateView;
        private ImageView movieCoverView;

        public MovieViewHolder (View view){
            super(view);
            nameView = itemView.findViewById(R.id.movie_name_text_view);
            nameEngView = itemView.findViewById(R.id.movie_name_eng_text_view);
            descriptionView = itemView.findViewById(R.id.movie_description_text_view);
            premiereDateView = itemView.findViewById(R.id.movie_premiere_date_text_view);
            movieCoverView = itemView.findViewById(R.id.movie_cover_image_view);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void bind(MovieElement movieElement) {

        }
    }
}
