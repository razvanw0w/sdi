package ro.sdi.lab24.core.repository.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import ro.sdi.lab24.core.model.Client;
import ro.sdi.lab24.core.repository.ClientCustomRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("ClientCriteriaRepositoryImpl")
public class ClientCriteriaRepositoryImpl implements ClientCustomRepository {
    private static final Logger log = LoggerFactory.getLogger(ClientCriteriaRepositoryImpl.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Client> findByExactName(@Param("name") String name) {
        log.trace("got into criteria api client name filter");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);

        Root<Client> clientRoot = criteriaQuery.from(Client.class);
        ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.where(criteriaBuilder.equal(clientRoot.get("name"), parameter));

        TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, name);
        return query.getResultList();
    }

    @Override
    public List<Client> findByExactFidelity(@Param("fidelity") Integer fidelity) {
        log.trace("got into criteria api client fidelity filter");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);

        Root<Client> clientRoot = criteriaQuery.from(Client.class);
        ParameterExpression<Integer> parameter = criteriaBuilder.parameter(Integer.class);
        criteriaQuery.where(criteriaBuilder.equal(clientRoot.get("fidelity"), parameter));

        TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(parameter, fidelity);
        return query.getResultList();
    }
}
