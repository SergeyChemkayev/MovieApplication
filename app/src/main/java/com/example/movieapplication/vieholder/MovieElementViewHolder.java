package com.example.movieapplication.vieholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.movieapplication.entity.MovieElement;

public abstract class MovieElementViewHolder extends RecyclerView.ViewHolder {

    public MovieElementViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(MovieElement movieElement);
}
