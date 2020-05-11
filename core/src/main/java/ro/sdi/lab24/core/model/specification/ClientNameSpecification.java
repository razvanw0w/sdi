package ro.sdi.lab24.core.model.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ro.sdi.lab24.core.model.Client;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientNameSpecification implements Specification<Client> {
    private String namePattern;

    @Override
    public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.<String>get("name"), "%" + namePattern + "%");
    }
}
