package com.example.movieapplication.api;

import com.example.movieapplication.entity.MovieList;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesNetwork implements MoviesRemoteSource {

    private static volatile MoviesRemoteSource INSTANCE = null;
    private static final int TIMEOUT_SECONDS = 20;
    private static MoviesApi moviesApi;

    public static MoviesRemoteSource getInstance() {
        MoviesRemoteSource moviesRemoteSource = INSTANCE;
        if (moviesRemoteSource == null) {
            synchronized (MoviesNetwork.class) {
                moviesRemoteSource = INSTANCE;
                if (moviesRemoteSource == null) {
                    INSTANCE = moviesRemoteSource = new MoviesNetwork();
                }
            }
        }
        return moviesRemoteSource;
    }

    private MoviesNetwork() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.build();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd").create();
        moviesApi = new Retrofit.Builder()
                .baseUrl(MoviesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build()
                .create(MoviesApi.class);
    }

    @Override
    public Single<MovieList> getMovieListObservable() {
        return moviesApi.movies();
    }
}
