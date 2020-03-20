package ro.sdi.lab24.model.serialization.database;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.model.Entity;

public interface TableAdapter<ID, T extends Entity<ID>>
{
    void insert(T entity, Connection connection);

    List<T> readAll(Connection connection);

    Optional<T> read(ID id, Connection connection);

    void update(T entity, Connection connection);

    void delete(ID id, Connection connection);
}
