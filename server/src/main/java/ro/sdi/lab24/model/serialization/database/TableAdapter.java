package ro.sdi.lab24.model.serialization.database;

import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.exception.DatabaseException;
import ro.sdi.lab24.model.Entity;

public interface TableAdapter<ID, T extends Entity<ID>>
{
    void insert(T entity) throws DatabaseException;

    List<T> readAll() throws DatabaseException;

    Optional<T> read(ID id) throws DatabaseException;

    void update(T entity) throws DatabaseException;

    void delete(ID id) throws DatabaseException;

    default <E extends RuntimeException, R> R handleConnectionException(
            Class<E> exceptionClass,
            Callable<R, E> callable
    )
    {
        try
        {
            return callable.call();
        }
        catch (RuntimeException e)
        {
            if (exceptionClass.isInstance(e))
            {
                throw new DatabaseException("Database connection error");
            }
            throw e;
        }
    }

    interface Callable<R, E extends RuntimeException>
    {
        R call() throws E;
    }
}
