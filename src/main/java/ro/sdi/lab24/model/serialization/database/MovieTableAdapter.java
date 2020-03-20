package ro.sdi.lab24.model.serialization.database;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import ro.sdi.lab24.model.Movie;

public class MovieTableAdapter implements TableAdapter<Integer, Movie>
{
    @Override
    public void insert(Movie entity, Connection connection)
    {
        //TODO
    }

    @Override
    public List<Movie> readAll(Connection connection)
    {
        return null;//TODO
    }

    @Override
    public Optional<Movie> read(Integer integer, Connection connection)
    {
        return Optional.empty();//TODO
    }

    @Override
    public void update(Movie entity, Connection connection)
    {
        //TODO
    }

    @Override
    public void delete(Integer id, Connection connection)
    {
        //TODO
    }
}
