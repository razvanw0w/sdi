package ro.sdi.lab24.core.model.serialization.database;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sdi.lab24.core.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieTableAdapter extends TableAdapter<Integer, Movie> {
    @Query("select distinct movie from Movie movie")
    @EntityGraph(value = "movieWithRentalsAndClients", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAllMoviesWithRentals();

    @Query("select distinct movie from Movie movie where movie.id=:movieID")
    @EntityGraph(value = "movieWithRentalsAndClients", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Movie> findByIdWithRentals(@Param("movieID") Integer movieID);
}
