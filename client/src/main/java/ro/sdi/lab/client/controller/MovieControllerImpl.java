package ro.sdi.lab.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sdi.lab.common.controller.MovieController;
import ro.sdi.lab.common.model.Movie;
import ro.sdi.lab.common.model.Sort;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class MovieControllerImpl implements FutureMovieController {
    public static final Logger log = LoggerFactory.getLogger(MovieControllerImpl.class);

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
