package ro.sdi.lab24.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.sorting.Sort;
import ro.sdi.lab24.web.converter.MovieConverter;
import ro.sdi.lab24.web.converter.SortConverter;
import ro.sdi.lab24.web.dto.MoviesDTO;
import ro.sdi.lab24.web.dto.SortDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieRestTemplateController {
    public static final String url = "http://localhost:8080/api/movies";
    private static final Logger log = LoggerFactory.getLogger(MovieRestTemplateController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private SortConverter sortConverter;

    public void addMovie(int id, String name, String genre, int rating) {
        Movie movie = new Movie(id, name, genre, rating);
        try {
            restTemplate.postForEntity(url, movie, Object.class);
            log.trace("movie {} added", movie);
        } catch (RestClientException e) {
            log.trace("movie {} already exists", movie);
            throw new AlreadyExistingElementException("Movie already exists!");
        }
    }

    public void deleteMovie(int id) {
        try {
            restTemplate.delete(url + "/" + id);
            log.trace("movie id = {} deleted", id);
        } catch (RestClientException e) {
            log.trace("movie id = {} was not deleted", id);
            throw new ElementNotFoundException("Movie does not exist!");
        }
    }

    public Iterable<Movie> getMovies() {
        MoviesDTO dto = restTemplate.getForObject(url, MoviesDTO.class);
        assert dto != null;
        List<Movie> movies = dto.getMovies()
                .stream()
                .map(movieConverter::toModel)
                .collect(Collectors.toUnmodifiableList());
        log.trace("fetched movies: {}", movies);
        return movies;
    }

    public void updateMovie(int id, String name, String genre, Integer rating) {
        Movie movie = new Movie(id, name, genre, rating);
        try {
            restTemplate.put(url + "/" + id, movie);
            log.trace("movie id = {} updated: {}", id, movie);
        } catch (RestClientException e) {
            log.trace("movie id = {} was not updated", id);
            throw new ElementNotFoundException("Movie not found!");
        }
    }

    public Iterable<Movie> filterMoviesByGenre(String genre) {
        MoviesDTO dto = restTemplate.getForObject(url + "/filter/" + genre, MoviesDTO.class);
        assert dto != null;
        List<Movie> movies = dto.getMovies()
                .stream()
                .map(movieConverter::toModel)
                .collect(Collectors.toList());
        log.trace("filtered movies by genre = {}: {}", genre, movies);
        return movies;
    }

    public Iterable<Movie> sortMovies(Sort criteria) {
        SortDTO sort = sortConverter.toDTO(criteria);
        MoviesDTO dto = restTemplate.postForObject(url + "/sort", sort, MoviesDTO.class);
        assert dto != null;
        Iterable<Movie> movies = dto.getMovies()
                .stream()
                .map(movieConverter::toModel)
                .collect(Collectors.toUnmodifiableList());
        log.trace("sorted movies by criteria = {}: {}", criteria, movies);
        return movies;
    }
}
