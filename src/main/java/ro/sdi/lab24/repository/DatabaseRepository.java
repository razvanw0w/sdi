package ro.sdi.lab24.repository;

import java.sql.Connection;
import java.util.Optional;
import java.util.function.Supplier;

import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.serialization.database.TableAdapter;
import ro.sdi.lab24.sorting.Sort;

public class DatabaseRepository<ID, T extends Entity<ID>> implements SortingRepository<ID, T>
{
    private Supplier<Connection> connectionSupplier;
    private TableAdapter<T> tableAdapter;

    public DatabaseRepository(Supplier<Connection> connectionSupplier, TableAdapter<T> tableAdapter)
    {
        this.connectionSupplier = connectionSupplier;
        this.tableAdapter = tableAdapter;
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        return Optional.empty();//TODO
    }

    @Override
    public Iterable<T> findAll()
    {
        return null;//TODO
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException
    {
        return Optional.empty();//TODO
    }

    @Override
    public Optional<T> delete(ID id)
    {
        return Optional.empty();//TODO
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        return Optional.empty();//TODO
    }

    @Override
    public Iterable<T> findAll(Sort sort)
    {
        return null;
    }
}
