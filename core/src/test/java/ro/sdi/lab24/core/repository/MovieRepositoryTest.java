package ro.sdi.lab24.core.repository;

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
import org.springframework.transaction.annotation.Transactional;
import ro.sdi.lab24.core.ITConfig;
import ro.sdi.lab24.core.model.Movie;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ITConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/META-INF/dbtest/db-data-movies.xml")
public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    @Test
    public void findAll() throws Exception {
        List<Movie> all = movieRepository.findAll();
        assertEquals(4, all.size());
    }

    @Test
    public void add() throws Exception {
        Movie movie = Movie.builder()
                .name("Mock")
                .genre("mockgenre")
                .rating(100)
                .build();
        movie.setId(-1);
        movieRepository.save(movie);
        List<Movie> all = movieRepository.findAll();
        assertEquals(5, all.size());
    }

    @Test
    public void delete() throws Exception {
        movieRepository.deleteById(1);
        List<Movie> all = movieRepository.findAll();
        assertEquals(3, all.size());
    }

    @Test
    @Transactional
    public void update() throws Exception {
        Movie movie = movieRepository.findById(1).orElseThrow(RuntimeException::new);
        movie.setName("updated");
        List<Movie> all = movieRepository.findAll();
        assertEquals("updated", all.get(0).getName());
    }
}
