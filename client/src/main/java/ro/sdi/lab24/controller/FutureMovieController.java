package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Sort;

import java.util.concurrent.Future;

public interface FutureMovieController {
    Future<Void> addMovie(int id, String name, String genre, int rating);

    Future<Void> deleteMovie(int id);

    Future<Iterable<Movie>> getMovies();

    Future<Void> updateMovie(
            int id,
            String name,
            String genre,
            Integer rating
    );

    Future<Iterable<Movie>> filterMoviesByGenre(String genre);

    Future<Iterable<Movie>> sortMovies(Sort criteria);
}
