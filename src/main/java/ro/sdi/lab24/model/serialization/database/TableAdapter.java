package ro.sdi.lab24.model.serialization.database;

import java.sql.Connection;
import java.util.List;

public interface TableAdapter<T>
{
    void insert(T entity, Connection connection);

    List<T> read(Connection connection);

    void update(T entity, Connection connection);

    void delete(T entity, Connection connection);
}
