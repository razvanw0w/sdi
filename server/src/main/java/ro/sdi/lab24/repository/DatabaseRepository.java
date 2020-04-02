package ro.sdi.lab24.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.Sort;
import ro.sdi.lab24.model.serialization.database.TableAdapter;
import ro.sdi.lab24.sorting.SortingUtils;

public class DatabaseRepository<ID, T extends Entity<ID>> implements SortingRepository<ID, T>
{
    private TableAdapter<ID, T> tableAdapter;

    public DatabaseRepository(
            TableAdapter<ID, T> tableAdapter
    )
    {
        this.tableAdapter = tableAdapter;
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        Objects.requireNonNull(id, "Id must not be null");
        return tableAdapter.read(id);
    }

    @Override
    public Iterable<T> findAll()
    {
        return getAll();
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "Entity must not be null");
        Optional<T> readEntity = tableAdapter.read(entity.getId());
        if (readEntity.isEmpty())
        {
            tableAdapter.insert(entity);
        }
        return readEntity;
    }

    @Override
    public Optional<T> delete(ID id)
    {
        Objects.requireNonNull(id, "Id must not be null");
        return tableAdapter.read(id).map(
                readEntity ->
                {
                    tableAdapter.delete(id);
                    return readEntity;
                }
        );
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "Entity must not be null");

        return tableAdapter.read(entity.getId()).map(
                readEntity ->
                {
                    tableAdapter.update(entity);
                    return entity;
                }
        );

    }

    private List<T> getAll()
    {
        return tableAdapter.readAll();
    }

    @Override
    public Iterable<T> findAll(Sort sort)
    {
        return SortingUtils.sort(getAll(), sort);
    }
}
