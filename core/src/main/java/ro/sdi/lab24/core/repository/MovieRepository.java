package ro.sdi.lab24.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Movie;

import java.util.List;
import java.util.Optional;

@Component("MovieNativeRepository")
public interface MovieRepository extends Repository<Integer, Movie>, MovieCustomRepository {
    @Query("select distinct movie from Movie movie")
    @EntityGraph(value = "movieWithRentalsAndClients", type = EntityGraph.EntityGraphType.LOAD)
    List<Movie> findAllMoviesWithRentals();

    @Query("select distinct movie from Movie movie where movie.id=:movieID")
    @EntityGraph(value = "movieWithRentalsAndClients", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Movie> findByIdWithRentals(@Param("movieID") Integer movieID);
}
