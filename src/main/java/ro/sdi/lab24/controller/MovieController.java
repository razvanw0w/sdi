package ro.sdi.lab24.controller;

import ro.sdi.lab24.exception.AlreadyExistingElementException;
import ro.sdi.lab24.exception.ElementNotFoundException;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.repository.Repository;

public class MovieController
{
    Repository<Integer, Movie> movieRepository;

    public MovieController(Repository<Integer, Movie> movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    /**
     * This function adds a movie to the repository
     *
     * @param id:   the ID of the movie
     * @param name: the name of the movie
     * @throws AlreadyExistingElementException if the movie (the ID) is already there
     */
    public void addMovie(int id, String name)
    {
        Movie movie = new Movie(id, name);
        movieRepository.save(movie).ifPresent(opt ->
        {
            throw new AlreadyExistingElementException(String.format("movie %d already exists", id));
        });
    }

    /**
     * This function removes a movie from the repository based on their ID
     *
     * @param id: the ID of the movie
     * @throws ElementNotFoundException if the movie isn't found in the repository based on their ID
     */
    public void deleteMovie(int id)
    {
        movieRepository.delete(id).orElseThrow(() -> new ElementNotFoundException(String.format("Movie %d does not exists", id)));
    }

    /**
     * This function returns an iterable collection of the current state of the movies in the repository
     *
     * @return all: an iterable collection of movies
     */
    public Iterable<Movie> getMovies()
    {
        return movieRepository.findAll();
    }

    /**
     * This function updated a movie based on their ID with a new name
     *
     * @param id:   the movie's ID
     * @param name: the new name of the movie
     * @throws ElementNotFoundException if the movie isn't found in the repository based on their ID
     */
    public void updateMovie(int id, String name)
    {
        Movie movie = new Movie(id, name);
        movieRepository.update(movie).orElseThrow(() -> new ElementNotFoundException(String.format("Movie %d does not exists", id)));
    }
}
