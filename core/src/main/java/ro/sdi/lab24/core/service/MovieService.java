package ro.sdi.lab24.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.exception.SortingException;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.repository.Repository;
import ro.sdi.lab24.core.repository.SortingRepository;
import ro.sdi.lab24.core.validation.Validator;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MovieService {
    private static final Logger log = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    Repository<Integer, Movie> movieRepository;

    @Autowired
    Validator<Movie> movieValidator;

    EntityDeletedListener<Movie> entityDeletedListener = null;

    public void setEntityDeletedListener(EntityDeletedListener<Movie> entityDeletedListener) {
        this.entityDeletedListener = entityDeletedListener;
    }

    /**
     * This function adds a movie to the repository
     *
     * @param id:   the ID of the movie
     * @param name: the name of the movie
     * @throws AlreadyExistingElementException if the movie (the ID) is already there
     */
    public void addMovie(int id, String name, String genre, int rating) {
        Movie movie = new Movie(id, name, genre, rating);
        movieValidator.validate(movie);
        log.trace("Adding movie {}", movie);
        movieRepository.save(movie).ifPresent(opt ->
        {
            throw new AlreadyExistingElementException(String.format(
                    "Movie %d already exists",
                    id
            ));
        });
    }

    /**
     * This function removes a movie from the repository based on their ID
     *
     * @param id: the ID of the movie
     * @throws ElementNotFoundException if the movie isn't found in the repository based on their ID
     */
    public void deleteMovie(int id) {
        log.trace("Deleting movie with id {}", id);
        movieRepository.delete(id)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Movie %d does not exist",
                        id
                )));
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
        Movie storedMovie = movieRepository.findOne(id)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Movie %d does not exist",
                        id
                )));
        Movie movie = new Movie(id, Optional.ofNullable(name).orElseGet(storedMovie::getName),
                Optional.ofNullable(genre).orElseGet(storedMovie::getGenre),
                Optional.ofNullable(rating).orElseGet(storedMovie::getRating)
        );
        movieValidator.validate(movie);
        log.trace("Updating movie {}", movie);
        movieRepository.update(movie)
                .orElseThrow(() -> new ElementNotFoundException(String.format(
                        "Movie %d does not exist",
                        id
                )));
    }

    public Iterable<Movie> filterMoviesByGenre(String genre) {
        log.trace("Filtering movies by the genre {}", genre);
        String regex = ".*" + genre + ".*";
        return StreamSupport.stream(movieRepository.findAll().spliterator(), false)
                .filter(movie -> movie.getGenre().matches(regex))
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Movie> findOne(int movieId) {
        return movieRepository.findOne(movieId);
    }

    public Iterable<Movie> sortMovies(Sort sort) {
        log.trace("Sorting movies");
        SortingRepository<Integer, Movie> sortingRepo = Optional.of(movieRepository)
                .filter(repo -> repo instanceof SortingRepository)
                .map(repo -> (SortingRepository<Integer, Movie>) repo)
                .orElseThrow(() -> new SortingException("repository doesn't support sorting"));
        return sortingRepo.findAll(sort);
    }
}
