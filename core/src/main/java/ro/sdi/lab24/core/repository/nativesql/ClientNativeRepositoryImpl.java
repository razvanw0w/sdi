package ro.sdi.lab24.core.repository.nativesql;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.core.repository.ClientCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("ClientNativeRepositoryImpl")
public class ClientNativeRepositoryImpl implements ClientCustomRepository {
    public static final Logger log = LoggerFactory.getLogger(ClientNativeRepositoryImpl.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<Client> findByExactName(@Param("name") String name) {
        log.trace("got into nativesql client name filter");
        Session hibernateEntityManager = entityManager.unwrap(Session.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery(
                "select distinct {c.*} from client c where c.name = :name")
                .addEntity("c", Client.class)
                .setParameter("name", name)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Client> findByExactFidelity(Integer fidelity) {
        log.trace("got into nativesql client fidelity filter");
        Session hibernateEntityManager = entityManager.unwrap(Session.class);
        Session session = hibernateEntityManager.getSession();
        org.hibernate.Query query = session.createSQLQuery(
                "select distinct {c.*} from client c where c.fidelity = :fidelity")
                .addEntity("c", Client.class)
                .setParameter("fidelity", fidelity)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return query.getResultList();
    }
}
