package ro.sdi.lab24.repository;

import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.sorting.Sort;

import java.io.Serializable;

public interface SortingRepository<ID extends Serializable, T extends Entity<ID>> extends Repository<ID, T> {
    Iterable<T> findAll(Sort sort);
}
