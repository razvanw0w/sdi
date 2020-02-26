package ro.sdi.lab24.controller;

import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.repository.Repository;

public class MovieController
{
    Repository<Integer, Movie> movieRepository;

    public MovieController(Repository<Integer, Movie> movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    public void addMovie(int id, String name)
    {

    }

    public void deleteMovie(int id)
    {

    }

    public Iterable<Movie> getMovies()
    {
        return null;
    }

    public void updateMovie(int id, String name)
    {

    }

    //TODO Razvan
}
