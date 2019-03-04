package com.example.movieapplication.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapplication.R;
import com.example.movieapplication.data.Disposer;
import com.example.movieapplication.data.MovieApplication;
import com.example.movieapplication.entity.Movie;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;

public class MovieActivity extends BaseActivity {

    @BindView(R.id.movie_name_toolbar)
    Toolbar movieNameToolbar;
    @BindView(R.id.movie_cover_toolbar_image_view)
    ImageView movieCoverView;
    @BindView(R.id.movie_description_text_view)
    TextView movieDescriptionView;
    @BindView(R.id.movie_name_eng_text_view)
    TextView movieNameEngTextView;
    @BindView(R.id.movie_premiere_date_text_view)
    TextView moviePremiereTextView;
    @BindView(R.id.movie_share_image_view)
    ImageView movieShareImageView;
    @Inject
    Disposer disposer;

    Movie movie;

    public static void open(Context context, Movie movie, ActivityOptionsCompat options) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra("movie", movie);
        context.startActivity(intent, options.toBundle());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieApplication.getComponent().inject(this);
        initMovie();
        initToolbar();
        initShareButton();
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
        movie = getIntent().getParcelableExtra("movie");
        Glide.with(this)
                .asFile()
                .load(movie.getImage())
                .apply(new RequestOptions().centerCrop())
                .submit();
        movieDescriptionView.setText(movie.getDescription());
        movieNameToolbar.setTitle(movie.getName());
        movieNameEngTextView.setText(movie.getNameEng());
        moviePremiereTextView.setText(movie.getPremiere());
    }

    public void initShareButton() {
        disposer.add(RxView.clicks(movieShareImageView)
                .subscribe(a -> shareMovieName()));
    }

    public void shareMovieName() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(Intent.EXTRA_STREAM, movie.getImage());
        PackageManager packageManager = this.getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Choose sharing method"));
        } else {
            Toast.makeText(this, "you have no apps to share it", Toast.LENGTH_SHORT).show();
        }
    }
}
