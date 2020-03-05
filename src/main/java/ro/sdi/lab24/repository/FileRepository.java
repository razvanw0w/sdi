package ro.sdi.lab24.repository;

import java.util.Optional;

import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.serialization.CSVSerializer;

public class FileRepository<ID, T extends Entity<ID>> implements Repository<ID, T>
{
    private CSVSerializer<T> serializer;

    public FileRepository(CSVSerializer<T> serializer)
    {
        this.serializer = serializer;
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        return Optional.empty();//TODO Razvan
    }

    @Override
    public Iterable<T> findAll()
    {
        return null;//TODO Razvan
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
        return Optional.empty();//TODO Razvan
    }

    @Override
    public Optional<T> delete(ID id)
    {
        return Optional.empty();//TODO Razvan
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        return Optional.empty();//TODO Razvan
    }
}
