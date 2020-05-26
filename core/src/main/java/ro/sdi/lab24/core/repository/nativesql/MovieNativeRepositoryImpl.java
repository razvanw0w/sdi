package ro.sdi.lab24.core.repository.nativesql;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.sdi.lab24.core.model.Movie;
import ro.sdi.lab24.core.repository.MovieCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("MovieNativeRepositoryImpl")
public class MovieNativeRepositoryImpl implements MovieCustomRepository {
    public static final Logger log = LoggerFactory.getLogger(MovieNativeRepositoryImpl.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<Movie> findByExactName(String name) {
        log.trace("got into nativesql movie name filter");
        Session hibernateEntityManager = entityManager.unwrap(Session.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery("select distinct {m.*} from movie m where m.name = :name")
                .addEntity("m", Movie.class)
                .setParameter("name", name)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Movie> findByExactGenre(String genre) {
        log.trace("got into nativesql movie genre filter");
        Session hibernateEntityManager = entityManager.unwrap(Session.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery("select distinct {m.*} from movie m where m.genre = :genre")
                .addEntity("m", Movie.class)
                .setParameter("genre", genre)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }
}
