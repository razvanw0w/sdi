package ro.sdi.lab24.core.repository.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.repository.MovieCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("MovieCriteriaRepositoryImpl")
public class MovieCriteriaRepositoryImpl implements MovieCustomRepository {
    private static final Logger log = LoggerFactory.getLogger(MovieCriteriaRepositoryImpl.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Movie> findByExactName(String name) {
        log.trace("got into criteria api movie name filter");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);

        Root<Movie> movieRoot = criteriaQuery.from(Movie.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(movieRoot.get("name"), parameter));

        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, name);
        return query.getResultList();
    }

    @Override
    public List<Movie> findByExactGenre(String genre) {
        log.trace("got into criteria api movie genre filter");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);

        Root<Movie> movieRoot = criteriaQuery.from(Movie.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(movieRoot.get("genre"), parameter));

        TypedQuery<Movie> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, genre);
        return query.getResultList();
    }
}
