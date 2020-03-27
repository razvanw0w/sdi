package ro.sdi.lab24.repository;

import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.Sort;

public interface SortingRepository<ID, T extends Entity<ID>> extends Repository<ID, T>
{
    Iterable<T> findAll(Sort sort);
}
