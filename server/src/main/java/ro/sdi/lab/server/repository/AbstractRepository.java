package ro.sdi.lab.server.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import ro.sdi.lab.common.exception.ValidatorException;
import ro.sdi.lab.common.model.Entity;

public abstract class AbstractRepository<ID, T extends Entity<ID>> implements Repository<ID, T>
{
    protected Map<ID, T> entities;

    public AbstractRepository()
    {
        this.entities = new HashMap<>();
    }

    protected abstract void loadPersistence();

    protected abstract void updatePersistence();

    @Override
    public Optional<T> findOne(ID id)
    {
        Objects.requireNonNull(id, "id must not be null");
        loadPersistence();
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll()
    {
        loadPersistence();
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "entity must not be null");
        loadPersistence();
        Optional<T> returnedEntity = Optional.ofNullable(entities.putIfAbsent(
                entity.getId(),
                entity
        ));
        if (returnedEntity.isEmpty())
        {
            updatePersistence();
        }
        return returnedEntity;
    }

    @Override
    public Optional<T> delete(ID id)
    {
        Objects.requireNonNull(id, "id must not be null");
        loadPersistence();
        Optional<T> returnedEntity = Optional.ofNullable(entities.remove(id));
        returnedEntity.ifPresent(t -> updatePersistence());
        return returnedEntity;
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "entity must not be null");
        loadPersistence();
        Optional<T> returnedEntity = Optional.ofNullable(entities.computeIfPresent(
                entity.getId(),
                (k, v) -> entity
        ));
        returnedEntity.ifPresent(t -> updatePersistence());
        return returnedEntity;
    }
}
