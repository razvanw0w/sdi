package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Sort;

public interface MovieController
{
    void addMovie(int id, String name, String genre, int rating);

    void deleteMovie(int id);

    Iterable<Movie> getMovies();

    void updateMovie(
            int id,
            String name,
            String genre,
            Integer rating
    );

    Iterable<Movie> filterMoviesByGenre(String genre);

    Iterable<Movie> sortMovies(Sort criteria);
}
