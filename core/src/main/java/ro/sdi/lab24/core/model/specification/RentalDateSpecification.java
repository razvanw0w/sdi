package ro.sdi.lab24.core.model.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ro.sdi.lab24.core.model.Rental;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDateSpecification implements Specification<Rental> {
    private LocalDateTime dateTime;

    @Override
    public Predicate toPredicate(Root<Rental> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(root.<LocalDateTime>get("time"), dateTime);
    }
}
