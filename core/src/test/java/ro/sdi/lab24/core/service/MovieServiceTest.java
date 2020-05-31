package ro.sdi.lab24.core.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import ro.sdi.lab24.core.ITConfig;
import ro.sdi.lab24.core.model.Movie;

import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-movies.xml")
public class MovieServiceTest {
    @Autowired
    private MovieService movieService;

    @Test
    public void getMovies() throws Exception {
        Iterable<Movie> movies = movieService.getMovies();
        long size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(4, size);
    }

    @Test
    public void addMovie() throws Exception {
        Iterable<Movie> movies = movieService.getMovies();
        long size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(4, size);

        movieService.addMovie("Movie10", "genreThree", 70);
        movies = movieService.getMovies();
        size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(size, 5);
    }

    @Test
    public void deleteMovie() throws Exception {
        Iterable<Movie> movies = movieService.getMovies();
        long size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(4, size);

        movieService.deleteMovie(1);
        movies = movieService.getMovies();
        size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(size, 3);
    }

    @Test
    public void updateMovie() throws Exception {
        Iterable<Movie> movies = movieService.getMovies();
        long size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(4, size);

        movieService.updateMovie(1, "MovieUpdate", "genreUpdate", 100);
        movies = movieService.getMovies();
        size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(4, size);

        Movie newMovie = movies.iterator().next();
        assertEquals("MovieUpdate", newMovie.getName());
        assertEquals("genreUpdate", newMovie.getGenre());
        assertEquals(100, newMovie.getRating());
    }

    @Test
    public void filterMoviesByName() throws Exception {
        Iterable<Movie> movies = movieService.filterMoviesByName("Movie1");
        long size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(1, size);

        movies = movieService.filterMoviesByName("inexistent");
        size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(0, size);
    }

    @Test
    public void filterMoviesByGenre() throws Exception {
        Iterable<Movie> movies = movieService.filterMoviesByGenre("genreOne");
        long size = StreamSupport.stream(movies.spliterator(), false).count();
        assertEquals(2, size);
    }
}
