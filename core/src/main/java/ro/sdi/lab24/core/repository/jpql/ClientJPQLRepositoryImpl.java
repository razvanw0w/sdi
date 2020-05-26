package ro.sdi.lab24.core.repository.jpql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.core.repository.ClientCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("ClientJPQLRepositoryImpl")
public class ClientJPQLRepositoryImpl implements ClientCustomRepository {
    private static final Logger log = LoggerFactory.getLogger(ClientJPQLRepositoryImpl.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Client> findByExactName(@Param("name") String name) {
        log.trace("got into jpql client name filter");
        Query query = entityManager.createQuery(
                "select distinct c from Client c where c.name = :name"
        );
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Client> findByExactFidelity(Integer fidelity) {
        log.trace("got into jpql client fidelity filter");
        Query query = entityManager.createQuery(
                "select distinct c from Client c where c.fidelity = :fidelity"
        );
        query.setParameter("fidelity", fidelity);
        return query.getResultList();
    }
}
