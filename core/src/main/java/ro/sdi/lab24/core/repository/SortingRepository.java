package ro.sdi.lab24.core.repository;

import org.springframework.data.domain.Sort;
import ro.sdi.lab24.core.model.Entity;

import java.io.Serializable;

public interface SortingRepository<ID extends Serializable, T extends Entity<ID>> extends Repository<ID, T> {
    Iterable<T> findAll(Sort sort);
}
