package ro.sdi.lab24.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import ro.sdi.lab24.exception.DatabaseException;
import ro.sdi.lab24.exception.ValidatorException;
import ro.sdi.lab24.model.Entity;
import ro.sdi.lab24.model.serialization.database.TableAdapter;
import ro.sdi.lab24.model.Sort;
import ro.sdi.lab24.sorting.SortingUtils;

public class DatabaseRepository<ID, T extends Entity<ID>> implements SortingRepository<ID, T>
{
    private Supplier<Connection> connectionSupplier;
    private TableAdapter<ID, T> tableAdapter;

    public DatabaseRepository(
            Supplier<Connection> connectionSupplier,
            TableAdapter<ID, T> tableAdapter
    )
    {
        this.connectionSupplier = connectionSupplier;
        this.tableAdapter = tableAdapter;
    }

    @Override
    public Optional<T> findOne(ID id)
    {
        Objects.requireNonNull(id, "Id must not be null");
        try (Connection connection = connectionSupplier.get())
        {
            return tableAdapter.read(id, connection);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Database connection error");
        }
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
        try (Connection connection = connectionSupplier.get())
        {
            Optional<T> readEntity = tableAdapter.read(entity.getId(), connection);
            if (readEntity.isEmpty())
            {
                tableAdapter.insert(entity, connection);
            }
            return readEntity;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Database connection error");
        }
    }

    @Override
    public Optional<T> delete(ID id)
    {
        Objects.requireNonNull(id, "Id must not be null");
        try (Connection connection = connectionSupplier.get())
        {
            return tableAdapter.read(id, connection).map(
                    readEntity ->
                    {
                        try
                        {
                            tableAdapter.delete(id, connection);
                        }
                        catch (SQLException e)
                        {
                            throw new DatabaseException("Database connection error");
                        }
                        return readEntity;
                    }
            );
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Database connection error");
        }
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException
    {
        Objects.requireNonNull(entity, "Entity must not be null");
        try (Connection connection = connectionSupplier.get())
        {
            return tableAdapter.read(entity.getId(), connection).map(
                    readEntity ->
                    {
                        try
                        {
                            tableAdapter.update(entity, connection);
                        }
                        catch (SQLException e)
                        {
                            throw new DatabaseException("Database connection error");
                        }
                        return entity;
                    }
            );
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Database connection error");
        }
    }

    private List<T> getAll()
    {
        try (Connection connection = connectionSupplier.get())
        {
            return tableAdapter.readAll(connection);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Database connection error");
        }
    }

    @Override
    public Iterable<T> findAll(Sort sort)
    {
        return SortingUtils.sort(getAll(), sort);
    }
}
