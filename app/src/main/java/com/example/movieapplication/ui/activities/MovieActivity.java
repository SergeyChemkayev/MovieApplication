package com.example.movieapplication.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapplication.R;
import com.example.movieapplication.entity.Movie;

public class MovieActivity extends AppCompatActivity {

    private Toolbar movieNameToolbar;

    public static void open(Context context, Movie movie, ActivityOptionsCompat options) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra("movie", movie);
        context.startActivity(intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        initMovie();
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(movieNameToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        movieNameToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initMovie() {
        ImageView movieCoverView = findViewById(R.id.movie_cover_toolbar_image_view);
        TextView movieDescriptionView = findViewById(R.id.movie_description_text_view);
        TextView movieNameEngTextView = findViewById(R.id.movie_name_eng_text_view);
        TextView moviePremiereTextView = findViewById(R.id.movie_premiere_date_text_view);
        movieNameToolbar = findViewById(R.id.movie_name_toolbar);
        Movie movie = getIntent().getParcelableExtra("movie");
        Glide.with(this)
                .load(movie.getImage())
                .apply(new RequestOptions().centerCrop())
                .into(movieCoverView);
        movieDescriptionView.setText(movie.getDescription());
        movieNameToolbar.setTitle(movie.getName());
        movieNameEngTextView.setText(movie.getNameEng());
        moviePremiereTextView.setText(movie.getPremiere());
    }
}
