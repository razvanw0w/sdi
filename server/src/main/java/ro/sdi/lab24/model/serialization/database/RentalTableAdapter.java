package ro.sdi.lab24.model.serialization.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.exception.DatabaseException;
import ro.sdi.lab24.model.Rental;

public class RentalTableAdapter implements TableAdapter<Rental.RentalID, Rental>
{
    @Override
    public void insert(Rental entity, Connection connection) throws SQLException
    {
        String query = "insert into rentals values(?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(query);
        insertStatement.setInt(1, entity.getId().getMovieId());
        insertStatement.setInt(2, entity.getId().getClientId());
        insertStatement.setObject(3, entity.getTime());
        insertStatement.executeUpdate();
    }

    @Override
    public List<Rental> readAll(Connection connection) throws SQLException
    {
        List<Rental> result = new ArrayList<>();
        String query = "select * from rentals";
        PreparedStatement selectStatement = connection.prepareStatement(query);
        ResultSet resultSet = selectStatement.executeQuery();
        while (resultSet.next())
        {
            int movieID = resultSet.getInt("movieid");
            int clientID = resultSet.getInt("clientid");
            LocalDateTime date = resultSet.getObject("date", LocalDateTime.class);
            result.add(new Rental(movieID, clientID, date));
        }
        return result;
    }

    @Override
    public Optional<Rental> read(Rental.RentalID rentalID, Connection connection)
            throws SQLException
    {
        String query = "select * from rentals where movieid = ? and clientid = ?";
        PreparedStatement selectStatement = connection.prepareStatement(query);
        selectStatement.setInt(1, rentalID.getMovieId());
        selectStatement.setInt(2, rentalID.getClientId());
        ResultSet resultSet = selectStatement.executeQuery();
        return Optional.of(resultSet.next())
                       .filter(boolValue -> boolValue)
                       .map(boolValue ->
                            {
                                try
                                {
                                    return new Rental(
                                            resultSet.getInt("movieid"),
                                            resultSet.getInt("clientid"),
                                            resultSet.getObject("date", LocalDateTime.class)
                                    );
                                }
                                catch (SQLException e)
                                {
                                    throw new DatabaseException("Can't retrieve data on rental read");
                                }
                            }
                       );
    }

    @Override
    public void update(Rental entity, Connection connection) throws SQLException
    {
        String query = "update rentals set date = ? where movieid = ? and clientid = ?";
        PreparedStatement updateStatement = connection.prepareStatement(query);
        updateStatement.setObject(1, entity.getTime());
        updateStatement.setInt(2, entity.getId().getMovieId());
        updateStatement.setInt(3, entity.getId().getClientId());
        updateStatement.executeUpdate();
    }

    @Override
    public void delete(Rental.RentalID id, Connection connection) throws SQLException
    {
        String query = "delete from rentals where movieid = ? and clientid = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(query);
        deleteStatement.setInt(1, id.getMovieId());
        deleteStatement.setInt(2, id.getClientId());
        deleteStatement.executeUpdate();
    }
}
