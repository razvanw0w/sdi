package ro.sdi.lab24.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.model.specification.MovieGenreSpecification;
import ro.sdi.lab24.core.repository.MovieRepository;
import ro.sdi.lab24.core.validation.Validator;

import java.util.Optional;

@Service
public class MovieService {
    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    Validator<Movie> movieValidator;

    EntityDeletedListener<Movie> entityDeletedListener = null;

    public void setEntityDeletedListener(EntityDeletedListener<Movie> entityDeletedListener) {
        this.entityDeletedListener = entityDeletedListener;
    }

    /**
     * This function adds a movie to the repository
     *
     * @param name: the name of the movie
     * @throws AlreadyExistingElementException if the movie (the ID) is already there
     */
    public void addMovie(String name, String genre, int rating) {
        Movie movie = Movie.builder().name(name).genre(genre).rating(rating).build();
        movieValidator.validate(movie);
        log.trace("Adding movie {}", movie);
        movieRepository.save(movie);
    }

    /**
     * This function removes a movie from the repository based on their ID
     *
     * @param id: the ID of the movie
     * @throws ElementNotFoundException if the movie isn't found in the repository based on their ID
     */
    public void deleteMovie(int id) {
        log.trace("Deleting movie with id {}", id);
        movieRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(String.format(
                "Client %d does not exist",
                id
        )));
        movieRepository.deleteById(id);
    }

    /**
     * This function returns an iterable collection of the current state of the movies in the repository
     *
     * @return all: an iterable collection of movies
     */
    public Iterable<Movie> getMovies() {
        log.trace("Fetching all movies");
        return movieRepository.findAll();
    }

    public Page<Movie> getMoviesPaginated(int page, int size) {
        log.trace("Fetching all movies page = {} size = {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository.findAll(pageable);
    }

    /**
     * This function updated a movie based on their ID with a new name
     *
     * @param id     : the movie's ID
     * @param name   : the new name of the movie
     * @param genre  : the genre of the movie
     * @param rating : the rating of the movie
     * @throws ElementNotFoundException if the movie isn't found in the repository based on their ID
     */
    public void updateMovie(
            int id,
            String name,
            String genre,
            Integer rating
    ) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Movie %d does not exist",
                        id
                )));
        movie.setName(Optional.ofNullable(name).orElseGet(movie::getName));
        movie.setGenre(Optional.ofNullable(genre).orElseGet(movie::getGenre));
        movie.setRating(Optional.ofNullable(rating).orElseGet(movie::getRating));
        movieValidator.validate(movie);
        log.trace("Updating movie {}", movie);
        movieRepository.save(movie);
    }

    public Iterable<Movie> filterMoviesByGenre(String genre) {
        log.trace("Filtering movies by the genre {}", genre);
        /*String regex = ".*" + genre + ".*";
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
                .filter(movie -> movie.getGenre().matches(regex))
                .collect(Collectors.toUnmodifiableList());*/
        Specification<Movie> specification = new MovieGenreSpecification(genre);
        return movieRepository.findAll(specification);
    }

    public Optional<Movie> findOne(int movieId) {
        return movieRepository.findById(movieId);
    }

    public Iterable<Movie> sortMovies(Sort sort) {
        log.trace("Sorting movies");
        return movieRepository.findAll(sort);
    }

    public Iterable<Movie> sortMoviesPaginated(Sort sort, int page, int size) {
        log.trace("Sorting movies");
        Pageable pageable = PageRequest.of(page, size, sort);
        return movieRepository.findAll(pageable);
    }

    public Page<Movie> filterMoviesByGenrePaginated(String genre, int page, int size) {
        Specification<Movie> specification = new MovieGenreSpecification(genre);
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository.findAll(specification, pageable);
    }
}
