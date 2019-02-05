package api;

import com.example.movieapplication.entity.MovieList;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MoviesApi {
    String BASE_URL = "http://www.mocky.io/v2/";

    @GET("57cffac8260000181e650041")
    Single<MovieList> movies();
}
