package ro.sdi.lab24.model.serialization.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import ro.sdi.lab24.exception.DatabaseException;
import ro.sdi.lab24.model.Movie;

import java.util.List;
import java.util.Optional;

public class MovieTableAdapter implements TableAdapter<Integer, Movie> {
    @Autowired
    private JdbcOperations jdbcOperations;
    private RowMapper<Movie> movieRowMapper = (resultSet, rowNumber) ->
            new Movie(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("genre"),
                    resultSet.getInt("rating")
            );

    @Override
    public void insert(Movie entity) throws DatabaseException
    {
        handleConnectionException(DataAccessException.class, () ->
        {
            String query = "INSERT INTO movies VALUES(?,?,?,?)";
            jdbcOperations.update(
                    query,
                    entity.getId(),
                    entity.getName(),
                    entity.getGenre(),
                    entity.getRating()
            );
            return null;
        });
    }

    @Override
    public List<Movie> readAll() throws DatabaseException
    {
        return handleConnectionException(DataAccessException.class, () ->
        {
            String query = "SELECT * FROM movies";
            return jdbcOperations.query(query, movieRowMapper);
        });
    }

    @Override
    public Optional<Movie> read(Integer id) throws DatabaseException
    {
        return handleConnectionException(DataAccessException.class, () ->
        {
            String query = "SELECT * FROM movies WHERE id = ?";
            List<Movie> movieList = jdbcOperations.query(
                    query,
                    new Object[]{id},
                    movieRowMapper
            );

            if (movieList.size() != 1) return Optional.empty();
            return Optional.of(movieList.get(0));
        });
    }

    @Override
    public void update(Movie entity) throws DatabaseException {
        handleConnectionException(DataAccessException.class, () -> {
            String query = "update movies set name = ?, genre = ?, rating = ? where id = ?";
            jdbcOperations.update(query, entity.getName(), entity.getGenre(), entity.getRating(), entity.getId());
            return null;
        });
    }

    @Override
    public void delete(Integer id) throws DatabaseException {
        handleConnectionException(DataAccessException.class, () -> {
            String query = "delete from movies where id = ?";
            jdbcOperations.update(query, id);
            return null;
        });
    }
}
