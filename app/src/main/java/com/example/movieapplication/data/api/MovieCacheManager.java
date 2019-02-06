package com.example.movieapplication.data.api;

import com.example.movieapplication.data.MovieApplication;
import com.example.movieapplication.data.RoomMoviesDao;
import com.example.movieapplication.entity.Movie;
import com.example.movieapplication.entity.RoomMovie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class MovieCacheManager implements MovieCacheSource {

    private RoomMoviesDao roomMoviesDao = MovieApplication.getInstance().getDatabase().moviesDao();

    @Override
    public void putMovies(List<Movie> movies) {
        new Thread(() -> roomMoviesDao.insertAll(parseMoviesToRoomMovies(movies).toArray(new RoomMovie[0]))).start();
    }

    @Override
    public Flowable<List<Movie>> getMovies() {
        return roomMoviesDao.getAll().map(this::parseRoomMoviesToMovies);
    }

    @Override
    public void removeMovies() {
        new Thread(() -> roomMoviesDao.removeAll()).start();
    }

    @Override
    public void updateMovies(List<Movie> movies) {
        new Thread(() -> roomMoviesDao.updateData(parseMoviesToRoomMovies(movies).toArray(new RoomMovie[0]))).start();
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
