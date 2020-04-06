package ro.sdi.lab24.model.serialization.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import ro.sdi.lab24.exception.DatabaseException;
import ro.sdi.lab24.model.Rental;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RentalTableAdapter implements TableAdapter<Rental.RentalID, Rental> {
    @Autowired
    private JdbcOperations jdbcOperations;

    private RowMapper<Rental> rentalRowMapper = (resultSet, rowNum) ->
            new Rental(
                    resultSet.getInt("movieid"),
                    resultSet.getInt("clientid"),
                    resultSet.getObject("date", LocalDateTime.class)
            );

    @Override
    public void insert(Rental entity) throws DatabaseException {
        handleConnectionException(DataAccessException.class, () -> {
            String query = "insert into rentals values(?, ?, ?)";
            jdbcOperations.update(query, entity.getId().getMovieId(), entity.getId().getClientId(), entity.getTime());
            return null;
        });
    }

    @Override
    public List<Rental> readAll() throws DatabaseException {
        return handleConnectionException(DataAccessException.class, () -> {
            List<Rental> result = new ArrayList<>();
            String query = "select * from rentals";
            return jdbcOperations.query(query, rentalRowMapper);
        });
    }

    @Override
    public Optional<Rental> read(Rental.RentalID rentalID)
            throws DatabaseException {
        return handleConnectionException(DataAccessException.class, () -> {
            String query = "select * from rentals where movieid = ? and clientid = ?";
            List<Rental> rentalList = jdbcOperations.query(query,
                    new Object[]{rentalID.getMovieId(), rentalID.getClientId()},
                    rentalRowMapper
            );

            if (rentalList.size() != 1)
                return Optional.empty();
            return Optional.of(rentalList.get(0));
        });
    }

    @Override
    public void update(Rental entity) throws DatabaseException {
        handleConnectionException(DataAccessException.class, () -> {
            String query = "update rentals set date = ? where movieid = ? and clientid = ?";
            jdbcOperations.update(query,
                    entity.getTime(),
                    entity.getId().getMovieId(),
                    entity.getId().getClientId()
            );
            return null;
        });
    }

    @Override
    public void delete(Rental.RentalID id) throws DatabaseException {
        handleConnectionException(DataAccessException.class, () -> {
            String query = "delete from rentals where movieid = ? and clientid = ?";
            jdbcOperations.update(query, id.getMovieId(), id.getClientId());
            return null;
        });
    }
}
