package ro.sdi.lab24.model.serialization.database;

import ro.sdi.lab24.exception.DatabaseException;
import ro.sdi.lab24.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientTableAdapter implements TableAdapter<Integer, Client> {

    @Override
    public void insert(Client entity, Connection connection) throws SQLException {
        String query = "insert into clients values(?,?)";
        PreparedStatement insertStatement = connection.prepareStatement(query);
        insertStatement.setInt(1, entity.getId());
        insertStatement.setString(2, entity.getName());
        insertStatement.executeUpdate();
    }

    @Override
    public List<Client> readAll(Connection connection) throws SQLException {
        List<Client> result = new ArrayList<>();
        String query = "select * from clients";
        PreparedStatement selectStatement = connection.prepareStatement(query);
        ResultSet resultSet = selectStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            result.add(new Client(id, name));
        }
        return result;
    }

    @Override
    public Optional<Client> read(Integer id, Connection connection) throws SQLException {
        String query = "select * from clients where id = ?";
        PreparedStatement selectStatement = connection.prepareStatement(query);
        selectStatement.setInt(1, id);
        ResultSet resultSet = selectStatement.executeQuery();
        Optional<Client> client = Optional.of(resultSet.next())
                .filter(boolValue -> boolValue)
                .map(boolValue -> {
                    try {
                        return new Client(resultSet.getInt("id"),
                                resultSet.getString("name"));
                    } catch (SQLException e) {
                        throw new DatabaseException("Can't retrieve data on client read");
                    }
                });
        return client;
    }

    @Override
    public void update(Client entity, Connection connection) throws SQLException {
        String query = "update clients set name = ? where id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(query);
        updateStatement.setString(1, entity.getName());
        updateStatement.setInt(2, entity.getId());
        updateStatement.executeUpdate();
    }

    @Override
    public void delete(Integer id, Connection connection) throws SQLException {
        String query = "delete from clients where id = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(query);
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }
}
