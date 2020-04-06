package ro.sdi.lab24.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.model.Sort;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MovieControllerImpl implements FutureMovieController {
    @Autowired
    private ExecutorService executorService;

    @Autowired
    private MovieController movieController;

    @Override
    public Future<Void> addMovie(int id, String name, String genre, int rating) {
        Callable<Void> callable = () -> {
            movieController.addMovie(id, name, genre, rating);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> deleteMovie(int id) {
        Callable<Void> callable = () -> {
            movieController.deleteMovie(id);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Movie>> getMovies() {
        Callable<Iterable<Movie>> callable = () -> {
            return movieController.getMovies();
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Void> updateMovie(int id, String name, String genre, Integer rating) {
        Callable<Void> callable = () -> {
            movieController.updateMovie(id, name, genre, rating);
            return null;
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Movie>> filterMoviesByGenre(String genre) {
        Callable<Iterable<Movie>> callable = () -> {
            return movieController.filterMoviesByGenre(genre);
        };
        return executorService.submit(callable);
    }

    @Override
    public Future<Iterable<Movie>> sortMovies(Sort criteria) {
        Callable<Iterable<Movie>> callable = () -> {
            return movieController.sortMovies(criteria);
        };
        return executorService.submit(callable);
    }
}
