package com.example.movieapplication.data.api;

import com.example.movieapplication.data.MovieApplication;
import com.example.movieapplication.data.RoomMoviesDao;
import com.example.movieapplication.entity.Movie;
import com.example.movieapplication.entity.MovieList;
import com.example.movieapplication.entity.RoomMovie;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager implements DataSource {

    private volatile static DataSource instance;
    private static final int TIMEOUT_SECONDS = 20;

    private RoomMoviesDao roomMoviesDao = MovieApplication.getInstance().getDatabase().moviesDao();

    private DataManager() {
    }

    public static DataSource getInstance() {
        DataSource dataSource = instance;
        if (null == dataSource) {
            synchronized (DataManager.class) {
                dataSource = instance;
                if (null == dataSource) {
                    instance = dataSource = new DataManager();
                }
            }
        }
        return dataSource;
    }

    @Override
    public Observable<List<Movie>> loadMovies() {
        return getMoviesFromApi().toObservable()
                .doOnNext(this::updateMoviesCache)
                .onErrorResumeNext(getMoviesFromCache().toObservable());
    }

    private Single<List<Movie>> getMoviesFromApi() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = builder.build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd").create();

        return new Retrofit.Builder()
                .baseUrl(MoviesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build()
                .create(MoviesApi.class)
                .movies()
                .map(MovieList::getList);
    }

    private Flowable<List<Movie>> getMoviesFromCache() {
        return roomMoviesDao.getAll()
                .map(this::parseRoomMoviesToMovies);
    }

    private void updateMoviesCache(List<Movie> movies) {
        roomMoviesDao.updateData(parseMoviesToRoomMovies(movies).toArray(new RoomMovie[0]));
    }

    private List<RoomMovie> parseMoviesToRoomMovies(List<Movie> movies) {
        List<RoomMovie> roomMovies = new ArrayList<>();
        for (Movie movie : movies) {
            roomMovies.add(new RoomMovie(movie.getName(), movie.getNameEng(), movie.getDescription(), movie.getPremiere(), movie.getImage()));
        }
        return roomMovies;
    }

    private List<Movie> parseRoomMoviesToMovies(List<RoomMovie> roomMovies) {
        List<Movie> movies = new ArrayList<>();
        for (RoomMovie roomMovie : roomMovies) {
            movies.add(new Movie(roomMovie.getName(), roomMovie.getNameEng(), roomMovie.getDescription(), roomMovie.getPremiere(), roomMovie.getCover()));
        }
        return movies;
    }
}
