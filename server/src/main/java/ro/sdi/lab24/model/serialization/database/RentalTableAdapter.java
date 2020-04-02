package ro.sdi.lab24.model.serialization.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.exception.DatabaseException;
import ro.sdi.lab24.model.Rental;

public class RentalTableAdapter implements TableAdapter<Rental.RentalID, Rental>
{
    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public void insert(Rental entity) throws DatabaseException
    {
        /*String query = "insert into rentals values(?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(query);
        insertStatement.setInt(1, entity.getId().getMovieId());
        insertStatement.setInt(2, entity.getId().getClientId());
        insertStatement.setObject(3, entity.getTime());
        insertStatement.executeUpdate();*/
        //TODO
    }

    @Override
    public List<Rental> readAll() throws DatabaseException
    {
        /*List<Rental> result = new ArrayList<>();
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
        return result;*/
        //TODO
        return null;
    }

    @Override
    public Optional<Rental> read(Rental.RentalID rentalID)
            throws DatabaseException
    {
        /*String query = "select * from rentals where movieid = ? and clientid = ?";
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
                       );*/
        //TODO
        return Optional.empty();
    }

    @Override
    public void update(Rental entity) throws DatabaseException
    {
        /*String query = "update rentals set date = ? where movieid = ? and clientid = ?";
        PreparedStatement updateStatement = connection.prepareStatement(query);
        updateStatement.setObject(1, entity.getTime());
        updateStatement.setInt(2, entity.getId().getMovieId());
        updateStatement.setInt(3, entity.getId().getClientId());
        updateStatement.executeUpdate();*/
        //TODO
    }

    @Override
    public void delete(Rental.RentalID id) throws DatabaseException
    {
        /*String query = "delete from rentals where movieid = ? and clientid = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(query);
        deleteStatement.setInt(1, id.getMovieId());
        deleteStatement.setInt(2, id.getClientId());
        deleteStatement.executeUpdate();*/
        //TODO
    }
}
