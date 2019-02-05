package api;

import com.example.movieapplication.entity.MovieList;

import io.reactivex.Single;

public interface MoviesRemoteSource {
    Single<MovieList> getMovieListObservable();
}
