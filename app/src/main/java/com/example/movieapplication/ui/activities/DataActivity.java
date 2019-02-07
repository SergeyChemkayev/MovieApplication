package com.example.movieapplication.ui.activities;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.movieapplication.R;
import com.example.movieapplication.data.api.DataManager;
import com.example.movieapplication.data.api.DataSource;
import com.example.movieapplication.data.listeners.OnMovieClickListener;
import com.example.movieapplication.entity.Movie;
import com.example.movieapplication.ui.adapter.MoviesAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class DataActivity extends AppCompatActivity implements OnMovieClickListener {

    private MoviesAdapter adapter;
    @BindView(R.id.data_movies_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.data_empty_view)
    View emptyView;
    @BindView(R.id.data_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isAbleToLoadMovies = true;
    private DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dataSource = DataManager.getInstance();
        adapter = new MoviesAdapter();
        adapter.setOnMovieClickListener(this);
        initRecyclerView();
        initSwipeRefreshLayout();
        getMovies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(adapter);
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.data_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (isAbleToLoadMovies) {
                getMovies();
            }
        });
    }

    @Override
    public void onMovieClick(Movie movie, View coverView, View nameEngView, View premiereDateView) {
        openMovieActivity(movie, coverView, nameEngView, premiereDateView);
    }

    private void openMovieActivity(Movie movie, View coverView, View nameEngView, View premiereDateView) {
        Pair<View, String> movieCoverPair = Pair.create(coverView, getText(R.string.movie_cover_transition_name).toString());
        Pair<View, String> movieNameEngViewPair = Pair.create(nameEngView, getText(R.string.movie_name_eng_transition_name).toString());
        Pair<View, String> moviePremiereDateViewPair = Pair.create(premiereDateView, getText(R.string.movie_premiere_transition_name).toString());
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, movieCoverPair, movieNameEngViewPair, moviePremiereDateViewPair);
        MovieActivity.open(this, movie, options);
    }

    private void getMovies() {
        isAbleToLoadMovies = false;
        swipeRefreshLayout.setRefreshing(true);
        compositeDisposable.add(dataSource.loadMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetMoviesSuccess, this::onGetMoviesError));
    }


    public void onGetMoviesSuccess(List<Movie> movies) {
        hideLoading();
        isAbleToLoadMovies = true;
        setMovies(movies);
        updateViewsVisibility();
    }

    public void onGetMoviesError(Throwable error) {
        hideLoading();
        isAbleToLoadMovies = true;
        Toast.makeText(DataActivity.this, R.string.error_message, Toast.LENGTH_SHORT).show();
        updateViewsVisibility();
    }

    private void setMovies(List<Movie> movies) {
        adapter.setMovies(movies);
    }

    private void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void updateViewsVisibility() {
        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }
}
