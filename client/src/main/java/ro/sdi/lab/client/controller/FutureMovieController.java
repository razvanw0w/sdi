package ro.sdi.lab.client.controller;

import ro.sdi.lab.common.model.Movie;
import ro.sdi.lab.common.model.Sort;

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
