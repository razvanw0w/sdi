package ro.sdi.lab.common.controller;

import ro.sdi.lab.common.model.Movie;
import ro.sdi.lab.common.model.Sort;

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
