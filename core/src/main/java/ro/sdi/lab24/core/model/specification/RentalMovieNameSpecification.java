package ro.sdi.lab24.core.model.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ro.sdi.lab24.core.model.Rental;
import ro.sdi.lab24.core.service.MovieService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalMovieNameSpecification implements Specification<Rental> {
    private MovieService movieService;

    @Override
    public Predicate toPredicate(Root<Rental> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
