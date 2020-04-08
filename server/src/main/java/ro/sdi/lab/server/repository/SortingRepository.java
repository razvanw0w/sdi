package ro.sdi.lab.server.repository;

import ro.sdi.lab.common.model.Entity;
import ro.sdi.lab.common.model.Sort;

public interface SortingRepository<ID, T extends Entity<ID>> extends Repository<ID, T>
{
    Iterable<T> findAll(Sort sort);
}
