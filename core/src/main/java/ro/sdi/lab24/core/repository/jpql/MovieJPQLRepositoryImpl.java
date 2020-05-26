package ro.sdi.lab24.core.repository.jpql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.repository.MovieCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("MovieJPQLRepositoryImpl")
public class MovieJPQLRepositoryImpl implements MovieCustomRepository {
    public static final Logger log = LoggerFactory.getLogger(MovieJPQLRepositoryImpl.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Movie> findByExactName(String name) {
        log.trace("got into jpql movie name filter");
        Query query = entityManager.createQuery(
                "select distinct m from Movie m where m.name = :name"
        );
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Movie> findByExactGenre(String genre) {
        log.trace("got into jpql movie genre filter");
        Query query = entityManager.createQuery(
                "select distinct m from Movie m where m.genre = :genre"
        );
        query.setParameter("genre", genre);
        return query.getResultList();
    }
}
