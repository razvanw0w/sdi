package ro.sdi.lab24.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.validation.Validator;
import ro.sdi.lab24.validation.ValidatorException;

public class InMemoryRepository<ID, T extends Entity<ID>> implements Repository<ID, T>
{

    private Map<ID, T> entities;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> validator)
    {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll()
    {
        return Collections.unmodifiableCollection(entities.values());
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
        if (entity == null)
        {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        if (entity == null)
        {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
