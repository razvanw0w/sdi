package ro.sdi.lab24.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import ro.sdi.lab24.core.exception.AlreadyExistingElementException;
import ro.sdi.lab24.core.exception.ElementNotFoundException;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.service.MovieService;
import ro.sdi.lab24.web.converter.MovieConverter;
import ro.sdi.lab24.web.converter.SortConverter;
import ro.sdi.lab24.web.dto.MovieDTO;
import ro.sdi.lab24.web.dto.MoviesDTO;
import ro.sdi.lab24.web.dto.SortDTO;

@RestController
public class MovieRestController {
    private static final Logger log = LoggerFactory.getLogger(MovieRestController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private SortConverter sortConverter;

    @RequestMapping(value = "/movies", method = RequestMethod.GET)
    public MoviesDTO getMovies() {
        Iterable<Movie> movies = movieService.getMovies();
        log.trace("fetched movies: {}", movies);
        return new MoviesDTO(movieConverter.toDTOList(movies));
    }

    @RequestMapping(value = "/movies/page={page}&size={size}", method = RequestMethod.GET)
    public MoviesDTO getMoviesPaginated(@PathVariable int page, @PathVariable int size) {
        Iterable<Movie> movies = movieService.getMoviesPaginated(page, size);
        log.trace("fetched movies (page={} size={}): {}", page, size, movies);
        return new MoviesDTO(movieConverter.toDTOList(movies));
    }

    @RequestMapping(value = "/movies", method = RequestMethod.POST)
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO dto) {
        Movie movie = movieConverter.toModel(dto);
        try {
            movieService.addMovie(
                    movie.getId(),
                    movie.getName(),
                    movie.getGenre(),
                    movie.getRating()
            );
        } catch (AlreadyExistingElementException e) {
            log.trace("movie {} already exists", movie);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("movie {} added", movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody MovieDTO dto) {
        Movie movie = movieConverter.toModel(dto);
        try {
            movieService.updateMovie(
                    id,
                    movie.getName(),
                    movie.getGenre(),
                    movie.getRating()
            );
        } catch (ElementNotFoundException e) {
            log.trace("movie {} could not be updated", movie);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("movie id = {} updated: {}", id, movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        try {
            movieService.deleteMovie(id);
        } catch (ElementNotFoundException e) {
            log.trace("movie id = {} could not be deleted", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.trace("movie id = {} deleted", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/movies/filter/{genre}", method = RequestMethod.GET)
    public MoviesDTO filterMoviesByGenre(@PathVariable String genre) {
        log.trace("filtered movies by genre = {}", genre);
        return new MoviesDTO(movieConverter.toDTOList(movieService.filterMoviesByGenre(genre)));
    }

    @RequestMapping(value = "/movies/filter-paginated/{genre}&page={page}&size={size}", method = RequestMethod.GET)
    public MoviesDTO filterMoviesByGenrePaginated(@PathVariable String genre, @PathVariable int page, @PathVariable int size) {
        log.trace("filtered movies by genre = {} page = {} size = {}", genre, page, size);
        return new MoviesDTO(movieConverter.toDTOList(movieService.filterMoviesByGenrePaginated(genre, page, size)));
    }

    @RequestMapping(value = "/movies/sort", method = RequestMethod.POST)
    public MoviesDTO sortMovies(@RequestBody SortDTO dto) {
        log.trace("sorting movies by criteria = {}", dto);
        Iterable<Movie> movies = movieService.sortMovies(sortConverter.toModel(dto));
        return new MoviesDTO(movieConverter.toDTOList(movies));
    }

    @RequestMapping(value = "/movies/sort&page={page}&size={size}", method = RequestMethod.POST)
    public MoviesDTO sortMoviesPaginated(@RequestBody SortDTO dto, @PathVariable int page, @PathVariable int size) {
        log.trace("sorting movies by criteria = {} page = {} size = {}", dto, page, size);
        Iterable<Movie> movies = movieService.sortMoviesPaginated(sortConverter.toModel(dto), page, size);
        return new MoviesDTO(movieConverter.toDTOList(movies));
    }
}
