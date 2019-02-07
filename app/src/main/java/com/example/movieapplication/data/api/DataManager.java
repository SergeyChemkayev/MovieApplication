package com.example.movieapplication.data.api;

import com.example.movieapplication.data.MovieApplication;
import com.example.movieapplication.data.RoomMoviesDao;
import com.example.movieapplication.entity.Movie;
import com.example.movieapplication.entity.MovieList;
import com.example.movieapplication.entity.RoomMovie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class DataManager implements DataSource {

    private RoomMoviesDao roomMoviesDao = MovieApplication.getInstance().getDatabase().moviesDao();
    private MoviesRemoteSource moviesRemoteSource = MoviesNetwork.getInstance();

    @Override
    public Observable<List<Movie>> loadMovies() {
        return getMoviesFromApi().toObservable()
                .doOnNext(this::updateMoviesCache)
                .onErrorResumeNext(getMoviesFromCache().toObservable());
    }

    private Single<List<Movie>> getMoviesFromApi() {
        return moviesRemoteSource.getMovieListSingle()
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
