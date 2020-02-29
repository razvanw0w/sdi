package ro.sdi.lab24.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.sdi.lab24.exception.AlreadyExistingElementException;
import ro.sdi.lab24.exception.ElementNotFoundException;
import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.repository.InMemoryRepository;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.MovieValidator;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class MovieControllerTest {
    private Repository<Integer, Movie> repo;
    private MovieController controller;
    private MovieValidator validator;

    private long length(Iterable<Movie> list) {
        return StreamSupport.stream(list.spliterator(), false).count();
    }

    @BeforeEach
    void setUp() {
        validator = new MovieValidator();
        repo = new InMemoryRepository<Integer, Movie>(validator);
        controller = new MovieController(repo);
    }

    @Test
    void addMovie() {
        Movie movie, movieA, movieB, movie2;

        Iterable<Movie> list = controller.getMovies();
        assertEquals(0, length(list));

        controller.addMovie(1, "a");
        movieA = new Movie(1, "a");
        list = controller.getMovies();
        assertEquals(1, length(list));

        movie = list.iterator().next();
        assertEquals(movieA, movie);

        controller.addMovie(2, "b");
        movieB = new Movie(2, "b");
        list = controller.getMovies();
        assertEquals(2, length(list));

        Iterator<Movie> it = list.iterator();
        movie = it.next();
        movie2 = it.next();
        assertEquals(movie, movieA);
        assertEquals(movie2, movieB);

        assertThrows(AlreadyExistingElementException.class, () -> controller.addMovie(1, "c"));
    }

    @Test
    void deleteMovie() {
        Movie movie = new Movie(2, "b");
        controller.addMovie(1, "a");
        controller.addMovie(2, "b");
        assertEquals(length(controller.getMovies()), 2);

        controller.deleteMovie(1);
        assertEquals(length(controller.getMovies()), 1);

        assertThrows(ElementNotFoundException.class, () -> controller.deleteMovie(1));
        assertEquals(length(controller.getMovies()), 1);
        assertEquals(controller.getMovies().iterator().next(), movie);
    }

    @Test
    void getMovies() {
        controller.addMovie(1, "a");
        controller.addMovie(2, "b");
        assertEquals(length(controller.getMovies()), 2);

        controller.deleteMovie(1);
        assertEquals(length(controller.getMovies()), 1);

        controller.deleteMovie(2);
        assertEquals(length(controller.getMovies()), 0);
    }

    @Test
    void updateMovie() {
        controller.addMovie(1, "a");
        controller.addMovie(2, "b");

        Movie movie = new Movie(1, "c");

        controller.updateMovie(1, "c");
        assertEquals(controller.getMovies().iterator().next(), movie);
    }
}