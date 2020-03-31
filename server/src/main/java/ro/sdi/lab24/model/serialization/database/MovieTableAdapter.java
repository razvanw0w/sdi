package ro.sdi.lab24.model.serialization.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.exception.DatabaseException;
import ro.sdi.lab24.model.Movie;

public class MovieTableAdapter implements TableAdapter<Integer, Movie>
{
    @Override
    public void insert(Movie entity, Connection connection) throws SQLException
    {
        String query = "insert into movies values(?,?,?,?)";
        PreparedStatement insertStatement = connection.prepareStatement(query);
        insertStatement.setInt(1, entity.getId());
        insertStatement.setString(2, entity.getName());
        insertStatement.setString(3, entity.getGenre());
        insertStatement.setInt(4, entity.getRating());
        insertStatement.executeUpdate();
    }

    @Override
    public List<Movie> readAll(Connection connection) throws SQLException
    {
        List<Movie> result = new ArrayList<>();
        String query = "select * from movies";
        PreparedStatement selectStatement = connection.prepareStatement(query);
        ResultSet resultSet = selectStatement.executeQuery();
        while (resultSet.next())
        {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String genre = resultSet.getString("genre");
            int rating = resultSet.getInt("rating");
            result.add(new Movie(id, name, genre, rating));
        }
        return result;
    }

    @Override
    public Optional<Movie> read(Integer id, Connection connection) throws SQLException
    {
        String query = "select * from movies where id = ?";
        PreparedStatement selectStatement = connection.prepareStatement(query);
        selectStatement.setInt(1, id);
        ResultSet resultSet = selectStatement.executeQuery();
        return Optional.of(resultSet.next())
                       .filter(boolValue -> boolValue)
                       .map(boolValue ->
                            {
                                try
                                {
                                    return new Movie(
                                            resultSet.getInt("id"),
                                            resultSet.getString("name"),
                                            resultSet.getString("genre"),
                                            resultSet.getInt("rating")
                                    );
                                }
                                catch (SQLException e)
                                {
                                    throw new DatabaseException(
                                            "Can't retrieve data on movie read");
                                }
                            }
                       );
    }

    @Override
    public void update(Movie entity, Connection connection) throws SQLException
    {
        String query = "update movies set name = ?, genre = ?, rating = ? where id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(query);
        updateStatement.setString(1, entity.getName());
        updateStatement.setString(2, entity.getGenre());
        updateStatement.setInt(3, entity.getRating());
        updateStatement.setInt(4, entity.getId());
        updateStatement.executeUpdate();
    }

    @Override
    public void delete(Integer id, Connection connection) throws SQLException
    {
        String query = "delete from movies where id = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(query);
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }
}
