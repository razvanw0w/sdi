package ro.sdi.lab24.model.serialization.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.exception.DatabaseException;
import ro.sdi.lab24.model.Client;

public class ClientTableAdapter implements TableAdapter<Integer, Client>
{
    @Autowired
    private JdbcOperations jdbcOperations;

    private RowMapper<Client> clientRowMapper = (resultSet, rowNum) ->
            new Client(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );

    @Override
    public void insert(Client client) throws DatabaseException
    {
        handleConnectionException(DataAccessException.class, () ->
        {
            String query = "insert into clients values(?,?)";
            jdbcOperations.update(query, client.getId(), client.getName());
            return null;
        });
    }

    @Override
    public List<Client> readAll() throws DatabaseException
    {
        return handleConnectionException(DataAccessException.class, () ->
        {
            List<Client> result = new ArrayList<>();
            String query = "SELECT * FROM clients";
            return jdbcOperations.query(query, clientRowMapper);
        });
    }

    @Override
    public Optional<Client> read(Integer id) throws DatabaseException
    {
        return handleConnectionException(DataAccessException.class, () ->
        {
            String query = "SELECT * FROM clients WHERE id = ?";
            List<Client> clientList = jdbcOperations.query(
                    query,
                    new Object[]{id},
                    clientRowMapper
            );

            if (clientList.size() != 1) return Optional.empty();
            return Optional.of(clientList.get(0));
        });
    }

    @Override
    public void update(Client client) throws DatabaseException
    {
        handleConnectionException(DataAccessException.class, () ->
        {
            String query = "UPDATE clients SET name = ? WHERE id = ?";
            jdbcOperations.update(query, client.getName(), client.getId());
            return null;
        });
    }

    @Override
    public void delete(Integer id) throws DatabaseException
    {
        handleConnectionException(DataAccessException.class, () ->
        {
            String query = "DELETE FROM clients WHERE id = ?";
            jdbcOperations.update(query, id);
            return null;
        });
    }
}
