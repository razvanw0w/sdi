package ro.sdi.lab24.model.serialization.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.model.Entity;

public interface TableAdapter<ID, T extends Entity<ID>>
{
    void insert(T entity, Connection connection) throws SQLException;

    List<T> readAll(Connection connection) throws SQLException;

    Optional<T> read(ID id, Connection connection) throws SQLException;

    void update(T entity, Connection connection) throws SQLException;

    void delete(ID id, Connection connection) throws SQLException;
}
