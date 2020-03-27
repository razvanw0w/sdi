package ro.sdi.lab24.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import ro.sdi.lab24.exception.AlreadyExistingElementException;
import ro.sdi.lab24.exception.ElementNotFoundException;
import ro.sdi.lab24.model.Movie;
import ro.sdi.lab24.repository.MemoryRepository;
import ro.sdi.lab24.repository.Repository;
import ro.sdi.lab24.validation.MovieValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovieControllerTest
{
    private Repository<Integer, Movie> repo;
    private MovieController controller;
    private MovieValidator validator;

    private long length(Iterable<Movie> list)
    {
        return StreamSupport.stream(list.spliterator(), false).count();
    }

    @BeforeEach
    void setUp()
    {
        validator = new MovieValidator();
        repo = new MemoryRepository<Integer, Movie>();
        controller = new MovieControllerImpl(repo, validator);
    }

    @Test
    void addMovie()
    {
        Movie movie, movieA, movieB, movie2;

        Iterable<Movie> list = controller.getMovies();
        assertEquals(0, length(list));

        controller.addMovie(1, "a", "g1", 100);
        movieA = new Movie(1, "a", "g1", 100);
        list = controller.getMovies();
        assertEquals(1, length(list));

        movie = list.iterator().next();
        assertEquals(movieA, movie);

        controller.addMovie(2, "b", "g2", 75);
        movieB = new Movie(2, "b", "g2", 75);
        list = controller.getMovies();
        assertEquals(2, length(list));

        Iterator<Movie> it = list.iterator();
        movie = it.next();
        movie2 = it.next();
        assertEquals(movie, movieA);
        assertEquals(movie2, movieB);

        assertThrows(
                AlreadyExistingElementException.class,
                () -> controller.addMovie(1, "c", "g3", 2)
        );
    }

    @Test
    void deleteMovie()
    {
        Movie movie = new Movie(2, "b", "g1", 100);
        controller.addMovie(1, "a", "g1", 75);
        controller.addMovie(2, "b", "g1", 100);
        assertEquals(length(controller.getMovies()), 2);

        controller.deleteMovie(1);
        assertEquals(length(controller.getMovies()), 1);

        assertThrows(ElementNotFoundException.class, () -> controller.deleteMovie(1));
        assertEquals(length(controller.getMovies()), 1);
        assertEquals(controller.getMovies().iterator().next(), movie);
    }

    @Test
    void getMovies()
    {
        controller.addMovie(1, "a", "g1", 75);
        controller.addMovie(2, "b", "g1", 100);
        assertEquals(length(controller.getMovies()), 2);

        controller.deleteMovie(1);
        assertEquals(length(controller.getMovies()), 1);

        controller.deleteMovie(2);
        assertEquals(length(controller.getMovies()), 0);
    }

    @Test
    void updateMovie()
    {
        controller.addMovie(1, "a", "g1", 75);
        controller.addMovie(2, "b", "g1", 100);

        Movie movie = new Movie(1, "c", "g1", 75);
        Movie movie2 = new Movie(1, "d", "g2", 75);
        Movie movie3 = new Movie(1, "e", "g3", 100);

        controller.updateMovie(1, "c", null, null);
        assertEquals(controller.getMovies().iterator().next(), movie);
        controller.updateMovie(1, "d", "g2", null);
        assertEquals(controller.getMovies().iterator().next(), movie2);
        controller.updateMovie(1, "e", "g3", 100);
        assertEquals(controller.getMovies().iterator().next(), movie3);
    }
}