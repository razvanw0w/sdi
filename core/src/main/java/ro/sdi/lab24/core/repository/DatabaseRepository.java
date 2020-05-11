package ro.sdi.lab24.core.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import ro.sdi.lab24.core.exception.ValidatorException;
import ro.sdi.lab24.core.model.Entity;
import ro.sdi.lab24.core.model.copyadapters.CopyAdapter;
import ro.sdi.lab24.core.model.serialization.database.TableAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class DatabaseRepository<ID extends Serializable, T extends Entity<ID>>
        implements SortingRepository<ID, T> {
    private TableAdapter<ID, T> tableAdapter;
    private CopyAdapter<T> copyAdapter;

    public DatabaseRepository(
            TableAdapter<ID, T> tableAdapter,
            CopyAdapter<T> copyAdapter
    ) {
        this.tableAdapter = tableAdapter;
        this.copyAdapter = copyAdapter;
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        Objects.requireNonNull(id, "Id must not be null");
        return tableAdapter.findById(id);
    }

    @Override
    public Iterable<T> findAll()
    {
        return getAll();
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        Objects.requireNonNull(entity, "Entity must not be null");
        Optional<T> readEntity = tableAdapter.findById(entity.getId());
        if (readEntity.isEmpty()) {
            tableAdapter.save(entity);
        }
        return readEntity;
    }

    @Override
    public Optional<T> delete(ID id) {
        Objects.requireNonNull(id, "Id must not be null");
        return tableAdapter.findById(id).map(
                readEntity ->
                {
                    tableAdapter.deleteById(id);
                    return readEntity;
                }
        );
    }

    @Override
    @Transactional
    public Optional<T> update(T entity) throws ValidatorException {
        Objects.requireNonNull(entity, "Entity must not be null");

        return tableAdapter.findById(entity.getId()).map(
                readEntity ->
                {
                    tableAdapter.findById(entity.getId())
                            .ifPresent(e -> copyAdapter.copy(entity, e));
                    return entity;
                }
        );
    }

    private List<T> getAll() {
        return tableAdapter.findAll();
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return tableAdapter.findAll(sort);
    }

    @Override
    public Iterable<T> findAll(Specification<T> specification) {
        return tableAdapter.findAll(specification);
    }
}
