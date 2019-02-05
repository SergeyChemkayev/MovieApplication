package com.example.movieapplication;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.movieapplication.entity.Movie;
import com.example.movieapplication.entity.MovieList;
import com.example.movieapplication.listeners.OnMovieClickListener;

import java.util.List;

import api.MoviesNetwork;
import api.MoviesRemoteSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {

    private MoviesAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MoviesRemoteSource moviesRemoteSource = MoviesNetwork.getInstance();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isAbleToLoadMovies = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyView = findViewById(R.id.data_empty_view);
        adapter = new MoviesAdapter();
        adapter.setOnMovieClickListener(this);
        initRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.data_movies_recycler_view);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMovieClick(Movie movie, View coverView, View descriptionView) {

    }

    private void getMovies(){
        isAbleToLoadMovies = false;
        swipeRefreshLayout.setRefreshing(true);
        compositeDisposable.add(moviesRemoteSource.getMovieListObservable()
        .map(MovieList::getList)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onGetMoviesSuccess, this::onGetMoviesError));
    }

    public void onGetMoviesSuccess(List<Movie> movies){
        hideLoading();
        isAbleToLoadMovies = true;
    }

    public void onGetMoviesError(Throwable error){
        hideLoading();
        isAbleToLoadMovies = true;
        Toast.makeText(MainActivity.this,R.string.error_message,Toast.LENGTH_SHORT).show();
    }

    private void hideLoading(){
        swipeRefreshLayout.setRefreshing(false);
    }
}
