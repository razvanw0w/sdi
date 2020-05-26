package ro.sdi.lab24.core.repository;

import ro.sdi.lab24.core.model.Movie;

import java.util.List;

public interface MovieCustomRepository {
    List<Movie> findByExactName(String name);

    List<Movie> findByExactGenre(String genre);
}
